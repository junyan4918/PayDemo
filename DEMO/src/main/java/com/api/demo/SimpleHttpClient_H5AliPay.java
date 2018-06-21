package com.api.demo;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;            
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;

import org.apache.commons.io.output.ByteArrayOutputStream;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import net.sf.json.xml.XMLSerializer;

/**
 * HttpClient客户端请求
 * @param url
 * @param map
 * @param encoding
 * @return
 * @throws ParseException
 * @throws IOException
 */
@SuppressWarnings("all")
public class SimpleHttpClient_H5AliPay {
	private static final int MAX_DECRYPT_BLOCK = 0;
	private static final String KEY_ALGORITHM = null;
	private static String privateKey ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANtJ+k37mGqblItOapsfNnng1QImUZAr1VOy6n72xMHm8YmRoFHlQbLCUyM/1OJcq4LJPQRy5Bu7/d0vlDnmKU2w6XIJjNHQxU/mn8n4dYV4nJdWDhDEpwd+tqhA5e8AtRRYa0mY+dgY9qwjVfzsEs2EVsJY2FB+SjiltpUqLW4LAgMBAAECgYEAvilo5jdH9Sr1pdhamip3Eznlffe8w0k/tVVyBKf5+CxNSP8ACATG9D2P2wF/mszB7qiUQUdgiKx9eVHJOGxfY2Q+60WhcRJEGVpt1PpQxb5BgDrPZ8jS6sgbUmyuYgiYnlx5YA0vP8PIKk7jsXKeVy9SlQ4shkAtGwAgZyluEAECQQD8qB1CZYISph9d+umhIWjbETyk4YQLAllj1Z714A/wBEcbALhi9PioVKuuoonafuK/2TRcGwFgJbHnJ4TK5PovAkEA3jDULk2yGrYOYNPxK1a0w5DWrDPjrdjqhhhEpPcaNHZkWiNgLCX3tvB/Muh+Nh6KISGSKf5exbH0FxWEghH+5QJBAITfXNPoUmnAV8qzBF8rFNvwyrXjq++mQqCQXdLtDTSq60I2NMJGFudf8BIdhOYVyOYgMrslsKmB6Xepftq9ZR0CQBaYrqt62VcgFrxAMbGvoriU3VAsAsamXct/YlPIkZCuTaxhFqHGxreDI9yAin1kg7W03VGJ/gdq/qDuXkiAHgUCQCm1efxUs7FnpfSw2FnQsJbp7xA0tov7PU16pyLzf0wF9HnLmyIksMZtnY2f3nfoTICxWOZ8kjYVh5fRHMGer1M=";  
	private static String algorithm="SHA256WithRSA";//MD5withRSA  SHA1WithRSA SHA256WithRSA  
	private static String publickey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUEVG6pSxF5TzDIMbwkqjSC/R794rZSNuVEtq6tcZ6s6/xBWm0Xyal80b8KhcWYjWgEAR4kIk8VT6fGColTw00UlRJc+BuJi9jdC6GUDQwH9YJqoHHLYsmTvXY+Wmi+sd1FRTd5ViGGrLrqgKkf5ggZpCwe7WZLIt2uSxp1XQVAQIDAQAB";
	/**
	 * 发送HttpPost请求
	 * @param url
	 * @param map
	 * @param encoding
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String send(String url, TreeMap<String,String> map,String encoding) throws IOException{
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
	/**
	 * 下单接口
	 */
	public static void orderCreate(){
		try {
			String url="https://manager.jifenmy.com/zfs_trade_gw/order/orderCreate.do";
			TreeMap<String, String> map = new TreeMap<String, String>();  
			map.put("merchant_no","B00100100100101001");
			map.put("trade_type", "alipay_h5api");
			map.put("notify_url", "http://www.fastbank.net/Notify_Url.jsp");
			map.put("interface_version", "V1.0");
			map.put("client_ip","183.6.120.111");
			map.put("referer", "http://www.baidu.com");
			map.put("sign_type", "RSA");
			map.put("order_no",new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+"37");
			map.put("order_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			map.put("order_amount", "16");
//			map.put("referer", "http://www.baidu.com");		
			map.put("product_name", "1t");
			map.put("product_code", "123");
			map.put("product_num", "132");
			map.put("product_desc", "132");
			map.put("scene_info", "12");
			map.put("extend_param","132");
			String str = "";
			String formap = "";
			if(map!=null){  
				for (Map.Entry<String, String> entry : map.entrySet()) {  
					formap=formap+ entry.getKey()+"="+entry.getValue()+"&";
				}
				System.out.println(formap.substring(0,formap.length()-1));
				str = formap.substring(0,formap.length()-1);
				try {
					String sign = RSAWithSoftware.signByPrivateKey(str, privateKey);
					map.put("sign",sign);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String body = send(url, map,"utf-8");
			String result =xml2json.xml2json(body);
			System.out.println("响应结果");  
			System.out.println(body);  
			System.out.println("响应由XML转换成json格式："+result);

//			JSONObject j = JSON.parseObject(result);
//
//			System.out.println(j.toJSONString());
//
//			net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(j.toJSONString());
//			String appid = json.getString("response");
//
//			net.sf.json.JSONObject json1 = net.sf.json.JSONObject.fromObject(appid);
//			String appid1 = json1.getString("data");
//
//			net.sf.json.JSONObject json2 = net.sf.json.JSONObject.fromObject(appid1);
//			String sign = json2.getString("sign");
//
//			net.sf.json.JSONObject jsondata = net.sf.json.JSONObject.fromObject(appid1);
//			String order_no = jsondata.getString("order_no");
//
//			net.sf.json.JSONObject merchant_no_data = net.sf.json.JSONObject.fromObject(appid1);
//			String merchant_no = merchant_no_data.getString("merchant_no");
//
//			net.sf.json.JSONObject trade_time_data = net.sf.json.JSONObject.fromObject(appid1);
//			String trade_time = trade_time_data.getString("trade_time");
//
//			net.sf.json.JSONObject resp_desc_data = net.sf.json.JSONObject.fromObject(appid1);
//			String resp_desc = resp_desc_data.getString("resp_desc");
//
//			net.sf.json.JSONObject interface_version_data = net.sf.json.JSONObject.fromObject(appid1);
//			String interface_version = interface_version_data.getString("interface_version");
//
//			net.sf.json.JSONObject order_time_data = net.sf.json.JSONObject.fromObject(appid1);
//			String order_time = order_time_data.getString("order_time");
//
//			net.sf.json.JSONObject pay_url_data = net.sf.json.JSONObject.fromObject(appid1);
//			String pay_url = pay_url_data.getString("pay_url");
//
//			net.sf.json.JSONObject resp_code_data = net.sf.json.JSONObject.fromObject(appid1);
//			String resp_code = resp_code_data.getString("resp_code");
//
//			net.sf.json.JSONObject trade_no_data = net.sf.json.JSONObject.fromObject(appid1);
//			String trade_no = trade_no_data.getString("trade_no");
//
//			net.sf.json.JSONObject sign_type_date = net.sf.json.JSONObject.fromObject(appid1);
//			String sign_type = sign_type_date.getString("sign_type");
//
//			net.sf.json.JSONObject order_amount_date = net.sf.json.JSONObject.fromObject(appid1);
//			String order_amount = sign_type_date.getString("order_amount");
//
//			
//			//{"response":{"rspInf":"操作成功","data":{"order_no":2018060421135542237,"merchant_no":"B00100100100101001","trade_time":"2018-06-04 21:13:47","resp_desc":"订单创建成功","sign":"fcR+hsZ7uw0c41krQV2aAW271ko0eYVbaychWnz0QKozbwrZKPzNfc7r5RmQ3tGfWw8ZmDEA9Fr4jw7KDCJ5iTjLCv8R1FjFrZwe1jt8jjvpt94eb+yb5bmW2pIHKioVsG0sBY0y3I3uK+Kt3WWFUT8bFeKF4zEXMRPccXrpIFM=","interface_version":"V1.0","order_time":"2018-06-04 21:13:55","pay_url":"HTTPS://QR.ALIPAY.COM/FKX02391ZZK5X5ZQHQRH0E","resp_code":"SUCCESS","trade_no":212201806042113473267,"sign_type":"RSA"},"v":1,"responseTm":20180604211348,"rspCd":"00000","rspType":0}}
//
//			TreeMap<String, String> treeMap = new TreeMap<String, String>();
//			treeMap.put("interface_version",interface_version);
//			treeMap.put("merchant_no",merchant_no);
//			treeMap.put("order_no",order_no);
//			treeMap.put("order_time",order_time);
//			treeMap.put("pay_url", pay_url);
//			treeMap.put("order_amount",order_amount);
//			treeMap.put("resp_code",resp_code);
//			treeMap.put("resp_desc",resp_desc);
//			treeMap.put("sign_type",sign_type);
//			treeMap.put("trade_no", trade_no.toString());
//			System.out.println(trade_no.toString());
//			System.out.println();
//			treeMap.put("trade_time",trade_time);
//			String validate = "";
//			String valisigndate="";
//			if(treeMap!=null){  
//				for (Map.Entry<String, String> entry : treeMap.entrySet()) {  
//					validate=validate+ entry.getKey()+"="+entry.getValue()+"&";
//				}
//				valisigndate = validate.substring(0,validate.length()-1);
//			}
//			RSAWithSoftware mh = new RSAWithSoftware();
//			boolean mag=mh.validateSignByPublicKey(valisigndate,publickey,sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 代付
	 */
	public static void OutQueryCreate() {
		try {
			String url="https://manager.jifenmy.com/zfs_trade_gw/order/outOrderCreate.do";
			TreeMap<String, String> map = new TreeMap<String, String>();  
			map.put("interface_version", "V1.0");
			map.put("merchant_no","B00100100100101001");
			map.put("bank_code","中国建设银行");
			map.put("sign_type","RSA-S");
			map.put("account_no", "614545");
			map.put("province", "湖北省");
			map.put("city", "襄阳市");
			map.put("amount", "1");
			map.put("account_name","吴丰");
			map.put("payee_mobile","15071500381");
			map.put("remark", "dsadsa");
			map.put("order_no",new SimpleDateFormat("yyyyMMddHHssSSS").format(new Date()));
			String str = "";
			String formap = "";
			if(map!=null){  
				for (Map.Entry<String, String> entry : map.entrySet()) {  
					formap=formap+ entry.getKey()+"="+entry.getValue()+"&";
				}
				System.out.println(formap.substring(0,formap.length()-1));
				str = formap.substring(0,formap.length()-1);
				try {
					String sign = RSAWithSoftware.signByPrivateKey(str, privateKey);
					map.put("sign",sign);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String body = send(url, map,"utf-8");
			System.out.println("响应结果");  
			System.out.println(body);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//代付查询
	public static void QueryZZ() {
		try {
			String url="https://manager.jifenmy.com/zfs_trade_gw/order/queryOutOrder.do";
			TreeMap<String, String> map = new TreeMap<String, String>();  
			map.put("interface_version", "V1.0");
			map.put("merchant_no","B00100100100101001");
			map.put("order_no","201806042139091");
			map.put("sign_type","RSA-S");
			map.put("trade_no","1003213201806062135130012");
			String str = "";
			String formap = "";
			if(map!=null){  
				for (Map.Entry<String, String> entry : map.entrySet()) {  
					formap=formap+ entry.getKey()+"="+entry.getValue()+"&";
				}
				System.out.println(formap.substring(0,formap.length()-1));
				str = formap.substring(0,formap.length()-1);
				try {
					String sign = RSAWithSoftware.signByPrivateKey(str, privateKey);
					map.put("sign",sign);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String body = send(url, map,"utf-8");
			System.out.println("响应结果");  
			System.out.println(body);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 订单查询接口
	 */
	public static void orderQuery(){
		try {
			String url="https://manager.jifenmy.com/zfs_trade_gw/order/orderQuery.do";
			TreeMap<String, String> map = new TreeMap<String, String>();  
			map.put("interface_version", "V1.0");
			map.put("merchant_no","B00100100100101001");
			map.put("order_no","2018052918140261637");
			map.put("sign_type","RSA-S");
			map.put("trade_no","213201805291814000108");
			String str = "";
			String formap = "";
			if(map!=null){  
				for (Map.Entry<String, String> entry : map.entrySet()) {  
					formap=formap+ entry.getKey()+"="+entry.getValue()+"&";
				}
				System.out.println(formap.substring(0,formap.length()-1));
				str = formap.substring(0,formap.length()-1);
				try {
					String sign = RSAWithSoftware.signByPrivateKey(str, privateKey);
					map.put("sign",sign);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String body = send(url, map,"utf-8");
			System.out.println("响应结果");  
			System.out.println(body);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Notice() {
		try {
			//测试补通知
			String url="http://www.bbpay6.com/Pay_JHWX_notifyurl.html";
			TreeMap<String, String> map = new TreeMap<String, String>();  
			map.put("interface_version", "V1.0");
			map.put("merchant_no","B00100100100101214");
			map.put("order_amount","50");
			map.put("order_no","20186073963508819213");
			map.put("order_time","2018-06-07 10:06:15");
			map.put("sign_type","RSA");
			map.put("trade_no", "213201806071047155254");
			map.put("trade_status", "SUCCESS");
			map.put("trade_type", "alipay_h5api");
			//			map.put("extra_param","");

			StringBuilder stringBuilder = new StringBuilder();
			Set<Map.Entry<String, String>> entries = map.entrySet();
			for (Map.Entry<String, String> entry : entries) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (null != value) {
					stringBuilder.append("&").append(key).append("=").append(value);
				}
			}
			String toSignValue = stringBuilder.substring(1).toString();
			System.out.println(toSignValue);
			try {
				String signByPrivateKey = RSAWithSoftware.signByPrivateKey(toSignValue,privateKey);
				System.out.println("signByPrivateKey:"+signByPrivateKey);
				map.put("sign", signByPrivateKey);
			} catch (Exception e) {
//				TransactionLogger.error("sign error , " + Throwables.getStackTraceAsString(e));
			}
//			String sign  = "dEwBYM7EJs0wD0XCJAt++FoNZiLmarHLO3nLrS49gfIjtqzlDBkIpIeJAHlRfBRS6FqjkbsEIMGgv8EJ8pKWzBvFmPssEqG4ATzDQiql9w3eZyEYJSyUei+ZUFCIzU68tytFdr4xP2+n3aT0+mjD6Um4wJGVD0c4WDbPG8+mowk=";
//			RSAWithSoftware mh = new RSAWithSoftware();
//			boolean mag=mh.validateSignByPublicKey(toSignValue,publickey,sign);
			String body = send(url, map,"utf-8");
			System.out.println("响应结果");  
			System.out.println(body);  
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		try {
			//读取本地证书
			//System.setProperty("javax.net.ssl.trustStore","E:\\certificate.keystore");
			orderCreate(); 
//			Notice();
//			orderQuery();
//			OutQueryCreate();
//			QueryZZ();
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
}