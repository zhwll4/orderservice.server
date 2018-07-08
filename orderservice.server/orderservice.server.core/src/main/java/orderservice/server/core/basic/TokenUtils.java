package orderservice.server.core.basic;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class TokenUtils {

	
	private final static String defaultJwtSec = "threemantkonsec";
	
	private final static Map<String, Object> map = new HashMap<String,Object>();
	
	static{
		map.put("alg", "HS256");
		map.put("typ", "JWT");
	}
	
	
	
	public static <T> String createToken(T datas,int expireMilliSeconds){
		
		String jwtSec = BasicUtils.getTextValue("jwttokensec",defaultJwtSec);
		
		Date iatDate = new Date();
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MILLISECOND, expireMilliSeconds);
		Date expiresDate = nowTime.getTime();
 
		String sdatas = null;
		
		if(datas instanceof String){
			sdatas = (String) datas;
		}else{
			sdatas = JsonUtils.stringify(datas);
		}
		
		String token  = JWT.create().withHeader(map)
				.withClaim("datas", sdatas==null?"":sdatas) 
				.withIssuedAt(iatDate) // sign time
				.withExpiresAt(expiresDate) // expire time
				.sign(Algorithm.HMAC256(jwtSec)); // signature
 
		return token;
		
	}
//	public static String createToken(String datas,int expireMilliSeconds){
//		
//		String jwtSec = BasicUtils.getTextValue("jwttokensec",defaultJwtSec);
//		
//		Date iatDate = new Date();
//		Calendar nowTime = Calendar.getInstance();
//		nowTime.add(Calendar.MILLISECOND, expireMilliSeconds);
//		Date expiresDate = nowTime.getTime();
// 
//		String token  = JWT.create().withHeader(map)
//				.withClaim("datas", datas==null?"":datas) 
//				.withIssuedAt(iatDate) // sign time
//				.withExpiresAt(expiresDate) // expire time
//				.sign(Algorithm.HMAC256(jwtSec)); // signature
// 
//		return token;
//		
//	}
	/**
	 * 解密Token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Claim> verifyToken(String token) {
		DecodedJWT jwt = null;
		try {
			String jwtSec = BasicUtils.getTextValue("jwttokensec",defaultJwtSec);
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSec)).build();
			jwt = verifier.verify(token);
		} catch (Exception e) {
			return null;
		}
		return jwt.getClaims();
	}
	/**
	 * 解密Token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static <T> T getDatas(String token,Class<T> t) {
		DecodedJWT jwt = null;
		try {
			String jwtSec = BasicUtils.getTextValue("jwttokensec",defaultJwtSec);
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSec)).build();
			jwt = verifier.verify(token);
			
			String data = jwt.getClaims().get("datas").asString();
			if(t.getName().equals(String.class.getName())){
				return (T) data;
			}else{
				return JsonUtils.parseObject(data, t);
			}
		} catch (Exception e) {
			
			return null;
		}
	}
}
