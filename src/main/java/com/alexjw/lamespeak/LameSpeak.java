package com.alexjw.lamespeak;

import com.alexjw.lamespeak.listeners.GuildManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class LameSpeak {
    public static void main(String[] args) throws IllegalArgumentException {
        GuildManager guildManager = new GuildManager();
        Logger logger = LoggerFactory.getLogger(LameSpeak.class);
        try {
            logger.info("Logging in...");
            JDA api = JDABuilder.createDefault("")
                    .addEventListeners(guildManager)
                    .setActivity(Activity.playing("Test"))
                    .build();
            logger.info("Online... awaiting connections");
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
