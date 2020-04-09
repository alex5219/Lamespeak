package com.alexjw.lamespeak.listeners;

import com.alexjw.lamespeak.audio.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class GuildManager extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        connect(event.getChannelJoined());
        PlayerManager playerManager = PlayerManager.getInstance();
        playerManager.loadAndPlay(event.getChannelJoined(), "https://youtu.be/VRWzXSLwNQU");
        playerManager.getGuildMusicManager(event.getGuild()).player.setVolume(25);
        disconnect(event.getChannelJoined());
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        connect(event.getChannelLeft());
        PlayerManager playerManager = PlayerManager.getInstance();
        playerManager.loadAndPlay(event.getChannelLeft(), "https://youtu.be/TRRR6nv48pk");
        playerManager.getGuildMusicManager(event.getGuild()).player.setVolume(25);
        disconnect(event.getChannelLeft());
    }

    public void connect(VoiceChannel voiceChannel) {
        AudioManager audioManager = voiceChannel.getGuild().getAudioManager();
        if (audioManager.isConnected()) {
            return;
        }
        Member selfMember = voiceChannel.getGuild().getSelfMember();
        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            return;
        }
        audioManager.openAudioConnection(voiceChannel);
    }

    public void disconnect(VoiceChannel voiceChannel) {
        AudioManager audioManager = voiceChannel.getGuild().getAudioManager();
        if (audioManager.isConnected()) {
            audioManager.closeAudioConnection();
        }
    }
}
