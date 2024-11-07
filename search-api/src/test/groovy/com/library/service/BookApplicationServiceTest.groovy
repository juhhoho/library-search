package com.library.service

import com.library.entity.DailyStat
import spock.lang.Specification

import java.time.LocalDate

class BookApplicationServiceTest extends Specification {

    BookApplicationService bookApplicationService

    BookQueryService bookQueryService = Mock(BookQueryService)
    DailyStatCommandService dailyStatCommandService = Mock(DailyStatCommandService)
    DailyStatQueryService dailyStatQueryService = Mock(DailyStatQueryService)

    void setup(){
        bookApplicationService = new BookApplicationService(bookQueryService, dailyStatCommandService, dailyStatQueryService)
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

    def "findQueryCount 메서드 호출시 인자를 조작없이 넘겨준다."(){

        given:
        def givenQuery = "HTTP"
        def givenDate = LocalDate.now()

        when:
        bookApplicationService.findQueryCount(givenQuery, givenDate)

        then:
        1 * dailyStatQueryService.findQueryCount(*_)>>{
            String query, LocalDate date ->
                assert query == givenQuery
                assert date == givenDate
        }

    }

    def "findTop5Query 메서드 호출시 DailyStatQueryService의 findTop5Query가 호출된다."(){

        given:
        when:
        bookApplicationService.findTop5Query()

        then:
        1 * dailyStatQueryService.findTop5Query()

    }
}
