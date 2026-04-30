package com.tutorial.inventory.stock;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockNotificationListener implements InitializingBean {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final DataSource dataSource;
    private final StockNotificationService notificationService;

    private static final Logger log = LoggerFactory.getLogger(StockNotificationListener.class);

    @Override
    public void afterPropertiesSet() {
        // Run in background thread — this blocks waiting for events
        new Thread(this::listenForNotifications, "pg-notify-listener").start();
    }

    private void listenForNotifications() {
        try (Connection conn = dataSource.getConnection()) {
            // Cast to PGConnection to access LISTEN
            PGConnection pgConn = conn.unwrap(PGConnection.class);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("LISTEN low_stock_alert");
            }

            while (!Thread.currentThread().isInterrupted()) {
                PGNotification[] notifications = pgConn.getNotifications(5000); // 5s timeout
                if (notifications != null) {
                    for (PGNotification notification : notifications) {
                        StockNotification stockNotification = objectMapper.readValue(notification.getParameter(), StockNotification.class);
                        notificationService.sendAlert(stockNotification);

                    }
                }
            }
        } catch (SQLException e) {
            log.error("Error while listening for PostgreSQL notifications", e);
        } catch (JsonProcessingException e) {
            log.error("Error while processing the JSON", e);
        }
    }
}
