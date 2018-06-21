package com.api.demo;

//import common.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * HttpClient瀹為敓鏂ゆ嫹
 * @return
 * @throws ParseException
 * @throws IOException
 */
@SuppressWarnings("unused")
public class SimpleHttpClient_H5AliPay21 {
	private static String privateKey ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANtJ+k37mGqblItOapsfNnng1QImUZAr1VOy6n72xMHm8YmRoFHlQbLCUyM/1OJcq4LJPQRy5Bu7/d0vlDnmKU2w6XIJjNHQxU/mn8n4dYV4nJdWDhDEpwd+tqhA5e8AtRRYa0mY+dgY9qwjVfzsEs2EVsJY2FB+SjiltpUqLW4LAgMBAAECgYEAvilo5jdH9Sr1pdhamip3Eznlffe8w0k/tVVyBKf5+CxNSP8ACATG9D2P2wF/mszB7qiUQUdgiKx9eVHJOGxfY2Q+60WhcRJEGVpt1PpQxb5BgDrPZ8jS6sgbUmyuYgiYnlx5YA0vP8PIKk7jsXKeVy9SlQ4shkAtGwAgZyluEAECQQD8qB1CZYISph9d+umhIWjbETyk4YQLAllj1Z714A/wBEcbALhi9PioVKuuoonafuK/2TRcGwFgJbHnJ4TK5PovAkEA3jDULk2yGrYOYNPxK1a0w5DWrDPjrdjqhhhEpPcaNHZkWiNgLCX3tvB/Muh+Nh6KISGSKf5exbH0FxWEghH+5QJBAITfXNPoUmnAV8qzBF8rFNvwyrXjq++mQqCQXdLtDTSq60I2NMJGFudf8BIdhOYVyOYgMrslsKmB6Xepftq9ZR0CQBaYrqt62VcgFrxAMbGvoriU3VAsAsamXct/YlPIkZCuTaxhFqHGxreDI9yAin1kg7W03VGJ/gdq/qDuXkiAHgUCQCm1efxUs7FnpfSw2FnQsJbp7xA0tov7PU16pyLzf0wF9HnLmyIksMZtnY2f3nfoTICxWOZ8kjYVh5fRHMGer1M=";
	private static String algorithm="SHA256WithRSA";//MD5withRSA  SHA1WithRSA SHA256WithRSA


	public static String send(String url, TreeMap<String,String> map,String encoding) throws ParseException, IOException{  
		
		
		String body = "";  

		CloseableHttpClient client = HttpClients.createDefault();  
		HttpPost httpPost = new HttpPost(url);   
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
		if(map!=null){  
			for (Entry<String, String> entry : map.entrySet()) {  
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
			}  
		}  
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));  

		System.out.println("请求地址"+url);  
		System.out.println("请求参数"+nvps.toString());  

		CloseableHttpResponse response = client.execute(httpPost);  
		HttpEntity entity = response.getEntity();  
		if (entity != null) {  
			body = EntityUtils.toString(entity, encoding);  
		}  
		EntityUtils.consume(entity);  
		response.close();  
		return body;
	}  
	public static void main(String[] args) throws ParseException, IOException {
		String url="https://manager.jifenmy.com/zfs_trade_gw/order/orderCreate.do";
		TreeMap<String, String> map = new TreeMap<String, String>();  
		map.put("merchant_no","B00100100100101001");
		map.put("trade_type", "alipay_h5api");
		map.put("notify_url", "http://www.fastbank.net/Notify_Url.jsp");
		map.put("interface_version", "V1.0");
		map.put("client_ip","183.6.120.111");
		map.put("referer", "http://www.baidu.com");
		map.put("sign_type", "RSA");
		map.put("order_no", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+"ab");
		map.put("order_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("order_amount", "16");
		map.put("product_name", "123");
		map.put("product_code", "11");
		map.put("product_num", "11");
		map.put("product_desc", "12");
		map.put("scene_info", "12");
		map.put("extend_param", "335");
		String str = "";
		String dd = "";
		if(map!=null){  
			for (Map.Entry<String, String> entry : map.entrySet()) {
				dd=dd+ entry.getKey()+"="+entry.getValue()+"&";
				
			}
			System.out.println(dd.substring(0,dd.length()-1));
			str = dd.substring(0,dd.length()-1);

			System.out.println("签名报文："+str);
			try {
				String st = RSAWithSoftware.signByPrivateKey(str, privateKey);
				System.out.println("签名--------"+st);
				map.put("sign",st);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//String body = send(url, map,"utf-8");
		String result= new HttpClientUtil().doPost(url, map, "utf-8");		 	// 向快银支付发送POST请求
		//System.out.println("响应结果");
		System.out.println(result);
		//System.out.println(body);


	}
}