package orderservice.server.core.socket.springwebsocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import io.netty.util.internal.StringUtil;
import orderservice.server.core.basic.BasicDataContext;

public class DefaultSpringWebSocketInterceptor implements HandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
			HttpSession session = serverHttpRequest.getServletRequest().getSession();
			if (session != null&&session.getAttribute(BasicDataContext.SessionPropsName.LOGINUSERID.toString())!=null
					&&!StringUtil.isNullOrEmpty(session.getAttribute(BasicDataContext.SessionPropsName.LOGINUSERID.toString()).toString())) {
				map.put(BasicDataContext.SessionPropsName.LOGINUSERID.toString(), session.getAttribute(BasicDataContext.SessionPropsName.LOGINUSERID.toString()));
			}else{
				return false;
			}
		}
		return true;
	}

}
