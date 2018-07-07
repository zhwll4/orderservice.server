package orderservice.server.core.basic;

public class BasicDataContext {
	
	public enum SessionPropsName{
		LOGINUSERID,
		LOGINUSERNAME,
		LOGINUSERPASS;
		public String toString(){
			return super.toString().toUpperCase();
		}
	}
}
