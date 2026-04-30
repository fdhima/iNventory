package com.tutorial.inventory.stock;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockNotificationListener implements InitializingBean, DisposableBean {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final DataSource dataSource;
    private final StockNotificationService notificationService;
    private final TelegramNotificationService telegramNotificationService;

    private static final Logger log = LoggerFactory.getLogger(StockNotificationListener.class);

    private final AtomicBoolean stopping = new AtomicBoolean(false);
    private volatile Connection listenerConnection;

    @Override
    public void afterPropertiesSet() {
        Thread thread = new Thread(this::listenForNotifications, "pg-notify-listener");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void destroy() throws Exception {
        stopping.set(true);
        Connection conn = listenerConnection;
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }

    private void listenForNotifications() {
        try (Connection conn = dataSource.getConnection()) {
            listenerConnection = conn;
            PGConnection pgConn = conn.unwrap(PGConnection.class);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("LISTEN low_stock_alert");
            }

            while (!Thread.currentThread().isInterrupted() && !stopping.get()) {
                PGNotification[] notifications = pgConn.getNotifications(5000);
                if (notifications != null) {
                    for (PGNotification notification : notifications) {
                        StockNotification stockNotification = objectMapper.readValue(notification.getParameter(), StockNotification.class);
                        notificationService.sendAlert(stockNotification);

                        telegramNotificationService.sendMessage(
                            "[LOW STOCK] " + stockNotification.getProductName() +
                            " @ " + stockNotification.getLocation() +
                            " — level: " + stockNotification.getLevel() +
                            ", threshold: " + stockNotification.getThreshold()
                        );
                    }
                }
            }
        } catch (SQLException e) {
            if (!stopping.get()) {
                log.error("Error while listening for PostgreSQL notifications", e);
            }
        } catch (JsonProcessingException e) {
            log.error("Error while processing the JSON", e);
        } finally {
            listenerConnection = null;
        }
    }

}
