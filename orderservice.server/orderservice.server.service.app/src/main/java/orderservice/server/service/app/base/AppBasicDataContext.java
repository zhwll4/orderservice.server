package orderservice.server.service.app.base;

import org.apache.log4j.Logger;

import orderservice.server.core.basic.BasicDataContext;
import orderservice.server.core.basic.BasicUtils;

public class AppBasicDataContext extends BasicDataContext{

	
	public static Logger getAppLoger(){
		return BasicUtils.getLogger("THM-SERVER-APP");
	}
}
