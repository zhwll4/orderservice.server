package orderservice.server.service.app.socketserver;

import org.springframework.util.StringUtils;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

import io.netty.util.internal.StringUtil;
import orderservice.server.core.basic.JsonUtils;
import orderservice.server.core.basic.TokenUtils;
import orderservice.server.core.form.UserResponseForm;
import orderservice.server.core.socket.netty.socketio.BaseSocketIOClientHandler;
import orderservice.server.core.socket.netty.socketio.NettySocketIOServer;
import orderservice.server.service.app.base.AppBasicDataContext;
import orderservice.server.service.app.model.AppUserLoginForm;
import orderservice.server.service.app.model.AppUserResponseForm;
import orderservice.server.service.app.model.AppUserTokenForm;

public class AppSocketIOEventHandler extends BaseSocketIOClientHandler {

	
	public AppSocketIOEventHandler(NettySocketIOServer socketIOServer) {
		// TODO Auto-generated constructor stub
		this.socketIOServer = socketIOServer;
	}
	

	@OnConnect
	public void onConnect(SocketIOClient client) {
		AppBasicDataContext.getAppLoger().info("有新的客户端连接....");
		this.scheduleLoginChecker(client, 30);
	}
	
	
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		SocketManager.removeClientToGroup(client);
	}
	@OnEvent(value = "ping")
	public void onHeartbeatPing(SocketIOClient client) throws Exception {
		
	}
	@OnEvent(value = "login")
	public void onLogin(SocketIOClient client, AckRequest request, String data) throws Exception {
		AppBasicDataContext.getAppLoger().info("收到数据...."+data);
		AppUserTokenForm uk = JsonUtils.parseObject(data, AppUserTokenForm.class);
		//UserResponseForm urf =null;
		if(uk==null){
			//urf =  new UserResponseForm(false,1,);
			sendLoginFail(client,"消息转换失败！");
			this.removeScheduleClient(client);
			return;
		}
		String userId = checkTokenUserId(uk.getToken());
		if(StringUtils.isEmpty(userId)
			||!userId.equals(uk.getUserid())){
			//urf =  new UserResponseForm(false,1,"消息转换失败！");
			sendLoginFail(client,"TOKEN鉴权失败或用户ID不符合！");
			this.removeScheduleClient(client);
			return;
		}else{
			AppUserResponseForm arf =  new AppUserResponseForm();
			SocketManager.sendMsgToClient(client, "login", arf);
		}
	}
	
	
	private String checkTokenUserId(String token){
		
		if(token==null)
		{
			return null;
		}
		AppUserLoginForm ulf = TokenUtils.getDatas(token, AppUserLoginForm.class);
		if(ulf==null){
			return null;
		}
		return ulf.getUserid();
	}
	
	private void sendLoginFail(SocketIOClient client,String msg){
		AppUserResponseForm urf = new AppUserResponseForm(false, 1, msg);
		SocketManager.sendMsgToClient(client, "login", urf);
	}
}
