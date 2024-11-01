package com.library.controller.response;


import java.util.List;


public record PageResult<T>(int start, int display, int totalElements, List<T> contents) {
}