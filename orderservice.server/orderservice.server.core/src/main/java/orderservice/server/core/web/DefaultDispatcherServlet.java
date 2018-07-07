package orderservice.server.core.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import orderservice.server.core.basic.BasicUtils;

/**
 * 
 * @author zhwioio
 *
 *仅仅继承方便以后增加功能
 *
 */
public class DefaultDispatcherServlet extends DispatcherServlet {
	
	@Override
	protected void onRefresh(ApplicationContext context) throws BeansException {
		super.onRefresh(context);
		BasicUtils.setCtx(context) ;
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		String prefix = config.getServletContext().getRealPath("/").replaceAll("/$", "");
		System.setProperty ("WORKDIR", prefix);
		WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
    }
}
