package com.PeterHausen.Clicker.schedulers;

import com.PeterHausen.Clicker.services.GameService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CloseScheduler {
    private static final Logger logger = LoggerFactory.getLogger(CloseScheduler.class);
    private final GameService gameService;

    @Scheduled(fixedDelay = 1000)
    public void schedule(){
        var expired = gameService.closeExpired();

        if (expired > 0) {
            logger.info("Closed expired: " + expired);
        }
    }
}
