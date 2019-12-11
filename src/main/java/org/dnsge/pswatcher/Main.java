package org.dnsge.pswatcher;

import org.dnsge.powerschoolapi.client.DefaultPowerschoolClient;
import org.dnsge.powerschoolapi.client.PowerschoolClient;
import org.dnsge.powerschoolapi.client.PowerschoolLoginException;
import org.dnsge.powerschoolapi.detail.Course;
import org.dnsge.powerschoolapi.user.User;
import org.dnsge.pswatcher.history.Modification;

import java.io.IOException;
import java.util.List;

public class Main {

    static final String VERSION_STRING = "pswatcher/1.0";

    public static void main(String[] args) {
        Configuration config = Configuration.fromEnv();

        MiniLogger.log("Configuration Loaded!");
        MiniLogger.log("Signing into service...");

        PowerschoolClient client = new DefaultPowerschoolClient(config.psURL, config.userAgent);
        User user;

        try {
            user = client.authenticate(config.username, config.password);
        } catch (PowerschoolLoginException e) {
            MiniLogger.err("Invalid username and or password provided.");
            return;
        } catch (IOException e) {
            MiniLogger.err(e);
            return;
        }

        MiniLogger.log("Signed in as " + user.getUsername() + " = " + user.getPersonName());
        MiniLogger.log("Beginning with delay of " + config.delay + " seconds");

        Actor<List<Modification<Course>>> actor =
                new TelegramModificationActor(config.botToken, config.chatId);

        TimedChecker.beginChecking(user, client, actor, config.delay);
    }


}
