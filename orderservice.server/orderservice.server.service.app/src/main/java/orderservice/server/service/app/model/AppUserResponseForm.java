package orderservice.server.service.app.model;

import orderservice.server.core.form.UserResponseForm;

public class AppUserResponseForm  extends UserResponseForm{

	
	public AppUserResponseForm(){
		
	}
	
	public AppUserResponseForm( boolean issuccess,int failcode,String msg){
		super(issuccess,failcode, msg);
	}
	
}
