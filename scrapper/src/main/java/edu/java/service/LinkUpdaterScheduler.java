package edu.java.service;

import edu.java.service.serviceDao.LinkUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "#{scheduler.delay()}")
    public void update() {
        linkUpdater.update();
        log.info("update");
    }
}
