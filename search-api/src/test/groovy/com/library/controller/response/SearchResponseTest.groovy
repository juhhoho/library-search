package com.library.controller.response

import spock.lang.Specification

import java.time.LocalDate

class SearchResponseTest extends Specification {

    def "SearchResponse 객체 생성"(){
        given:
        def givenTitle = "ex_title"
        def givenAuthor = "ex_author"
        def givenPublisher = "ex_publisher"
        def givenPubDate = LocalDate.of(2024, 01, 12)
        def givenIsbn = "123456789"
        when:

        def givenSearchResponse = SearchResponse.builder()
                .title(givenTitle)
                .author(givenAuthor)
                .publisher(givenPublisher)
                .pubDate(givenPubDate)
                .isbn(givenIsbn)
                .build();

        then:
        verifyAll {
            givenSearchResponse.title() == givenTitle
            givenSearchResponse.author() == givenAuthor
            givenSearchResponse.publisher() == givenPublisher
            givenSearchResponse.pubDate() == givenPubDate
            givenSearchResponse.isbn() == givenIsbn
        }
    }
}
