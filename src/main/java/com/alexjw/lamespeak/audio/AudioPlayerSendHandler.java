package com.alexjw.lamespeak.audio;


import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AudioPlayerSendHandler implements AudioSendHandler, AudioReceiveHandler {
    private final Queue<byte[]> queue = new ConcurrentLinkedQueue<>();
    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public boolean canReceiveCombined() {
        return queue.size() < 10;
    }

    @Override
    public void handleCombinedAudio(CombinedAudio combinedAudio) {
        if (combinedAudio.getUsers().isEmpty())
            return;
        byte[] data = combinedAudio.getAudioData(1.0f);
        queue.add(data);
    }

    @Override
    public boolean canProvide() {
        if (lastFrame == null) {
            lastFrame = audioPlayer.provide();
        }

        return lastFrame != null;
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        byte[] data = queue.poll();
        return data == null ? null : ByteBuffer.wrap(data);
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
