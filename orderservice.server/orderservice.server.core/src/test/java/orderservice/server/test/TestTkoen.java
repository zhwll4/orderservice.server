package orderservice.server.test;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.interfaces.Claim;
import com.corundumstudio.socketio.SocketIOClient;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import orderservice.server.core.basic.BasicUtils;
import orderservice.server.core.basic.JsonUtils;
import orderservice.server.core.basic.TokenUtils;
import orderservice.server.core.po.Test1;

public class TestTkoen {

	public static void main(String[] args) {
		
//		TestF p = new TestF();
//		System.out.println("token:" + JsonUtils.stringify(p,Include.NON_NULL));
//		
//		String token = TokenUtils.createToken(p, 10000);
//
//		System.out.println("token:" + token);
//
//		Map<String, Claim> m = TokenUtils.verifyToken(token);
//
//		if (m == null) {
//			System.out.println("校验失败~~~");
//		} else {
//			System.out.println("校验成功~~~");
//		}
//		String datas2 = JsonUtils.stringify(TokenUtils.getDatas(token,String.class));
//		System.out.println(datas2);
//		
//		System.out.println(JsonUtils.stringify(JsonUtils.parseObject(datas2, TestF.class)));
		
		
		new TestTkoen().scheduleLoginChecker(null,1);
		
	}
	
	protected ScheduledFuture<?> scheduleLoginChecker(SocketIOClient client,int seconds){
		
		ScheduledFuture<?> loginSchedule = (ScheduledFuture<?>) BasicUtils.getScheduledThreadPool().
				schedule(new DefaultConnectionLoginChecker(client), seconds, TimeUnit.SECONDS);
		return loginSchedule;
	}
	
	protected void removeScheduleClient(SocketIOClient client){
		try {
			System.out.println("ssssssss");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 	
	protected class DefaultConnectionLoginChecker implements Runnable{
		
		SocketIOClient client;
		
        public DefaultConnectionLoginChecker(SocketIOClient client) {
            // TODO Auto-generated constructor stub
        	this.client = client;
        }
        
        @Override
        public void run() {
            // TODO Auto-generated method stub
        	removeScheduleClient(client);
        }
        
    }
	
}
class TestF{
	
	boolean T1 = false;
	String T2 = "xxx";
	public boolean isT1() {
		return T1;
	}
	public void setT1(boolean t1) {
		T1 = t1;
	}
	public String getT2() {
		return T2;
	}
	public void setT2(String t2) {
		T2 = t2;
	}
	
	
}
