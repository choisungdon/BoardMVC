package com.springbook.board.user;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.springbook.board.common.Const;

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
	
	
	// 카카오 로그인 인증 코드 받기(요청) 
		@RequestMapping(value = "/loginKAKAO", method = RequestMethod.GET)
		public String loginKAKAO() {
			String uri = String.format(
					"redirect:https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
					Const.KAKAO_CLIENT_ID, Const.KAKAO_AUTH_REDIRECT_URI);
			return uri;
		}
			
		// 인증코드 받기 (응답)
		@RequestMapping(value="/joinKakao", method=RequestMethod.GET)
		public String joinKAKAO(@RequestParam(required=false) String code,
				@RequestParam(required=false) String error, HttpSession hs) {
			
			System.out.println("code : " + code); // 인증코드 
			System.out.println("error : " + error);
			
			if(code == null) { // 인증코드 (토큰) 없으면 로그인 화면으로
				return "redirect:/user/login";
			}
			
			int result = service.kakaoLogin(code, hs); // 로그인 정보 저장 
			
			return "redirect:/board/list";
		
		}
		@RequestMapping(value = "/profile", method = RequestMethod.GET)
		public String profile(Model model) {
			return "/user/profile";
		}
		
		@RequestMapping(value = "/profile", method = RequestMethod.POST)
		public String profile(@RequestParam("uploadProfile") MultipartFile uploadProfile) {
			System.out.println("uploadProfile : "+uploadProfile);
			return "/user/profile";
		}
		
		@RequestMapping(value="/logout", method=RequestMethod.GET)
		public String logout(HttpSession hs) {
			hs.invalidate();
			return "redirect:/user/login";
		}
		
		
		
}
