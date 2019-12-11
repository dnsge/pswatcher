package org.dnsge.pswatcher;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Telegram Bot message sender
 */
public class TelegramSender {

    private static final String sendEndpoint =
            "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

    private final String token;
    private final String chatId;

    /**
     * Create a Telegram Message sender
     *
     * @param token  Telegram Bot Token
     * @param chatId Chat ID to send messages to
     */
    TelegramSender(String token, String chatId) {
        this.token = token;
        this.chatId = chatId;
    }

    /**
     * Send message to chat
     *
     * @param text Message text
     * @throws IOException if something goes wrong
     */
    void sendMessage(String text) throws IOException {
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8); // Encode text for URL insertion

        Jsoup.connect(String.format(sendEndpoint, token, chatId, encodedText)) // Create message URL
                .ignoreContentType(true)
                .timeout(2000)
                .userAgent(Main.VERSION_STRING)
                .get();

        MiniLogger.log("Sent message: " + text);
    }

}
