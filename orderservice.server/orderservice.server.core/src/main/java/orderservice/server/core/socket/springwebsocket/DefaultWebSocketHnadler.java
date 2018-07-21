package orderservice.server.core.socket.springwebsocket;

import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import orderservice.server.core.basic.BasicDataContext;
import orderservice.server.core.basic.JsonUtils;
import orderservice.server.core.form.UserResponseForm;

public class DefaultWebSocketHnadler extends TextWebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String userId = getClientId(session);
		
		UserResponseForm ur =  new UserResponseForm();
		
		if (StringUtils.isEmpty(userId)) {
			ur.setIssuccess(false);
			ur.setFailcode(1);
			ur.setMsg("用户ID不正确！");
			session.sendMessage(new TextMessage(JsonUtils.stringify(ur)));
			
			session.close(CloseStatus.BAD_DATA);
			
		}else{
			
			DefaultSpringWebsocketManager.addClient(userId, session);
			
		}
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		
		
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		
		DefaultSpringWebsocketManager.removeClient(this.getClientId(session));
		
		if (session.isOpen()) {
			session.close();
		}
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		DefaultSpringWebsocketManager.removeClient(this.getClientId(session));
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 获取用户标识
	 * 
	 * @param session
	 * @return
	 */
	private String getClientId(WebSocketSession session) {
		try {
			return session.getAttributes().get(BasicDataContext.SessionPropsName.LOGINUSERID.toString()).toString();
		} catch (Exception e) {
			return null;
		}
	}
	
}
