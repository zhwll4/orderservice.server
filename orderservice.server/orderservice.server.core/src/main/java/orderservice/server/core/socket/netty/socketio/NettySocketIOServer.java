package orderservice.server.core.socket.netty.socketio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PreDestroy;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;

import io.netty.util.internal.StringUtil;
import orderservice.server.core.socket.netty.BaseSocketServer;

public  class NettySocketIOServer extends BaseSocketServer{


	
	public NettySocketIOServer(String host, Integer port, int workThreads) {
		super(host, port, workThreads, null, null, null, null);
		// TODO Auto-generated constructor stub
	}
	
	public NettySocketIOServer(String host, Integer port, int workThreads, String sslpath, String ssltype,
			String password, String sslinstance) {
		super(host, port, workThreads, sslpath, ssltype, password, sslinstance);
		// TODO Auto-generated constructor stub
	}

	private SocketIOServer server ;
	
   
    
    
    public SocketIOServer getSocketIOServer() throws NoSuchAlgorithmException, IOException   
    {  
    	Configuration config = new Configuration();
    	
    	if(!StringUtil.isNullOrEmpty(host)){
    		config.setHostname(host);
    	}
    	
		config.setPort(port);
		
		if(!StringUtil.isNullOrEmpty(sslpath)){
			File  sslFile = new File(sslpath) ;
	        if(sslFile.exists()){
	        	config.setKeyStorePassword(password);
	        	InputStream stream = new FileInputStream(sslFile);
        	    config.setKeyStore(stream);
	        	
	        }
		}
		
		config.setWorkerThreads(workThreads);

		config.setAuthorizationListener(new AuthorizationListener() {
			public boolean isAuthorized(HandshakeData data) {
				return authorizedClient(data);	//其他安全验证策略，IP，Token，用户名
			}
		});
		/**
		 * 性能优化
		 */
		config.getSocketConfig().setReuseAddress(true);
		config.getSocketConfig().setSoLinger(0);
		config.getSocketConfig().setTcpNoDelay(true);
		config.getSocketConfig().setTcpKeepAlive(true);
		
		this.server =  new SocketIOServer(config);  
		
        return server;
    }
    
    protected boolean authorizedClient(HandshakeData data){
    	
    	return true;
    }
    
    @PreDestroy  
    public void destory() { 
		server.stop();
	}
}
