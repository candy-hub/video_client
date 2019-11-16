package com.video.response;

import lombok.Data;

import java.util.List;

@Data
public class Pagination<T> {
    private List<T> list;
    private Long total;
}
