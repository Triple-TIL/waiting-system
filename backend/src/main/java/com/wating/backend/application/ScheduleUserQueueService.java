package com.wating.backend.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuples;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleUserQueueService {

    private static final String USER_QUEUE_WAIT_KEY_FOR_SCAN = "users:queue:*:wait";

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private final UserQueueService userQueueService;

    @Value("${scheduler.enabled}")
    private Boolean scheduling = false;

    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void scheduleAllowUser() {
        if(!scheduling) {
            return;
        }
        log.info("called scheduling...");
        var maxAllowUserCount = 3L;

        reactiveRedisTemplate.scan(ScanOptions.scanOptions()
                .match(USER_QUEUE_WAIT_KEY_FOR_SCAN)
                .count(100)
                .build())
                .map(key -> key.split(":")[2])
                .flatMap(queue -> userQueueService.allowUser(queue, maxAllowUserCount).map(allowed -> Tuples.of(queue, allowed)))
                .doOnNext(tuple -> log.info("Tried %d and allowed %d members of %s queues".formatted(maxAllowUserCount, tuple.getT2(), tuple.getT1())))
                .subscribe();
    }

}
