package orderservice.server.test;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

	private static ExecutorService defaultThreadPool = Executors.newFixedThreadPool(12);
	
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
		
		
		//new TestTkoen().scheduleLoginChecker(null,1);
		
		TestTkoen t = new TestTkoen();
		
		
		for(int i=1;i<100;i++){
			t.startNewThread(i);
		}
		
	}
	
	protected void startNewThread(int i){
		defaultThreadPool.execute(new WaitForInput(i));
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
	
	protected class WaitForInput implements Runnable{
		
		int idnex;
		
        public WaitForInput(int idnex) {
            // TODO Auto-generated constructor stub
        	this.idnex = idnex;
        }
        
        @Override
        public void run() {
            // TODO Auto-generated method stub
        	Scanner input = new Scanner(System.in);
        	String val = null;       // 记录输入的字符串
            do{
                System.out.print(""+idnex+"、请输入：");
                val = input.next();       // 等待输入值
                System.out.println("您输入的是："+val);
            }while(!val.equals("#"));   // 如果输入的值不是#就继续输入
            System.out.println("你输入了\"#\"，程序已经退出！");
            input.close(); // 关闭资源
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
