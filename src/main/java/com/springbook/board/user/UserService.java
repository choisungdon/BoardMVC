package com.springbook.board.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
