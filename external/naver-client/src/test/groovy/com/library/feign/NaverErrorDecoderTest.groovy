package com.library.feign

import com.fasterxml.jackson.databind.ObjectMapper
import com.library.ApiException
import com.library.ErrorType
import com.library.NaverErrorResponse
import feign.Request
import feign.Response
import org.springframework.http.HttpStatus
import spock.lang.Specification

class NaverErrorDecoderTest extends Specification {
    ObjectMapper objectMapper = Mock()
    NaverErrorDecoder errorDecoder = new NaverErrorDecoder(objectMapper)


    def "errorDecoder에서 error 발생시 ApiException 예외가 throw된다."(){
        given:
        def responseBody = Mock(Response.Body)
        def inputStream = new ByteArrayInputStream()
        def response = Response.builder()
            .status(400)
            .request(Request.create(Request.HttpMethod.GET, "testUrl", [:], null as Request.Body, null))
            .body(responseBody)
            .build()

        1 * responseBody.asInputStream() >> inputStream
        1 * objectMapper.readValue(*_) >> new NaverErrorResponse("error!!", "SEO3")

        when:
        errorDecoder.decode(_ as String, response)

        then:
        ApiException exception = thrown()
        verifyAll {
            exception.errorMessage == "error!!"
            exception.errorType == ErrorType.EXTERMINAL_API_ERROR
            exception.httpStatus == HttpStatus.BAD_REQUEST}
    }
}
