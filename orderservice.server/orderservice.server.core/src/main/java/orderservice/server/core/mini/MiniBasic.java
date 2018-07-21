package orderservice.server.core.mini;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
 
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import io.netty.util.internal.StringUtil;

public class MiniBasic {

	
	private static Logger logger=null;
	
	public static Random random =new Random();
	
	private static ExecutorService defaultThreadPool ;
	private static ScheduledExecutorService scheduledThreadPool ;
	public static ConcurrentHashMap<String,Object> gloabMemData= new ConcurrentHashMap<String,Object>();
	
	
	public static  ScheduledExecutorService getScheduledThreadPool(){
		if(scheduledThreadPool== null){
			int scct = Runtime.getRuntime().availableProcessors()*2;
			scct = scct<=4?4:scct;
			scheduledThreadPool = Executors.newScheduledThreadPool(scct);
		 
		}
		return scheduledThreadPool;
	}
	
	
	public static void startNewThread(Runnable th){
		if(th==null){
			return;
		}
		if(defaultThreadPool==null){
			defaultThreadPool= Executors.newCachedThreadPool();
		}
		defaultThreadPool.execute(th);
	}
	
	
	public static void stopAllThread(int sec){
		
		try {  
			defaultThreadPool.shutdown();  
	        if(!defaultThreadPool.awaitTermination(sec, TimeUnit.SECONDS)){  
	        	defaultThreadPool.shutdownNow();  
	        }  
	    } catch (InterruptedException e) { 
	        defaultThreadPool.shutdownNow();  
	    }  
	   
		
	}
	
	
	
	
	public static Logger getLogger(){
		if(logger==null){
			logger = Logger.getLogger("THM-SERVER") ;
		}
		return logger;
	}
	public static Logger getLogger(String name){
		if(name==null){
			name = "THM-SERVER";
		}
		return Logger.getLogger(name) ;
	}
	
	public static void Validate(String fieldType,String value) throws MiniException {
		
		if(value==null){
			throw new MiniException("值为NULL");
		}
		
		if(!StringUtil.isNullOrEmpty(fieldType))
		{
			if(fieldType.equals("int")){
				try{
					if(!value.matches("[-]?\\d+$")){
						throw new MiniException(value+"不能转换为INT");
					}
				}catch(Exception e){
					throw new MiniException(value+"转换为INT失败，"+e.getMessage());
				}
			}
			else if(fieldType.equals("float")){
				try{
					if(!value.matches("[-]?\\d+$|[-]?\\d+[.]\\d+$")){
						throw new MiniException(value+"不能转换为FLOAT");
					}
				}catch(Exception e){
					throw new MiniException(value+"转换为FLOAT失败，"+e.getMessage());
				}
			}
			else if(fieldType.equals("regex")){
				try{
					if(!value.matches(fieldType)){
						throw new MiniException(value+"不符合规则");
					}
				}catch(Exception e){
					throw new MiniException("判断"+value+"是否符合规则时失败，"+e.getMessage());
				}
			}
		}
	}
	
	public static Date getDate(String format) throws MiniException{
		if(format==null){
			format="yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formate = new SimpleDateFormat(format);
		Date date=null;
		try {
			date=formate.parse(formate.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new MiniException("将当前日期格式化为"+format+"失败，"+e.getMessage());
		}
		return date;
	}
	
	public static Date getDate(Date date,String format) throws MiniException{
		if(StringUtil.isNullOrEmpty(format)){
			format="yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formate = new SimpleDateFormat(format);
		try {
			date=formate.parse(formate.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new MiniException("将"+date+"格式化为"+format+"失败，"+e.getMessage());
		}
		return date;
	}	


	public static Date getDate(String date,String format) throws MiniException{
		if(format==null){
			format="yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formate = new SimpleDateFormat(format);
		try{
			return formate.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new MiniException("将"+date+"格式化为"+format+"失败，"+e.getMessage());
		}
	}
	
	public static Long get13Date(String date) throws MiniException
	{
		return get13Date(getDate(date,"yyyy-MM-dd HH:mm:ss"));
	}
	public static Long get13Date(Date date)
	{
		if(date==null){
			return System.currentTimeMillis();
		}
		return date.getTime();
	}
	
	public static String getStringDateNow(){
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formate.format(new Date());
	}
	public static String getStringDateNow(String format){
		SimpleDateFormat formate = new SimpleDateFormat(format);
		return formate.format(new Date());
	}
	public static String getStringDate(Date date,String format){
		SimpleDateFormat formate = new SimpleDateFormat(format);
		return formate.format(date);
	}
	public static String getStringDate(Date date){
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formate.format(date);
	}
	
	/**
	 * 
	 * @param date 日期
	 * @param type Calendar.DATE
	 * @return
	 */
	public static Date getDateAdd(Date date,int type,int count){		
	     Calendar   calendar   =   new   GregorianCalendar(); 
	     calendar.setTime(date); 
	     calendar.add(type,count);
	     return calendar.getTime();   
	}
	/**
	 * datea-dateb
	 * @param datea
	 * @param dateb
	 * @param type Calendar.DATE
	 * @return
	 */
	public static long getDateSub(Date datea,Date dateb,int type){		
	     
		long a =datea.getTime();
		long b = dateb.getTime();
		
		a=a-b;
		
		switch(type){
			case Calendar.DATE:
				a=a/(1000 * 60 * 60 * 24);
				break;
			case Calendar.HOUR:
				a=a/(1000 * 60 * 60);
				break;
			case Calendar.MINUTE:
				a=a/(1000 * 60);
				break;
			case Calendar.SECOND:
				a=a/(1000);
				break;
			default:
				break;
		}
		return a;
	}
	
	
	public static String createId(String curId,int length)
	{
		curId=curId==null?"0":curId;
		int nextid = Integer.parseInt(curId)+1;
		String id = Integer.toString(nextid);
		String nextId="";
		for(int i=0;i<length-(id.length());i++)
		{
			nextId = nextId+"0";
		}
		nextId = nextId+id;
		return nextId;
	}
	
public static Object copyUnits(Object srcClass,Object dstClass,String nocopyGetMethods,boolean errorIsContinue) throws MiniException{
		
	
		if(srcClass!=null){
			Class copy=srcClass.getClass();
			Method[] copyM=copy.getMethods();
			
			if(dstClass instanceof Class){
				try {
					dstClass=((Class)dstClass).newInstance();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new MiniException("实例化失败："+e.getMessage());
				} 
			}
			Class past=dstClass.getClass();
			for(int i=0;i<copyM.length;i++){
				String methodName=copyM[i].getName();
				if(methodName.startsWith("get")&&(methodName+";").indexOf(nocopyGetMethods)<0){
					methodName=methodName.replaceFirst("get", "set");
					Object ox;
					try {
						ox = copy.getMethod(copyM[i].getName(), null).invoke(srcClass, null);
						past.getMethod(methodName,copyM[i].getReturnType()).invoke(dstClass, ox);
					} catch (Exception e) {
						if(errorIsContinue){
							showLoggerInfo("复制"+past+"的方法："+methodName+"失败，但是设置为错误时继续。"+e.getMessage());
							continue;
						}else{
							
							throw new MiniException("复制方法："+methodName+"失败，"+e.getMessage());
						}
					}
				}
			}
			return dstClass;
		}else{
			return dstClass;
		}
	}
	
	public static Object copyUnits(Object srcClass,Object dstClass,boolean errorIsContinue) throws MiniException{
		return copyUnits(srcClass,dstClass,"getClass;getCallbacks;getHibernateLazyInitializer;getCallback;",errorIsContinue);
	}
	
	public static List copyList(List cList,Object dstClass) throws MiniException{
		List<Object> rList=new ArrayList<Object>();
		Class past=dstClass.getClass();
		for(Object o:cList){
			try {
				dstClass=past.newInstance();
				rList.add(copyUnits(o,dstClass,false));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new MiniException(e.getMessage());
			} 
		}
		return rList;
	}
	
	public static void showLoggerInfo(String msg){
		if(logger!=null){
			logger.info(msg);
		}
		
	}
	public static void showLoggerDebug(String msg){
		if(logger!=null){
			logger.debug(msg);
		}
	}
	
	
	public static int[] getResultByPage(int pageSize,int pageNo,List list){
		if (pageSize < 1) {
			pageSize = 50;
		}
		int i = list.size();
		int[] objs = new int[3];
		objs[1] = (i + pageSize - 1) / pageSize;
		if (pageNo > (Integer) objs[1]) {
			pageNo = (Integer) objs[1];
		}
		int index = 0;
		if (pageNo <= 1) {
			index = 0;
		} else {
			index = (pageNo - 1) * pageSize;
		}
		objs[0] = index;
		objs[2]=pageSize;
		return objs;
	}
	
	
}
