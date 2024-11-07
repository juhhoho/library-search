package com.library.repository

import com.library.entity.DailyStat
import com.library.feign.NaverClient
import jakarta.persistence.EntityManager
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDateTime

@DataJpaTest
@ActiveProfiles("test")
class DailyStatRepositoryTest extends Specification {

    @Autowired
    DailyStatRepository dailyStatRepository
    @Autowired
    EntityManager entityManager

    @SpringBean
    NaverClient naverClient = Mock()

    def "저장 후 조회가 된다."(){
        given:
        def givenQuery = "HTTP"

        when:
        def dailyStat = new DailyStat(givenQuery, LocalDateTime.now())

        def saved = dailyStatRepository.saveAndFlush(dailyStat)

        then: "실제 데이터베이스에 저장이 된다."
        saved.id != null

        when: "entity manager를 clear하고 재조회한다."
        entityManager.clear()
        def result = dailyStatRepository.findById(saved.id)

        then: "cache가 아닌 실제 DB 쿼리로 데이터를 조회한다."
        verifyAll {
            result.isPresent()
            result.get().query == givenQuery
        }

    }

}
