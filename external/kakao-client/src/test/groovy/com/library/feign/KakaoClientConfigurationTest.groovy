package com.library.feign

import feign.RequestInterceptor
import feign.RequestTemplate
import spock.lang.Specification

class KakaoClientConfigurationTest extends Specification {
    KakaoClientConfiguration configuration

    void setup(){
        configuration = new KakaoClientConfiguration()
    }

    def "requestInterceptor의 header에 key값들이 적용된다."(){
        given:
        def template = new RequestTemplate()
        def givenRestApiKey = "samplesample";

        and: "interceptor를 타기 전에는 header가 template에 존재하지 않는다."
        template.headers()["Authorization"] == null

        when:
        def interceptor = configuration.requestInterceptor(givenRestApiKey)
        interceptor.apply(template)

        then: "interceptor를 탄 후에는 header가 template에 추가된다."
        template.headers()["Authorization"].contains("KakaoAK " + givenRestApiKey)

        and:
        print "rest-api-key ${givenRestApiKey}"
    }

}
