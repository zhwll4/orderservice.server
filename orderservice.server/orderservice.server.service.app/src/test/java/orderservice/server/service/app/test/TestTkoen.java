package orderservice.server.service.app.test;

import java.util.Map;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import orderservice.server.core.basic.JsonUtils;
import orderservice.server.core.basic.TokenUtils;
import orderservice.server.core.po.Test1;
import orderservice.server.service.app.model.AppUserTokenForm;

public class TestTkoen {

	public static void main(String[] args) {
		
		AppUserTokenForm p = new AppUserTokenForm();
		p.setToken("xxx");
		p.setUserid("xada");
		System.out.println("token:" + JsonUtils.stringify(p,Include.NON_NULL));
		
		String token = TokenUtils.createToken(p, 10000);

		System.out.println("token:" + token);

		Map<String, Claim> m = TokenUtils.verifyToken(token);

		if (m == null) {
			System.out.println("校验失败~~~");
		} else {
			System.out.println("校验成功~~~");
		}
		String datas2 = JsonUtils.stringify(TokenUtils.getDatas(token,String.class));
		System.out.println(datas2);
		
		System.out.println(JsonUtils.stringify(JsonUtils.parseObject(datas2, AppUserTokenForm.class)));
	}

	
}
class TestF{
	
	boolean T1 = false;
	String T2 = "xxx";
	public boolean isT1() {
		return T1;
	}
	public void setT1(boolean t1) {
		T1 = t1;
	}
	public String getT2() {
		return T2;
	}
	public void setT2(String t2) {
		T2 = t2;
	}
	
	
}
