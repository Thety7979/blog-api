package com.tytran.blog.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tytran.blog.repository.InvalidatedTokenRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvalidatedTokenScheduled {

    InvalidatedTokenRepository invalidatedTokenRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanUpInvalidatedTokens() {
        try {
            invalidatedTokenRepository.deleteExpiriedToken();
            log.info("Xóa thành công");
        } catch (RuntimeException e) {
            log.error("Xóa thất bại", e);
        }
    }
}
