package orderservice.server.test;

import java.util.Map;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import orderservice.server.core.basic.JsonUtils;
import orderservice.server.core.basic.TokenUtils;
import orderservice.server.core.po.Test1;

public class TestTkoen {

	public static void main(String[] args) {
		
		Test1 p = new Test1();
		p.setT1("22222");
		System.out.println("token:" + JsonUtils.stringify(p,Include.NON_NULL));
		
		String token = TokenUtils.createToken(JsonUtils.stringify(p,Include.NON_NULL), 10000);

		System.out.println("token:" + token);

		Map<String, Claim> m = TokenUtils.verifyToken(token);

		if (m == null) {
			System.out.println("校验失败~~~");
		} else {
			System.out.println("校验成功~~~");
		}
		
		System.out.println(TokenUtils.getDatas(token,String.class));
	}

}
