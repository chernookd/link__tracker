package edu.java.bot.controller;

import edu.java.bot.controller.dto.LinkUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpdateProcessor {
    public ResponseEntity<Void> process(LinkUpdateRequest request) {
        log.info("UpdateProcessor work");
        return ResponseEntity.ok().build();
    }
}

