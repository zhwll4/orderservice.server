package orderservice.server.service.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.netty.util.internal.StringUtil;
import orderservice.server.core.basic.BasicDataContext;
import orderservice.server.core.web.DefaultInterceptor;

public class APPInterceptor extends DefaultInterceptor {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return super.preHandle(request, response, handler);
       
	}
	
}
