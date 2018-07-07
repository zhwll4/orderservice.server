package orderservice.server.core.mini;

public class MiniNotRollBackException  extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String message=null;
	public MiniNotRollBackException(String message){
		this.message=message;
		
	}
	public MiniNotRollBackException(){
		
	}
	public String getMessage(){
		return this.message;
	}
	
}

