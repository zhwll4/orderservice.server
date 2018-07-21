package orderservice.server.service.app.socketserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.corundumstudio.socketio.SocketIOClient;

import orderservice.server.service.app.base.AppBasicDataContext;

public class SocketManager {
	//
	private final static Map<String,SocketIOClient> clientsGroup =  new ConcurrentHashMap<String, SocketIOClient>();
	private final static Map<String,String> sessionGroup =  new ConcurrentHashMap<String, String>();
	
	public static Map<String,SocketIOClient> getGroup(){
		return clientsGroup;
	}
	
	public static void addClientToGroup(String clientId,SocketIOClient client){
		clientsGroup.put(clientId, client);
		sessionGroup.put(client.getSessionId().toString(), clientId);
	}
	
	public static void removeClientToGroup(String clientId){
		if(clientsGroup.containsKey(clientId)){
			String sid = clientsGroup.get(clientId).getSessionId().toString();
			if(sessionGroup.containsKey(sid)
				&&sessionGroup.get(sid).equals(clientId)){
				sessionGroup.remove(sid);
			}
			clientsGroup.remove(clientId);
			
		}
	}
	public static void removeClientToGroup(SocketIOClient client){
		try {
			String clid= client.getSessionId().toString();
			if(sessionGroup.containsKey(clid)){
				if(clientsGroup.containsKey(sessionGroup.get(clid))
					&&clientsGroup.get(sessionGroup.get(clid)).getSessionId().toString().equals(clid)){
					clientsGroup.remove(sessionGroup.get(clid));
					sessionGroup.remove(clid);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean sendMsgToClient(String clientId,String event,Object data){
		if(!clientsGroup.containsKey(clientId)){
			return false;
		}
		return sendMsgToClient(clientsGroup.get(clientId),event,data);
	}
	public static boolean sendMsgToClient(SocketIOClient socketclient,String event,Object data){
		try {
			socketclient.sendEvent(event, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			AppBasicDataContext.getAppLoger().info("发送消息失败:");
			
			e.printStackTrace();
			
			return false;
		}
		return true;
	}
}
