package com.video.response;

import lombok.Data;

import java.util.List;


@Data
public class Comments {

    private Pagination com;  //主

    private List<Pagination> comment; //从

}
