package com.library.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.NaverErrorRepsonse;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NaverErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    public NaverErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String s, Response response) {
        try {
            String body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            NaverErrorRepsonse errorRepsonse =  objectMapper.readValue(body, NaverErrorRepsonse.class);
            throw new RuntimeException(errorRepsonse.getErrorMessage());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
