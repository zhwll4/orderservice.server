package orderservice.server.core.socket.springwebsocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


public class BaseSpringWebSocketConfig implements WebSocketConfigurer {

	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(getHandler(), getHandlerName()).addInterceptors(new DefaultSpringWebSocketInterceptor());
	}
	
	public WebSocketHandler getHandler(){
		return new DefaultWebSocketHnadler();
	}
	
	public  String getHandlerName(){
		return "/miniprosocket";
	}
}
