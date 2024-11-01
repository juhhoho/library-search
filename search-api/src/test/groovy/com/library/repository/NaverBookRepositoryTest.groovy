package com.library.repository

import com.library.Item
import com.library.NaverBookResponse
import com.library.feign.NaverClient
import spock.lang.Specification

import java.time.LocalDate

class NaverBookRepositoryTest extends Specification {
    BookRepository bookRepository
    NaverClient naverClient = Mock()

    void setup(){
        bookRepository = new NaverBookRepository(naverClient)
    }

    def "search 호출 시 적절한 데이터 반환"(){
        given:
        def items = [
                new Item(title:"ex_title",  author:"ex_author", publisher: "ex_publisher", pubDate: "20241212", isbn: "ex_isbn"),
                new Item(title:"ex_title2", author:"ex_author2", publisher: "ex_publisher2", pubDate: "20241212", isbn: "ex_isbn2")
        ]
        def response = new NaverBookResponse(
                lastBuildDate: "Fri, 01 Nov 2024 14:21:07 +0900",
                total:2,
                start:1,
                display:2,
                items: items
        )

        and:
        1 * naverClient.search("HTTP", 1, 2) >> response

        when:
        def result = bookRepository.search("HTTP", 1, 2)

        then:
        verifyAll {
            result.start() == 1
            result.display() == 2
            result.totalElements() == 2
            result.contents().size() == 2
            result.contents().get(0).pubDate() == LocalDate.of(2024,12,12)
        }
    }

}
