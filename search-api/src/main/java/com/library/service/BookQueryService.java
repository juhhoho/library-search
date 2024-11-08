package com.library.service;

import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.repository.BookRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookQueryService {
    @Qualifier("naverBookRepository")
    private final BookRepository naverBookRepository;
    @Qualifier("kakaoBookRepository")
    private final BookRepository kakaoBookRepository;


    @CircuitBreaker(name = "naverSearch", fallbackMethod = "searchFallBack")
    public PageResult<SearchResponse> search(String query, int start, int display){
        return naverBookRepository.search(query, start, display);
    }

    public PageResult<SearchResponse> searchFallBack(String query, int start, int display, Throwable throwable){
        if (throwable instanceof CallNotPermittedException){
            return handleOpenCircuit(query, start, display);
        }
        return handleException(query, start, display, throwable);
    }

    public PageResult<SearchResponse> handleOpenCircuit(String query, int start, int display){
        log.warn("[BookQueryService] Circuit Breaker is open! Fallback to kakao search");
        return kakaoBookRepository.search(query, start, display);
    }

    public PageResult<SearchResponse> handleException(String query, int start, int display, Throwable throwable){
        log.error("[BookQueryService] An error occurred! Fallback to kakao search. errorMessage = {}", throwable.getMessage());
        return kakaoBookRepository.search(query, start, display);
    }


}
