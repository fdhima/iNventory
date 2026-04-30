package com.tutorial.inventory.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StockNotificationService {

    private static final Logger log = LoggerFactory.getLogger(StockNotificationService.class);


    public void sendAlert(StockNotification notification) {
        log.warn("[LOW STOCK] {} @ {} — level: {}, threshold: {}",
                notification.getProductName(),
                notification.getLocation(),
                notification.getLevel(),
                notification.getThreshold());
    }
}
