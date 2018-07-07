package orderservice.server.core.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.netty.util.internal.StringUtil;
import orderservice.server.core.basic.BasicDataContext;


public class DefaultInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
        //获取Session
		//所有请求中的session必须有 登录判定值，正常流程都是先登录，再请求（登录时使用token登录，验证过后则不再使用TOKEN）
        HttpSession session = request.getSession();  
        String loginId = (String)session.getAttribute(BasicDataContext.SessionPropsName.LOGINUSERID.toString());  
          
        if(!StringUtil.isNullOrEmpty(loginId)){
            return super.preHandle(request, response, handler);  
        }
        
        doRedirect(request,response);
		return false;
	}
	


	protected void doRedirect(HttpServletRequest request,HttpServletResponse response){
		 try {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
