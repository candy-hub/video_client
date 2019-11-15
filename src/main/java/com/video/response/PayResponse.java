package com.video.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayResponse {
    private Integer userId;
    private BigDecimal rechargeVip;
}
