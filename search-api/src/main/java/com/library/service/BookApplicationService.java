package com.library.service;

import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.controller.response.StatResponse;
import com.library.entity.DailyStat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookApplicationService {
    private final BookQueryService bookQueryService;
    private final DailyStatCommandService dailyStatCommandService;
    private final DailyStatQueryService dailyStatQueryService;


    public PageResult<SearchResponse> search(String query, int start, int display){
        // 외부 api 호출 -> 통계 데이터 저장 -> api 호출 값 응답
        PageResult<SearchResponse> response = bookQueryService.search(query, start, display);
        DailyStat dailyStat = new DailyStat(query, LocalDateTime.now());
        dailyStatCommandService.save(dailyStat);
        return response;
    }

    public StatResponse findQueryCount(String query, LocalDate date){
        return dailyStatQueryService.findQueryCount(query, date);
    }

    public List<StatResponse> findTop5Query(){
        return dailyStatQueryService.findTop5Query();
    }
}
