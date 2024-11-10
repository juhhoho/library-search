package com.library.repository

import com.library.Document
import com.library.KakaoBookResponse
import com.library.Meta
import com.library.feign.KakaoClient
import spock.lang.Specification

import java.time.LocalDate

class KakaoBookRepositoryTest extends Specification {

    BookRepository bookRepository
    KakaoClient kakaoClient = Mock()

    void setup(){
       bookRepository = new KakaoBookRepository(kakaoClient)
    }

    def "search 호출 시 적절한 데이터 반환"(){
        given:
        def givenDocuments = [
                new Document("제목1", ["저자1"], "출판사1", "isbn1", "2016-02-01T00:00:00.000+09:00"),
                new Document("제목2", ["저자2"], "출판사2", "isbn2", "2016-02-01T00:00:00.000+09:00"),
        ]
        def givenMeta = new Meta(false, 1, 10)
        def response = new KakaoBookResponse(givenDocuments, givenMeta)

        and:
        1 * kakaoClient.search("HTTP", 1, 2) >> response

        when:
        def result = bookRepository.search("HTTP", 1, 2)

        then:
        verifyAll(result){
            start() == 1
            display() == 2
            totalElements() == 10
            contents().size() == 2
            contents().get(0).pubDate() == LocalDate.of(2016,2,1)
        }


    }
}
