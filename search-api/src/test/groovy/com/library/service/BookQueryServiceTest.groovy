package com.library.service

import com.library.repository.BookRepository
import spock.lang.Specification

class BookQueryServiceTest extends Specification {
    BookRepository bookRepository = Mock(BookRepository)
    BookQueryService bookQueryService
    void setup(){
        bookQueryService = new BookQueryService(bookRepository)
    }

    def "[search] controller에서 받은 인자가 변경없이 넘어간다."(){
        given:
        def givenQuery = "ex_query"
        def givenStart = 1
        def givenDisplay = 1
        when:
        bookQueryService.search(givenQuery, givenStart, givenDisplay)

        then:
        1 * bookRepository.search(*_) >> {
            String query, int start, int display ->
                assert query == givenQuery
                assert start == givenStart
                assert display == givenDisplay
        }
    }
}
