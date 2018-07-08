package orderservice.server.core.basic;

import java.util.Locale;


import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import orderservice.server.core.mini.MiniBasic;
import orderservice.server.core.mini.MiniException;


public class BasicUtils extends MiniBasic{

	private static ApplicationContext ctx;
	private MessageSource msgt=null;
	private static MessageSource msg=null;
	
	public static Locale resLocale = Locale.SIMPLIFIED_CHINESE;
	
	
	
	
	public static String getText(String name,String[] value)throws MiniException{
		
			String msgx=getMsg().getMessage(name, value,resLocale);
			if(msgx==null){
				throw new MiniException("没有找到属性对应的值："+name+"");
			}
			return msgx;
			
	}

	public static String getText(String name) throws MiniException {
		try{
			String msgx=getMsg().getMessage(name,null,resLocale);
			return msgx;
		}catch(Exception e){
			throw new MiniException("没有找到属性对应的值"+name);
		}
	}
	public static String getTextValue(String name,String defaultValue) {
		try{
			String msgx=getMsg().getMessage(name,null,resLocale);
			return msgx;
		}catch(Exception e){
			return defaultValue;
		}
	}
	public static MessageSource getMsg() {
		return ctx;
	}

	public static void setMsg(MessageSource msg) {
		BasicUtils.msg = msg;
	}

	public MessageSource getMsgt() {
		return msgt;
	}

	public void setMsgt(MessageSource msgt) {
		
		this.msgt = msgt;
		if(msg==null)
			msg=this.msgt;
	}
	
	public static ApplicationContext getCtx() {
		return ctx;
	}
	public static void setCtx(ApplicationContext ctx) {
		BasicUtils.ctx = ctx;
	}
	
	
}
