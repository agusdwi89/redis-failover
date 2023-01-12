package com.telkomsel.digipos.redisfailover.controller;

import com.telkomsel.digipos.redisfailover.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class FailoverController {
    private final RedisService redisService;
    @GetMapping("/redis-test-failover")
    public ResponseEntity<String> failoverTest() {
        String redisValue = redisService.storeData("testKey", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
        if (redisValue != null) {
            return ResponseEntity.ok(redisValue);
        } else {
            return ResponseEntity.internalServerError().body("Failed to write to redis");
        }
    }
}
