package com.springbook.board.user;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbook.board.common.Const;
import com.springbook.board.common.KakaAuth;
import com.springbook.board.common.KakaoUserInfo;
import com.springbook.board.common.MyUtils;

@Service
public class UserService {
	
	@Autowired
	private UserMapper mapper;
	
	public int join(UserVO param) {
		int result = 0;
		
		String salt = MyUtils.gensalt();
		String pw = param.getCpw();
		String hashPw = MyUtils.hashPassword(pw,salt);
		param.setCpw(hashPw);
		param.setSalt(salt);
		result = mapper.join(param);
		
		return result;
	}
	
	public int login(UserVO param,HttpSession hs) {
		int result = 0;
	
		UserVO db_pram = mapper.login(param);// db에서 보내는 데이터 
		
//		System.out.println("pw : "+param.getCpw());
//		System.out.println("db_pram cpw: "+db_pram.getCpw());
		
		if(db_pram == null) {
			result = 3; // id 자체가 없음 
		}else {
			String hashPw = MyUtils.hashPassword(param.getCpw(),db_pram.getSalt());
			param.setCpw(hashPw);
			
			if(param.getCpw().equals(db_pram.getCpw())) {
				result = 1; // login 성공 
				db_pram.setCpw("");
				db_pram.setSalt("");
				hs.setAttribute("loginUser", db_pram);
			}else {
				result = 2;// 비번 틀림   
			}
		}
		
		return result ;
	}
	
	public int kakaoLogin(String code, HttpSession hs) {
		int data = 0;
		
		System.out.println("code : " + code); // 인증코드 
		
		
		// ----------------- 사용자 토큰 받기 -----------------[start]
		HttpHeaders headers = new HttpHeaders();
		
		Charset utf8 = Charset.forName("UTF-8"); // meta 정보 주기 
		MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON, utf8);		
		headers.setAccept(Arrays.asList(mediaType));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);	
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>(); // parameter 데이터 주기 
		map.add("grant_type", "authorization_code");
		map.add("client_id", Const.KAKAO_CLIENT_ID);
		map.add("redirect_uri", Const.KAKAO_TOKEN_REDIRECT_URI);
		map.add("code", code);
		
		HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity(map,headers); // Entity 계체	 ->> 통신 부분	
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> respEntity 
		= restTemplate.exchange(Const.KAKAO_ACCESS_TOKEN_HOST, HttpMethod.POST, entity, String.class);
		
		
		String result = respEntity.getBody();
		System.out.println("result : "+result);
		
		ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		KakaAuth auth = null;
		try {
			 auth = om.readValue(result, KakaAuth.class);
			//System.out.println("auth : "+ auth.toString()); // 
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//-----------------------사용자 정보 가져오기 위한 통신 세팅------------------------
		HttpHeaders headers2 = new HttpHeaders();		
		MediaType mediaType2 = new MediaType(MediaType.APPLICATION_JSON, utf8);		
		headers2.setAccept(Arrays.asList(mediaType2));
		headers2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers2.set("Authorization", "Bearer " +  auth.getAccess_token());
		
		HttpEntity<LinkedMultiValueMap<String, Object>> entity2 = new HttpEntity("", headers2);
				
		ResponseEntity<String> respEntity2 
		= restTemplate.exchange(Const.KAKAO_API_HOST + "/v2/user/me", HttpMethod.GET, entity2, String.class);
		
		String result2 = respEntity2.getBody();
		System.out.println("result2 : " + result2);
		
		KakaoUserInfo kui = null;
		
		try {
			kui = om.readValue(result2, KakaoUserInfo.class);
			
			System.out.println("id : "+kui.getId());
			System.out.println("connected_at: "+kui.getConnected_at());
			System.out.println("닉네임 : "+kui.getProperties().getNickname() );
			System.out.println("profile_image : "+kui.getProperties().getProfile_image() );
			System.out.println("thumbnail_image : "+kui.getProperties().getThumbnail_image());
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 아이디 존재 체크 
		UserVO param = new UserVO();
		param.setCid(String.valueOf(kui.getId()));
		UserVO dbResult = mapper.login(param);
		
		if(dbResult == null) { // 회원 가입 
			param.setNm(param.getCid());
			param.setNm(kui.getProperties().getNickname());
			param.setCpw("");
			param.setPh("");
			param.setSalt("");
			param.setCaddr("");
			
			mapper.join(param);
			
			dbResult = param;
		}
		// 로그인 처리  (세션에 값 add)
		hs.setAttribute("loginUser", dbResult);
		return data;
	}
}
