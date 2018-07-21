package orderservice.server.core.socket.springwebsocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;


@Service
public class DefaultSpringWebsocketManager {

	private final static Map<String,WebSocketSession> clientsMap = new ConcurrentHashMap<String,WebSocketSession>();
	
	public static Map<String,WebSocketSession> getClients(){
		return clientsMap;
	}
	
	public static void addClient(String userId,WebSocketSession session){
		
		getClients().put(userId, session);
		
	}
	
	public static void removeClient(String userId){
		if(!StringUtils.isEmpty(userId)
				&&getClients().containsKey(userId)){
			getClients().remove(userId);
		}
	}
	
	
	public static boolean sendMsgToClient(String userId,WebSocketMessage<?> msg){
		
		if(!getClients().containsKey(userId)){
			return false;
		}
		try {
			getClients().get(userId).sendMessage(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	public static List<String> broadMsgToClients(WebSocketMessage<?> msg){
		List<String> failClients = new ArrayList<String>();
		
		Set<String> userIds = getClients().keySet();
        WebSocketSession session = null;
        for (String userId : userIds) {
            try {
                session = getClients().get(userId);
                if (session.isOpen()) {
                    session.sendMessage(msg);
                }else{
                	failClients.add(userId);
                }
            } catch (IOException e) {
            	failClients.add(userId);
            }
        }
		return failClients;
		
	}
	
	
}
