package orderservice.server.service.app.socketserver;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIONamespace;

import orderservice.server.core.basic.BasicUtils;
import orderservice.server.core.socket.netty.socketio.NettySocketIOServer;
import orderservice.server.service.app.base.AppBasicDataContext;

@Service
public class StartAPPSocketIOServer {
	
	private final static String spaceName= "/app/service";
	
	
    @Value("${netty.socketio.server.host}")  
    private String host;  
	 
    @Value("${netty.socketio.server.port}")
    private Integer port;
    
    @Value("${netty.socketio.server.workThreads}")
    private int workThreads;
	
    @Value("${netty.socketio.server.sslpath}")
    private String sslpath;
    
    @Value("${netty.socketio.server.ssltype}")
    private String ssltype;
    
    @Value("${netty.socketio.server.password}")
    private String password;
    
    @Value("${netty.socketio.server.sslinstance}")
    private String sslinstance;
	
    
    private NettySocketIOServer socketIOServer;
    private SocketIONamespace serverNameSpace;
    
    
	@Bean
    public NettySocketIOServer startSocketServer(){
    	
		
		AppBasicDataContext.getAppLoger().info("*****开始启动APP NETTY SOCKET 服务器*****"+host+":"+port+":"+workThreads);
		
		socketIOServer =  new NettySocketIOServer(host, port, workThreads, sslpath, ssltype, password, sslinstance);
		
		try {
			
			serverNameSpace = socketIOServer.configSocketIOServer().addNamespace(spaceName);
			serverNameSpace.addListeners(new AppSocketIOEventHandler(socketIOServer));
			socketIOServer.startServer();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AppBasicDataContext.getAppLoger().info("--------------启动APP NETTY SOCKET 服务器 失败-----------");
			e.printStackTrace();
		} 
		AppBasicDataContext.getAppLoger().info("*****APP NETTY SOCKET 服务器  已经启动*****");
		return socketIOServer;
		
    }
	
    @PreDestroy
    public void stopSocketServer(){
    	if(socketIOServer!=null){
    		socketIOServer.stopServer();
    	}
    	AppBasicDataContext.getAppLoger().info("*****APP NETTY SOCKET 服务器  已经关闭*****");
    }
}
