package com.library.repository;

import com.library.Item;
import com.library.NaverBookResponse;
import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.feign.NaverClient;
import com.library.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class NaverBookRepository implements BookRepository{
    private final NaverClient naverClient;

    @Override
    public PageResult<SearchResponse> search(String query, int start, int display) {
        NaverBookResponse response = naverClient.search(query, start, display);

        List<SearchResponse> responses = response.getItems().stream()
                .map(this::createResponse)
                .collect(Collectors.toList());

        return new PageResult<>(start, display, response.getTotal(), responses);

    }

    private SearchResponse createResponse(Item item){
        return SearchResponse.builder()
                .title(item.getTitle())
                .author(item.getAuthor())
                .publisher(item.getPublisher())
                .pubDate(DateUtils.parseYYYYMMDD(item.getPubDate()))
                .isbn(item.getIsbn())
                .build();
    }
}
