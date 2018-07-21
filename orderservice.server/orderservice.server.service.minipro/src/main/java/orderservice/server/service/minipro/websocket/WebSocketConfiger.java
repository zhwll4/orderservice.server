package orderservice.server.service.minipro.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import orderservice.server.core.socket.springwebsocket.BaseSpringWebSocketConfig;

@Configuration
@EnableWebSocket
public class WebSocketConfiger extends BaseSpringWebSocketConfig{

}
