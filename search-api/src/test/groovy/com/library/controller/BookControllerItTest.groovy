package com.library.controller

import com.library.controller.request.SearchRequest
import com.library.controller.response.PageResult
import com.library.controller.response.SearchResponse
import com.library.service.BookQueryService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerItTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    BookQueryService bookQueryService = Mock()

    def "정상인자로 요청시 성공한다."(){
        given:
        def request= new SearchRequest(query: "HTTP", start: 1, display: 10)

        and:
        bookQueryService.search(*_) >> new PageResult<>(1, 10, 10, [Mock(SearchResponse)])

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("start", request.start.toString())
                .param("display", request.display.toString())
                )
        then:

        // result.andReturn().response's type is MockHttpServletResponse
        result
                .andExpect {status().is2xxSuccessful()}
                .andExpect {jsonPath('$.totalElements').value(10)}
                .andExpect{jsonPath('$.page').value(1)}
                .andExpect{jsonPath('$.size').value(10)}
                .andExpect{jsonPath('$.contents').isArray()}

        and:
        def response = result.andReturn().response
        print "status = ${response.getStatus()}"
        print "response = ${response.getContentAsString()}"


    }

    def "[query] 잘못된 인자1 - query가 비어있을 때 응답이 반환된다."(){
        given:
        def request= new SearchRequest(query: "", start: 1, display: 10)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("start", request.start.toString())
                .param("display", request.display.toString())
        )
        then:

        // result.andReturn().response's type is MockHttpServletResponse
        result
                .andExpect {status().isBadRequest()}
                .andExpect {jsonPath('$.errorMessage').value("입력은 비어있을 수 없습니다.")}
    }

    def "[query] 잘못된 인자2 - query가 50자를 초과할 때 응답이 반환된다. ."(){
        given:
        // query: 51자로 셋팅
        def request= new SearchRequest(query: "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", start: 1, display: 10)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("start", request.start.toString())
                .param("display", request.display.toString())
        )
        then:

        // result.andReturn().response's type is MockHttpServletResponse
        result
                .andExpect {status().isBadRequest()}
                .andExpect {jsonPath('$.errorMessage').value("query는 최대 50자를 초과할 수 없습니다.")}
    }

    def "[start] 잘못된 인자1 - start가 1보다 작을 때 응답이 반환된다. ."(){
        given:
        def request= new SearchRequest(query: "HTTP", start: 0, display: 10)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("start", request.start.toString())
                .param("display", request.display.toString())
        )
        then:

        result
                .andExpect {status().isBadRequest()}
                .andExpect {jsonPath('$.errorMessage').value("페이지 번호는 1 이상이어야 합니다.")}
    }

    def "[start] 잘못된 인자2 - start가 10000보다 클 때 응답이 반환된다. ."(){
        given:
        def request= new SearchRequest(query: "HTTP", start: 1, display: 10001)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("start", request.start.toString())
                .param("display", request.display.toString())
        )
        then:

        result
                .andExpect {status().isBadRequest()}
                .andExpect {jsonPath('$.errorMessage').value("페이지 번호는 50 이하여야 합니다.")}
    }

    def "[display] 잘못된 인자1 - display가 1보다 작을 때 응답이 반환된다. ."(){
        given:
        def request= new SearchRequest(query: "HTTP", start: 1, display: 0)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("start", request.start.toString())
                .param("display", request.display.toString())
        )
        then:

        result
                .andExpect {status().isBadRequest()}
                .andExpect {jsonPath('$.errorMessage').value("페이지 크기는 1 이상이어야 합니다.")}
    }

    def "[display] 잘못된 인자2 - display가 50보다 클 때 응답이 반환된다. ."(){
        given:
        def request= new SearchRequest(query: "HTTP", start: 1, display: 51)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("start", request.start.toString())
                .param("display", request.display.toString())
        )
        then:

        result
                .andExpect {status().isBadRequest()}
                .andExpect {jsonPath('$.errorMessage').value("페이지 크기는 50 이하여야 합니다.")}
    }
}
