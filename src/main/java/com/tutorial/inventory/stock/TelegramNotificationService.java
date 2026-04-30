package com.tutorial.inventory.stock;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TelegramNotificationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger log = LoggerFactory.getLogger(TelegramNotificationService.class);

    @Value("${telegram.bot-token}")
    private String botToken;

    @Value("${telegram.chat-id}")
    private String chatId;

    private static final String TELEGRAM_API = "https://api.telegram.org/bot";

    public void sendMessage(String message) {
        String url = TELEGRAM_API + botToken + "/sendMessage";

        Map<String, String> params = new HashMap<>();
        params.put("chat_id", chatId);
        params.put("text", message);
        params.put("parse_mode", "Markdown"); // supports *bold*, _italic_, `code`

        try {
            restTemplate.postForObject(url, params, String.class);
        } catch (Exception e) {
            log.error("Failed to send Telegram notification: {}", e.getMessage());
        }
    }
}