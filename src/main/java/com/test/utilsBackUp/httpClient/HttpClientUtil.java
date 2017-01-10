package com.test.utilsBackUp.httpClient;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParser;

/**
 * 
 * @Description:
 * @author woody
 * @date 2015年8月28日 下午5:33:45
 *
 */
public class HttpClientUtil {

	private static PoolingHttpClientConnectionManager connMgr;
	private static CloseableHttpClient httpClient;
	private static RequestConfig requestConfig;

	static {

		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		connMgr.setMaxTotal(40);
		connMgr.setDefaultMaxPerRoute(20);
		// 设置超时时间1s
		Builder configBuilder = RequestConfig.custom();
		configBuilder.setSocketTimeout(500);
		configBuilder.setConnectTimeout(500);
		configBuilder.setConnectionRequestTimeout(500);
		requestConfig = configBuilder.build();
	}

	/*
	 * 获取httpclient连接
	 */
	public static CloseableHttpClient getHttpClient() {

		return httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig)
				.build();
	}

	/**
	 * 
	 * @param url
	 *            "image/jpeg","application/Json"
	 * @return
	 */
	public static String sendHttpsGetUrl(String url) {

		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse response = null;
		HttpEntity entity = null;

		// 创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("SSL");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			SSLSocketFactory sf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", 443, sf);
			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			// 创建HttpPost
			HttpGet httpPost = new HttpGet(url);
			//httpPost.setHeader("content-type", contextType);
			// 执行POST请求
			response = httpClient.execute(httpPost);
			// 获取响应实体
			 entity = response.getEntity();
		 
			return EntityUtils.toString(entity); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			if (entity != null) {
				try {
					EntityUtils.toString(entity, "utf-8");
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				;
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 * @param contextType
	 *            "image/jpeg","application/Json"
	 * @return
	 */
	public static String sendHttpsGetUrl(String url, String contextType) {

		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse response = null;
		HttpEntity entity = null;

		// 创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("SSL");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			SSLSocketFactory sf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", 443, sf);
			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			// 创建HttpPost
			HttpGet httpPost = new HttpGet(url);
			httpPost.setHeader("content-type", contextType);
			// 执行POST请求
			response = httpClient.execute(httpPost);
			// 获取响应实体
			 entity = response.getEntity();
		 
			return EntityUtils.toString(entity); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			if (entity != null) {
				try {
					EntityUtils.toString(entity, "utf-8");
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				;
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/**
	 * HttpClient请求数据
	 * 
	 * @author gc.nie
	 * @param urlString
	 * @param paramMap
	 * @return
	 */
	public static String sendHttpPostUrl(String urlString, Map<String, String> paramMap) {

		HttpPost httpPost = new HttpPost(urlString);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			httpClient = getHttpClient();
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new UrlEncodedFormEntity(buildPostParam(paramMap), "utf-8"));
			response = httpClient.execute(httpPost);

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				String responseResult = "HttpClient请求失败。HTTP状态码：" + response.getStatusLine().getStatusCode();
				throw new Exception(responseResult);
			}
			entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8"); // response就是最后得到的结果
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (entity != null) {
				try {
					EntityUtils.toString(entity, "utf-8");
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				;
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static String sendHttpGetUrl(String urlStr, Map<String, String> paramMap) {

		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		httpClient = getHttpClient();
		String result = null;

		try {
			URIBuilder builder = buildUrlParam(paramMap, urlStr);
			httpGet = new HttpGet(builder.build());
			httpGet.setConfig(requestConfig);
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (entity != null) {
				try {
					entity.getContent().close();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
		}

		return result;
	}

	/**
	 * 
	 * @author
	 * @param paramMap
	 * @return
	 */
	private static List<NameValuePair> buildPostParam(Map<String, String> paramMap) {

		List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
		Set<Entry<String, String>> entrys = paramMap.entrySet();
		NameValuePair valuePair = null;
		for (Entry<String, String> entry : entrys) {
			valuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
			valuePairs.add(valuePair);
		}
		return valuePairs;
	}

	/**
	 * 
	 * @author
	 * @param paramMap
	 * @param urlStr
	 * @return
	 */
	private static URIBuilder buildUrlParam(Map<String, String> paramMap, String urlStr) {

		URIBuilder builder = null;
		try {
			builder = new URIBuilder(urlStr);
			if (paramMap != null && paramMap.size() > 0) {
				for (Entry<String, String> entry : paramMap.entrySet()) {
					builder.setParameter(entry.getKey(), entry.getValue());
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return builder;
		/*
		 * List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
		 * URIBuilder builder = new URIBuilder(YSCredit_API).
		 * setParameter("uid", YSCredit_USER_NAME). setParameter("api",
		 * YSCredit_API_NO). setParameter("args",
		 * "{\"name\":\""+userName+"\",\"cid\":\""+userIdCard+"\"}").
		 * setParameter("sign", signStr). setParameter("key", YSCredit_API_KEY);
		 * return valuePairs;
		 */
	}

	private static void enableSSL(DefaultHttpClient httpclient){  
        //调用ssl  
         try {  
                SSLContext sslcontext = SSLContext.getInstance("TLS");  
                sslcontext.init(null, new TrustManager[] { truseAllManager }, null);  
                SSLSocketFactory sf = new SSLSocketFactory(sslcontext);  
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
                Scheme https = new Scheme("https", sf, 443);  
                httpclient.getConnectionManager().getSchemeRegistry().register(https);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
    }  
	
	/** 
     * 重写验证方法，取消检测ssl 
     */  
    private static TrustManager truseAllManager = new X509TrustManager(){  
  
        public void checkClientTrusted(  
                java.security.cert.X509Certificate[] arg0, String arg1)  
                throws CertificateException {  
            // TODO Auto-generated method stub  
              
        }  
  
        public void checkServerTrusted(  
                java.security.cert.X509Certificate[] arg0, String arg1)  
                throws CertificateException {  
            // TODO Auto-generated method stub  
              
        }  
  
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
          
    }; 
 
}
