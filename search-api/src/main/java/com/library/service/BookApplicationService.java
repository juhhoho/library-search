package com.library.service;

import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.controller.response.StatResponse;
import com.library.entity.DailyStat;
import com.library.service.event.SearchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookApplicationService {
    private final BookQueryService bookQueryService;
    private final DailyStatQueryService dailyStatQueryService;
    private final ApplicationEventPublisher eventPublisher;

    /*
    public PageResult<SearchResponse> search(String query, int start, int display){
        // 외부 api 호출 -> 통계 데이터 저장 -> api 호출 값 응답
        PageResult<SearchResponse> response = bookQueryService.search(query, start, display);
        DailyStat dailyStat = new DailyStat(query, LocalDateTime.now());
        dailyStatCommandService.save(dailyStat);
        return response;
    }

    이 떄 사용자는 통계 데이터가 저장되는 로직에 소요되는 시간을 기다려야 할 이유가 없음.
    해당 과정을 이벤트 처리하여 분산 실행하도록 수정.
     */
    public PageResult<SearchResponse> search(String query, int start, int display){
        PageResult<SearchResponse> response = bookQueryService.search(query, start, display);
        if (!response.contents().isEmpty()){
            log.info("검색결과 개수: {}", response.display());
        }
        eventPublisher.publishEvent(new SearchEvent(query, LocalDateTime.now()));
        return response;
    }

    public StatResponse findQueryCount(String query, LocalDate date){
        return dailyStatQueryService.findQueryCount(query, date);
    }

    public List<StatResponse> findTop5Query(){
        return dailyStatQueryService.findTop5Query();
    }
}
