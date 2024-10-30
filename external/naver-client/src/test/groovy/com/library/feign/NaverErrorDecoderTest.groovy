package com.library.feign

import com.fasterxml.jackson.databind.ObjectMapper
import com.library.NaverErrorRepsonse
import feign.Request
import feign.Response
import spock.lang.Specification

class NaverErrorDecoderTest extends Specification {
    ObjectMapper objectMapper = Mock()
    NaverErrorDecoder errorDecoder = new NaverErrorDecoder(objectMapper)


    def "errorDecoder에서 error발생 시 RuntimeException 예외가 throw된다."(){
        given:
        def responseBody = Mock(Response.Body)
        def inputStream = new ByteArrayInputStream()
        def response = Response.builder()
            .status(400)
            .request(Request.create(Request.HttpMethod.GET, "testUrl", [:], null as Request.Body, null))
            .body(responseBody)
            .build()

        1 * responseBody.asInputStream() >> inputStream
        1 * objectMapper.readValue(*_) >> new NaverErrorRepsonse("error!!", "SEO3")

        when:
        errorDecoder.decode(_ as String, response)

        then:
        RuntimeException exception = thrown()
        exception.message == "error!!"
    }
}
