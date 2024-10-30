package com.library.feign

import feign.RequestTemplate
import spock.lang.Specification

class NaverClientConfigurationTest extends Specification{
    NaverClientConfiguration configuration

    void setup(){
        configuration = new NaverClientConfiguration()
    }

    def "requestInterceptor의 header에 key값들이 적용된다"(){
        given:
        def template = new RequestTemplate()
        def clientId = "id"
        def clientSecret = "secret"

        and: "Interceptor를 타기 전에는 header가 template에 존재하지 않는다."
        template.headers()["X-Naver-Client-Id"] == null
        template.headers()["X-Naver-Client-Secret"] == null

        when:
        def interceptor = configuration.requestInterceptor(clientId, clientSecret)
        interceptor.apply(template)

        then:"Interceptor를 탄 후에는 header가 template에 추가된다"
        template.headers()["X-Naver-Client-Id"].contains(clientId)
        template.headers()["X-Naver-Client-Secret"].contains(clientSecret)

        println "clientId: ${template.headers()["X-Naver-Client-Id"]}"
        println "clientId: ${template.headers()["X-Naver-Client-Secret"]}"


    }
}
