package com.library.service.event

import com.library.entity.DailyStat
import com.library.service.DailyStatCommandService
import spock.lang.Specification

import java.time.LocalDateTime

class SearchEventHandlerTest extends Specification {

    SearchEventHandler searchEventHandler
    DailyStatCommandService dailyStatCommandService = Mock()

    void setup(){
        searchEventHandler = new SearchEventHandler(dailyStatCommandService)
    }


    def "handleEvent"(){
        given:
        def givenSearchEvent = new SearchEvent("HTTP", LocalDateTime.of(2024,5,1,0,0,0))

        when:
        searchEventHandler.handleEvent(givenSearchEvent)

        then:
        1 * dailyStatCommandService.save(_ as DailyStat)

    }
}
