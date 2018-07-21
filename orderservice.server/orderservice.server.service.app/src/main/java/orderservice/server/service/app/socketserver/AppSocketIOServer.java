package orderservice.server.service.app.socketserver;

import com.corundumstudio.socketio.HandshakeData;

import orderservice.server.core.socket.netty.socketio.NettySocketIOServer;

public class AppSocketIOServer extends NettySocketIOServer {
	
	public AppSocketIOServer(String host, Integer port, int workThreads) {
		super(host, port, workThreads);
		// TODO Auto-generated constructor stub
	}

	public AppSocketIOServer(String host, Integer port, int workThreads, String sslpath, String ssltype,
			String password, String sslinstance) {
		super(host, port, workThreads, sslpath, ssltype, password, sslinstance);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean authorizedClient(HandshakeData data) {
		// TODO Auto-generated method stub
		//String
		
		
		
		return super.authorizedClient(data);
	}
	
	
	
}
