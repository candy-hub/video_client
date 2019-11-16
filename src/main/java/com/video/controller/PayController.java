package com.video.controller;

import com.alipay.api.AlipayApiException;
import com.video.config.AlipayConfig;
import com.video.domain.User;
import com.video.response.PayResponse;
import com.video.service.UserService;
import com.video.utils.AlipayUtils;
import com.video.utils.OrderUtils;
import com.video.utils.VipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class PayController {

    @Resource
    private OrderUtils orderUtils;

    @Autowired
    private AlipayUtils alipayUtils;

    @Autowired
    private VipUtils vipUtils;

    @Resource
    UserService userService;

    @RequestMapping(value = "/aliPayPayForCount/{userId}/{money}",method = RequestMethod.POST)
    public String aliPay(@PathVariable("userId")Integer userId,@PathVariable("money")BigDecimal money){
        System.out.println(userId);
        System.out.println(money);
        User user=userService.findByUserId(userId);
        user.setUserRechargeOrderNumber(orderUtils.getOrder());
        userService.update(user);
        String pay="";
        try {
            pay = alipayUtils.pay(user,money);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return pay;
    }

    @RequestMapping(value = "/countPayForVip",method = RequestMethod.POST)
    public String countPayForVip(@RequestBody PayResponse payResponse){
        User user = userService.findByUserId(payResponse.getUserId());
        user.setUserMoney(user.getUserMoney().subtract(payResponse.getRechargeVip()));
        user.setUserStatue(1);
        userService.update(user);
        return "1";
    }


    @RequestMapping(value = "/alipayPayForVip",method = RequestMethod.POST)
    public String userRecharge(@RequestBody PayResponse payResponse){
       /* System.out.println(payResponse.getUserId());
        System.out.println(payResponse.getRechargeVip());*/
        User user=userService.findByUserId(payResponse.getUserId());
        user.setUserRechargeVipOrderNumber(orderUtils.getOrder());
        userService.update(user);
        String pay="";
        try {
            pay = vipUtils.pay(user,payResponse.getRechargeVip());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return pay;
    }

    @RequestMapping(value = "/notify",method = RequestMethod.POST)
    public void Verify(HttpServletRequest request, HttpServletResponse response)throws AlipayApiException {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        try {
            response.getWriter().write("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String outTradeNo=params.get("out_trade_no");
        User user= userService.findAllByUserRechargeOrderNumber(outTradeNo);
        if (!outTradeNo.equals(user.getUserRechargeOrderNumber())) {
            throw new AlipayApiException("out_trade_no错误");
        }
        /*BigDecimal userMoney = user.getUserMoney();
        String totalAmount = params.get("total_amount");
        BigDecimal bigDecimal=new BigDecimal(totalAmount);
        if ((userMoney).compareTo(bigDecimal)!=0) {
            throw new AlipayApiException("error total_amount");
        }*/
        if (!params.get("app_id").equals(AlipayConfig.app_id)) {
            throw new AlipayApiException("app_id不一致");
        }
        String totalAmount = params.get("total_amount");
        BigDecimal bigDecimal=new BigDecimal(totalAmount);
        user.setUserMoney(bigDecimal);
        userService.update(user);
    }


    @RequestMapping(value = "/notifyVip",method = RequestMethod.POST)
    public void notifyVip(HttpServletRequest request, HttpServletResponse response)throws AlipayApiException {
        System.out.println("111111111111");
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        try {
            response.getWriter().write("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String outTradeNo=params.get("out_trade_no");
        User user= userService.findAllByUserRechargeVipOrderNumber(outTradeNo);
        System.out.println(user);
        if (!outTradeNo.equals(user.getUserRechargeVipOrderNumber())) {
            throw new AlipayApiException("out_trade_no错误");
        }
        /*BigDecimal userMoney = user.getUserMoney();
        String totalAmount = params.get("total_amount");
        BigDecimal bigDecimal=new BigDecimal(totalAmount);
        if ((userMoney).compareTo(bigDecimal)!=0) {
            throw new AlipayApiException("error total_amount");
        }*/
        if (!params.get("app_id").equals(AlipayConfig.app_id)) {
            throw new AlipayApiException("app_id不一致");
        }
        user.setUserStatue(1);
        userService.update(user);
    }
}