package com.library.service

import com.library.repository.DailyStatRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DailyStatQueryServiceTest extends Specification {
    DailyStatQueryService dailyStatQueryService
    DailyStatRepository dailyStatRepository = Mock(DailyStatRepository)

    void setup(){
        dailyStatQueryService = new DailyStatQueryService(dailyStatRepository)
    }

    def "findQueryCount 조회 시 하루치를 조회하면서 쿼리 개수가 반환된다."(){
        given:
        def givenQuery = "HTTP"
        def givenDate = LocalDate.of(2024,5,2)
        def expectedCount = 10

        when:
        def response = dailyStatQueryService.findQueryCount(givenQuery, givenDate)

        then:
        1 * dailyStatRepository.countByQueryAndEventDateTimeBetween(
                givenQuery,
                LocalDateTime.of(2024,5,2,0,0,0),
                LocalDateTime.of(2024,5,2,23,59,59, 999999999)
        ) >> expectedCount

        and:
        response.count() == 10
    }
}
