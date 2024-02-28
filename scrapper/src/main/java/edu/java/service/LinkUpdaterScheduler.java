package edu.java.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

@Log4j2
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "#{scheduler.delay()}")
    public void update() {
        log.info("update");
    }
}
