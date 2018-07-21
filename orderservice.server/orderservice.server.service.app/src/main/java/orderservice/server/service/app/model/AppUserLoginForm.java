package orderservice.server.service.app.model;

import orderservice.server.core.form.UserLoginForm;

public class AppUserLoginForm extends UserLoginForm{

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		if(super.getType()==null){
			return "SALERAPP";
		}
		return super.getType();
	}

	
}
