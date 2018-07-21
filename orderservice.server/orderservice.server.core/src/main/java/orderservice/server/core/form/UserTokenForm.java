package orderservice.server.core.form;

public class UserTokenForm  extends BaseForm{

	public String token;
	public String userid;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
