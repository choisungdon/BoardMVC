package com.springbook.board.common;

import org.mindrot.jbcrypt.BCrypt;

public class MyUtils {
	
	public static String gensalt() {
		return BCrypt.gensalt();
	}
	
	public static String hashPassword(String pw, String salt) {
		return BCrypt.hashpw(pw, salt); // 암호화 
	}
	
	// len : 길이(4)  (0~9) 사이의 숫자 
	// "0123"
	// "9017"
	public static String makeRandomNumber(int len) {
		String result = "";
		
		// 4자리 숫자 만드는 반복문 
		for(int i=0; i<len; i++) {
			result += (int)(Math.random()*10); // 숫자 이어 붙이기 
		}
		
		return result ;
	}
}
