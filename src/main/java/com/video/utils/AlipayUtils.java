package com.video.utils;
/*

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;


import com.qf.domain.Orders;
import com.video.config.AlipayConfig;
import org.springframework.stereotype.Component;

@Component
public class AlipayUtils {

    public String pay(Orders orders) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key,"json","utf-8",AlipayConfig.alipay_public_key,"RSA2");
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+orders.getONumber()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":\""+orders.getShopCount()+"\"," +
                "    \"subject\":\""+orders.getShopName()+"\"," +
                "    \"body\":\""+orders.getShopName()+"\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        String form="";
        form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单        System.out.println(response.getBody());
        return form;
    }
}*/
