package com.springbook.board.user;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService service ;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	
	@RequestMapping(value = "/joinPost", method = RequestMethod.POST)
	public String join(UserVO param) {
		//System.out.println("cid : "+param.getCid());
		int result  = service.join(param);
		return "redirect:/user/login";
	}
	
	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public String login(UserVO param,HttpSession hs,Model model) {
//		System.out.println("cid : "+param.getCid());
		int result = service.login(param,hs);
		
		switch (result) {
		case 1:
			return "redirect:/board/list";
		case 2:
			model.addAttribute("msg","비번이 틀립니다.");
			return "user/login";
		case 3:
			model.addAttribute("msg","id 자체가 없음");
			return "user/login";
		}
		return "user/login";
		
	}
	
}
