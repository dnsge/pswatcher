package org.dnsge.pswatcher;

/**
 * Container class for Grade Watcher configuration
 */
public class Configuration {

    final String username, password, botToken, chatId, psURL, userAgent;
    Integer delay;

    /**
     * Create new Configuration Object
     *
     * @param username  Username for PowerSchool Login
     * @param password  Password for PowerSchool Login
     * @param psURL     PowerSchool URL
     * @param botToken  Telegram Bot Token
     * @param chatId    Telegram Chat ID
     * @param userAgent PowerSchool interface User-Agent
     * @param delay     Number of seconds between grade refresh
     */
    public Configuration(String username, String password, String psURL, String botToken, String chatId,
                         String userAgent, Integer delay) {
        this.username = username;
        this.password = password;
        this.psURL = psURL;
        this.botToken = botToken;
        this.chatId = chatId;
        this.userAgent = userAgent;
        this.delay = delay;
    }

    /**
     * Load a Configuration from Environment Variables
     * Required env vars: USERNAME, PASSWORD, PS_URL, BOT_TOKEN, CHAT_ID
     *
     * @return New Configuration Object with values from environment
     * @throws RuntimeException if environment variable is missing
     */
    public static Configuration fromEnv() throws RuntimeException {
        String username = getEnvOrThrow("USERNAME");
        String password = getEnvOrThrow("PASSWORD");
        String psURL = getEnvOrThrow("PS_URL");
        String botToken = getEnvOrThrow("BOT_TOKEN");
        String chatId = getEnvOrThrow("CHAT_ID");
        String userAgent = System.getenv("USER_AGENT");
        String delayString = System.getenv("DELAY");
        int delay;

        if (delayString == null)
            delay = 30 * 60; // 30 minutes default
        else
            delay = Integer.parseInt(delayString);

        if (userAgent == null)
            userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/71.0"; // default

        return new Configuration(username, password, psURL, botToken, chatId, userAgent, delay);
    }

    /**
     * Get an Environment Variable value, or throw an exception if not set
     *
     * @param envName Name of env variable
     * @return Value of variable
     * @throws RuntimeException if env variable is not set
     */
    private static String getEnvOrThrow(String envName) {
        String value = System.getenv(envName);
        if (value == null) {
            throw new RuntimeException(String.format("Missing %s environment variable", envName));
        }
        return value;
    }

}
