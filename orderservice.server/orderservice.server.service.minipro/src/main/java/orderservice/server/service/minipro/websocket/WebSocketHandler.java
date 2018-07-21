package orderservice.server.service.minipro.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import orderservice.server.core.socket.springwebsocket.DefaultWebSocketHnadler;


/**
 * 微信小程序 只能使用 80和 443 ，所以暂时不能用netty,这里使用springwebsocket
 * 这里很多都是发送消息，所以一般不会有什么问题
 * @author zhwioio
 *
 */

@Service
public class WebSocketHandler extends DefaultWebSocketHnadler{

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		// TODO Auto-generated method stub
		
		String datas = message.getPayload();
		if("ping".equals(datas)){
			//心跳PING消息
			return;
		}
		
		
	}

	
	
	
}
