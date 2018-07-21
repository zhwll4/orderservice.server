package orderservice.server.core.socket.netty.socketio;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.corundumstudio.socketio.SocketIOClient;
import orderservice.server.core.basic.BasicUtils;

/**
 * 
 * @author zhwioio
 *
 */
public class BaseSocketIOClientHandler {

	
	protected NettySocketIOServer socketIOServer;
	
	protected Map<String, ScheduledFuture<?>> scheduleMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();
	
	
	protected ScheduledFuture<?> scheduleLoginChecker(SocketIOClient client,int seconds){
		
		ScheduledFuture<?> loginSchedule = (ScheduledFuture<?>) BasicUtils.getScheduledThreadPool().
				schedule(new DefaultConnectionLoginChecker(client), seconds, TimeUnit.SECONDS);
		scheduleMap.put(client.getSessionId().toString(), loginSchedule);
		return loginSchedule;
	}
	
	protected void removeScheduleClient(SocketIOClient client){
		try {
			if(scheduleMap.containsKey(client.getSessionId().toString())){
				
				try {
					if(scheduleMap.get(client.getSessionId().toString())!=null){
						scheduleMap.get(client.getSessionId().toString()).cancel(true);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				scheduleMap.remove(client.getSessionId().toString());
				client.disconnect();
			}
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
