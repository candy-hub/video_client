package com.video.config;


import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;


public class AlipayConfig {

    public static String app_id = "2016101300674782";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCP5aE3h9XIg58UoK3JOWyz4WbCpkiksRz58+36nLoitxdCjf/2LZ/VLSMkU0A7KeEwPrDcH+6t2/6jAO7joL5C1G868tOQdin0NqCqUZmngD+ZSEfCzKto/O4y1DK8DgIs2tHdATppjFuWFdmo5Q8/kmZJukfwNjZtwE4ntdw2RU0agUhcm7hTA7KuHU7wpD066zp9eIkabSy1knjZ32u5F+AxdBWHUBVBxW0X3/S4n6bSCUNEiwGAESQvbMgN3D6OIWNTy7h2DCi7ePly3NiEIwzl2yrFcQiDKifp96zqBSsbpYxf10WKDJziMghODJOg4NcFFY3rU+xnf3zcP/JVAgMBAAECggEAHQ8/nopFByhlkPHeBrwdErqbWQXht7i45FV5otnR0T7uaJdqsJmIJLHn3eceGpSbkHWom9urBNtfsiV3GOsWYUKrb9koFo12bYY2rg6qnCQPM/Jf45wXpa0moYcu88FUeSB7r0kXNceNm2KwQSZ5DNqQ46TF8GHSXhmu7uAQJ7lJ1FkMb0zf93Sffd6pXRWPn24rJzbX2YUjfoflph837Mae5QMbrqcLkl5aDXJCvuI5X4ryGqyen7uOE0AW+7z4quvaXoxS0Q2X8etDfOFAxPf9pofh203ZZZFGp40RNty75ZduL1hYjYEEVbcbGUgKWlVgCAmPzQCYBAZzJWRCwQKBgQDtLC3MSsvLrsBD0+QssxuIqx5g+u5mlBFBviOrJWvryl0Vy5Et+uarAjjfovhBEDxP+huIcSO6mTPfEW4Qv3PWC70ctOQKp8I+GUOlaOyGaluPY3GhG0gfxH9IfVGszsvUl4yDASgnr8gxalNJHiyNvRT9a+FntKgS0zB/K5hekQKBgQCbUeeJmkm4S0abMhMVDbht5zazYM5a68XWgiaYDEnpgT1cIfR0RVomO0GmUN0ZKks7usXo/BYcAyi/rQzP87/tByZL38lD5cNFukQ7zLlp6D9TSy29+6MBedpG/QIOf6brMllzs2UEUr3bKGva5cVMbN0Txe6Jx1PNQ/M+1tRBhQKBgQDEEajO4eq2uQIDUNGqTmttwaE8pVWfAbt+cRUFsMsWQ9ZOZVrg+zHkn9KqtwyqpSRnLanDU10pLuwd9kfampvFkTDG4ZhAYs2HDtXIkgQvXw/Qa3KNsIMgmA5tvARgaxlA6ARmbPdoT76dc3vkz4gfqPgIRT4zwLrNzT+jckPlsQKBgFQCBeDIQa1bkbHpcKbHiARxtYVqC9LjiVsPhD5jDyssCvslJmopBKx/CnAhdqTg2lZRHDB7AJ5gGw8pUy+64Akc/9q0DNEbmfXB3JwXjAW7Z1zu4avLSgxHUwdoW09qPecikX1h/3ZKJoQ+Ja/87Sz8CMtkGDQPSsVQRmWrnpxdAoGACaOCc7hAcm8HGiLsZ0YUVYZ27yV4FBMOlmKdLs6jUPqPHyceGNb+y96CwniykaG6R9Sub2juu69lMBt5D7toxolqOGn4u3G2P7C0xvVOIZ+41DFTR6YL9/OmtKjSUGZFvEDQDbR99oytby9RIwCocVXyA3Muss73sAE5EUJTjKU=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1PkXywQHJhf2jZvLysHOAkP2RbHvAM6JCS+kB70EJ9vpvncdKy86S8GrGWD8fjcpqHVtbBgBM2BiSoLZ3mxxWTh21d3A6SYnsXb1BoGUcu2ezxde1zUVeZi/qwqdfhUQo/r7+8vU/mEqnrrA7ZWdYdKnQ0VXJuajNctSxbBuNdna1qXJQWjirE5zLwqMSqBO92vrTe0SF+qRazrLIUVsQ7WYgnS8rUr6rDsboJ4F45e4aFoVVv2TqvsFkLnWuwrfx5bdOb547WciTBXw4yvD7P6S7f/DNxn/eS6GpKWTI/Kvcyv2dAEoiikYxusEWkAw2f5ficX39Ik3ohAWSWG2ewIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://2720y0y328.qicp.vip/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8081/#/index";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}