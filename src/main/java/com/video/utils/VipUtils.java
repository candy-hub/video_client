package com.video.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.video.config.VipConfig;
import com.video.domain.User;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;

@Component
public class VipUtils {
    public String pay(User user,BigDecimal money) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(VipConfig.gatewayUrl,VipConfig.app_id,VipConfig.merchant_private_key,"json","utf-8",VipConfig.alipay_public_key,"RSA2");
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+user.getUserRechargeVipOrderNumber()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":\""+money+"\"," +
                "    \"subject\":\"VIP充值\"," +
                "    \"body\":\"VIP充值\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");
        alipayRequest.setReturnUrl(VipConfig.return_url);
        alipayRequest.setNotifyUrl(VipConfig.notify_url);
        String form="";
        form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单        System.out.println(response.getBody());
        return form;
    }
}
