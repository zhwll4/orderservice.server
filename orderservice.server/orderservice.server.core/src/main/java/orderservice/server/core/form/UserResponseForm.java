package orderservice.server.core.form;

public class UserResponseForm  extends BaseForm{

	
	public boolean issuccess = true;
	public int failcode = 0;
	public String msg = "操作成功！";
	
	
	public UserResponseForm(){
		
	}
	
	public UserResponseForm( boolean issuccess,int failcode,String msg){
		this.issuccess = issuccess;
		this.failcode = failcode;
		this.msg = msg;
	}
	
	public boolean isIssuccess() {
		return issuccess;
	}
	public void setIssuccess(boolean issuccess) {
		this.issuccess = issuccess;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getFailcode() {
		return failcode;
	}
	public void setFailcode(int failcode) {
		this.failcode = failcode;
	}
	
	
}
