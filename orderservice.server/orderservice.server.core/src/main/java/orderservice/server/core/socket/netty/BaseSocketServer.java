package orderservice.server.core.socket.netty;

public class BaseSocketServer {

	
	protected String host;  
	  
	protected Integer port;
    

	protected int workThreads;
	
	protected String sslpath;
    
    
	protected String ssltype;
	
	protected String password;
	
	protected String sslinstance;
	
    
    public BaseSocketServer(String host,Integer port,int workThreads,String sslpath,String ssltype,String password,String sslinstance){
    	this.host = host;
    	this.port = port;
    	this.workThreads = workThreads;
    	this.sslpath = sslpath;
    	this.ssltype = ssltype;
    	this.password = password;
    	this.sslinstance = sslinstance;
    }
}
