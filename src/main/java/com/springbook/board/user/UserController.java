package com.springbook.board.user;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbook.board.common.Const;
import com.springbook.board.common.KakaAuth;
import com.springbook.board.common.MyUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(Model model, @RequestParam(value = "err", required = false) String err) {
		if (err != null) {
			model.addAttribute("msg", err);
		}
		return "user/join";
	}

	// 카카오 로그인
	@RequestMapping(value = "/loginKAKAO", method = RequestMethod.GET)
	public String loginKAKAO() {
		String uri = String.format(
				"redirect:https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
				Const.KAKAO_CLIENT_ID, Const.KAKAO_AUTH_REDIRECT_URI);
		return uri;
	}

	@RequestMapping(value="/joinKakao", method=RequestMethod.GET)
	public String joinKAKAO(@RequestParam(required=false) String code,
			@RequestParam(required=false) String error) {
		
		System.out.println("code : " + code);
		System.out.println("error : " + error);
		
		if(code == null) {
			return "redirect:/user/login";
		}
		
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
		
		try {
			KakaAuth auth = om.readValue(result, KakaAuth.class);
			//System.out.println("auth : "+ auth.toString());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/user/login";
	
	}
	
	@RequestMapping(value = "/joinPost", method = RequestMethod.POST)
	public String join(UserVO param, RedirectAttributes ra, HttpSession hs) {
		// System.out.println("cid : "+param.getCid());
		String rNumbers = (String) hs.getAttribute("rNumbers");

		if (!rNumbers.equals(param.getPhAuthNumber())) {
			ra.addAttribute("err", "인증번호가 맞지 않습니다. ");
			return "redirect:/user/join";
		}

		int result = service.join(param);

		return "redirect:/user/login";
	}

	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public String login(UserVO param, HttpSession hs, Model model) {
//		System.out.println("cid : "+param.getCid());
		int result = service.login(param, hs);

		switch (result) {
		case 1:
			return "redirect:/board/list";
		case 2:
			model.addAttribute("msg", "비번이 틀립니다.");
			return "user/login";
		case 3:
			model.addAttribute("msg", "id 자체가 없음");
			return "user/login";
		}
		return "user/login";

	}

	@RequestMapping(value = "/phAuth", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> phAuth(@RequestParam String ph, HttpSession hs) {// @ResponseBody 모든 객체 데이터
																								// 모두 -> json 형태로 보낸게
																								// 만든다.
		// System.out.println("ph : "+ph);

		// 4자리 랜덤값 (0~9)
		int len = 4; // 인증번호 길이
		String rNumbers = MyUtils.makeRandomNumber(len); // 4자리 랜덤 인증 번호 추출
		// ph 번호 인증번호를 문자로 보낸다.
		System.out.println("인증 번호 : " + rNumbers);

		hs.setAttribute("rNumbers", rNumbers);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", 1);

		return map;
	}
}
