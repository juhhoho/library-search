package com.library.repository;

import com.library.Document;
import com.library.Item;
import com.library.KakaoBookResponse;
import com.library.NaverBookResponse;
import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.feign.KakaoClient;
import com.library.util.DateUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KakaoBookRepository implements BookRepository{

    private final KakaoClient kakaoClient;

    public KakaoBookRepository(KakaoClient kakaoClient) {
        this.kakaoClient = kakaoClient;
    }

    @Override
    public PageResult<SearchResponse> search(String query, int page, int size) {
        KakaoBookResponse response = kakaoClient.search(query, page, size);

        List<SearchResponse> kakaoResponses = response.documents().stream()
                .map(this::createResponse)
                .collect(Collectors.toList());

        return new PageResult<>(page, size, response.meta().totalCount(), kakaoResponses);

    }

    private SearchResponse createResponse(Document document){
        return SearchResponse.builder()
                .title(document.title())
                .author(document.authors().get(0))
                .publisher(document.publisher())
                .pubDate(DateUtils.parseOffsetDateTime(document.datetime()).toLocalDate())
                .isbn(document.isbn())
                .build();
    }
}
