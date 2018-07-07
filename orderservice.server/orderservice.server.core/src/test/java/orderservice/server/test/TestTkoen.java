package orderservice.server.test;

import java.util.Map;

import com.auth0.jwt.interfaces.Claim;

import orderservice.server.core.basic.TokenUtils;

public class TestTkoen {

	public static void main(String[] args) {
		String token = TokenUtils.createToken("{x:12}", 2000);

		System.out.println("token:" + token);

		Map<String, Claim> m = TokenUtils.verifyToken(token);

		if (m == null) {
			System.out.println("校验失败~~~");
		} else {
			System.out.println("校验成功~~~");
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m = TokenUtils.verifyToken(token);

		if (m == null) {
			System.out.println("校验失败~~~");
		} else {
			System.out.println("校验成功~~~");
		}
	}

}
