package orderservice.server.core.mini;

public class MiniException  extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String message=null;
	public MiniException(String message){
		this.message=message;
		
	}
	public MiniException(){
		
	}
	public String getMessage(){
		return this.message;
	}
	
}

