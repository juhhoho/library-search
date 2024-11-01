package com.library.controller

import com.library.service.BookQueryService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class BookControllerTest extends Specification {
    BookQueryService bookQueryService = Mock(BookQueryService)

    BookController bookController
    MockMvc mockMvc
    void setup(){
        bookController = new BookController(bookQueryService)
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build()
    }

    def "[controller] search"(){
        given:
        def givenQuery = "HTTP"
        def givenStart = 1
        def givenDisplay = 1

        when:
        def response = mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/books?query=${givenQuery}&start=${givenStart}&display=${givenDisplay}"))
                .andReturn()
                .response


        then:
        response.status == HttpStatus.OK.value()

        and:
        1 * bookQueryService.search(*_) >> {
            String query, int start, int display ->
                assert query == givenQuery
                assert start == givenStart
                assert display == givenDisplay
        }
    }
}
