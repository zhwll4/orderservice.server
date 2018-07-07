package orderservice.server.core.netty.socketio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.annotation.PreDestroy;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;

import io.netty.util.internal.StringUtil;

public class NettySocketIOServer {

    private String host;  
  
    private Integer port;
    
    private String sslpath;
    
    private int workThreads;
    
    private String systemPassword;
    
    private SocketIOServer server ;
    
    
    public NettySocketIOServer(String host,Integer port,String sslpath,int workThreads,String systemPassword){
    	this.host = host;
    	this.port = port;
    	this.sslpath = sslpath;
    	this.workThreads = workThreads;
    	this.systemPassword = systemPassword;
    }
    
    
    public SocketIOServer getSocketIOServer() throws NoSuchAlgorithmException, IOException   
    {  
    	Configuration config = new Configuration();
    	
    	if(!StringUtil.isNullOrEmpty(host)){
    		config.setHostname(host);
    	}
    	
		config.setPort(port);
		
		if(!StringUtil.isNullOrEmpty(sslpath)){
			File sslFile = new File(sslpath) ;
	        if(sslFile.exists()){
	        	Properties sslProperties = new Properties();
	        	FileInputStream in = new FileInputStream(sslFile);
	        	sslProperties.load(in);
	        	in.close();
	        	if(!StringUtil.isNullOrEmpty(sslProperties.getProperty("key-store")) && !StringUtil.isNullOrEmpty(sslProperties.getProperty("key-store-password"))){
	        		config.setKeyStorePassword(systemPassword);
	        	    InputStream stream = new FileInputStream(new File(sslpath , "ssl/"+sslProperties.getProperty("key-store")));
	        	    config.setKeyStore(stream);
	        	}
	        }
		}
		
		
		config.setWorkerThreads(workThreads);

		config.setAuthorizationListener(new AuthorizationListener() {
			public boolean isAuthorized(HandshakeData data) {
				return true;	//其他安全验证策略，IP，Token，用户名
			}
		});
		/**
		 * 性能优化
		 */
		config.getSocketConfig().setReuseAddress(true);
		config.getSocketConfig().setSoLinger(0);
		config.getSocketConfig().setTcpNoDelay(true);
		config.getSocketConfig().setTcpKeepAlive(true);
		
		SocketIOServer server =  new SocketIOServer(config);  
		
        return server;
    }
    
    
    @PreDestroy  
    public void destory() { 
		server.stop();
	}
}
