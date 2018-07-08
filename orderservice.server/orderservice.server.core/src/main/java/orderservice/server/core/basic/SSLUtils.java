package orderservice.server.core.basic;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class SSLUtils {

	public static SSLEngine createSSLEngine(String type, String path, String password, String SSLInstance,
			boolean isClientMode) throws Exception {
		SSLContext sslContext = createSSLContext(type,path,password,SSLInstance);
		SSLEngine engine = sslContext.createSSLEngine();
		engine.setUseClientMode(isClientMode);
		return engine;
	}

	public static SSLContext createSSLContext(String type, String path, String password, String SSLInstance)  throws Exception {
		KeyStore ks = KeyStore.getInstance(type); /// "JKS"
		InputStream ksInputStream = new FileInputStream(path); /// 证书存放地址
		ks.load(ksInputStream, password.toCharArray());
		// KeyManagerFactory充当基于密钥内容源的密钥管理器的工厂。
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());// getDefaultAlgorithm:获取默认的
		kmf.init(ks, password.toCharArray());
		// SSLContext的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。
		SSLContext sslContext = SSLContext.getInstance(SSLInstance);
		sslContext.init(kmf.getKeyManagers(), null, null);
		return sslContext;
	}
}
