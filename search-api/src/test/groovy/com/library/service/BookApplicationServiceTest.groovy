package com.library.service

import com.library.entity.DailyStat
import spock.lang.Specification

class BookApplicationServiceTest extends Specification {

    BookApplicationService bookApplicationService

    BookQueryService bookQueryService = Mock(BookQueryService)
    DailyStatCommandService dailyStatCommandService = Mock(DailyStatCommandService)

    void setup(){
        bookApplicationService = new BookApplicationService(bookQueryService, dailyStatCommandService)
    }

    def "search 메서드 호출시 검색결과를 반환하고 통계데이터를 저장한다."(){

        given:
        def givenQuery = "HTTP"
        def givenStart = 1
        def givenDisplay = 1

        when:
        bookApplicationService.search(givenQuery, givenStart, givenDisplay)

        then:
        1 * bookQueryService.search(*_)>>{
            String query, int start, int display ->
                assert query == givenQuery
                assert start == givenStart
                assert display == givenDisplay
        }

        and:
        1 * dailyStatCommandService.save(*_)>>{
            DailyStat dailyStat ->
                assert dailyStat.query == givenQuery
        }
    }
}
