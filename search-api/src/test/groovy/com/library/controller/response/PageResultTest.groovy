package com.library.controller.response

import spock.lang.Specification

class PageResultTest extends Specification {

    def "PageResult 객체가 생성된다"(){
        given:
        def givenStart = 1
        def givenDisplay = 1
        def givenTotalElements = 1
        def searchResponse1 = Mock(SearchResponse)
        def searchResponse2 = Mock(SearchResponse)

        when:
        def result = new PageResult<>(givenStart, givenDisplay, givenTotalElements, [searchResponse1, searchResponse2])

        then:
        verifyAll {
            result.start() == givenStart
            result.display() == givenDisplay
            result.totalElements() == givenTotalElements
            result.contents().size() == 2
        }
    }

}
