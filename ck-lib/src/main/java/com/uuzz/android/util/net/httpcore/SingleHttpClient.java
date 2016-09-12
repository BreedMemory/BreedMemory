package com.uuzz.android.util.net.httpcore;

import com.uuzz.android.util.Common;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class SingleHttpClient extends DefaultHttpClient {
	private static HttpClient mHttpClient;
	private static Object lock = new Object();
	
	private SingleHttpClient(){
	}

	/**
	 * 描 述：HttpClient的简单封装，返回普通的对象或者单例对象<br/>
	 * 作者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
	 * @param isSingle 是否采用单例模式
	 * @return HttpClient对象
     */
	public static HttpClient getInstance(boolean isSingle){
		if(!isSingle){
			HttpClient lHttpClient = new SingleHttpClient();
			lHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Common.SO_TIMEOUT);
			lHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Common.CONNECTION_TIMEOUT);
			return lHttpClient;
		} else {
			synchronized (lock) {
				if(mHttpClient == null){
					mHttpClient = new SingleHttpClient();
				}
				mHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Common.SO_TIMEOUT);
				mHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Common.CONNECTION_TIMEOUT);
				return mHttpClient;
			}
		}
	}

	/**
	 * 描 述：获取一个https的httpclient，如果生成有异常则返回单例的http请求的httpclient实例<br/>
	 * 作 者：谌珂<br/>
	 * 历 史: (1.0.0) 谌珂 2016/9/12 <br/>
	 */
	public static HttpClient getHttpsInstance() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();

			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, RequestParams.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			HttpConnectionParams.setConnectionTimeout(params, Common.CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, Common.SO_TIMEOUT);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return getInstance(true);
		}
	}

	private static class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException,
				KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new HttpsTrustManager();

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
				throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

}
