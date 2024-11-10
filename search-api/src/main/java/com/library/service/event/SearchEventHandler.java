package com.library.service.event;

import com.library.entity.DailyStat;
import com.library.service.DailyStatCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@Slf4j
public class SearchEventHandler {
    private final DailyStatCommandService dailyStatCommandService;

    @Async
    @EventListener
    public void handleEvent(SearchEvent searchEvent) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 현재 스레드의 인터럽트 상태를 복원
            log.error("Thread was interrupted", e);
            return; // 오류 발생 시 처리 중단
        }
        log.info("[SearchEventHandler] handleEvent : {}", searchEvent);
        DailyStat dailyStat = new DailyStat(searchEvent.query(), searchEvent.timestamp());
        dailyStatCommandService.save(dailyStat);
    }
}
