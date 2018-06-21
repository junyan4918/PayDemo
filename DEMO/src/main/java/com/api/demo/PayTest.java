package com.api.demo;
@SuppressWarnings("all")
public class PayTest {
public static void main(String[] args) {
	SimpleHttpClient_H5AliPay  paytest = new SimpleHttpClient_H5AliPay();
//	paytest.Notice();          //测试补通知
	paytest.orderCreate();         //创建订单
//	paytest.orderQuery();            //订单查询
//	paytest.QueryZZ();              //转账查询
//	paytest.OutQueryCreate();      // 创建转账
}
}
