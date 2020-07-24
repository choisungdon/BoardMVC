package com.springbook.board.common;

import org.mindrot.jbcrypt.BCrypt;

public class MyUtils {
	public static String hashPassword(String pw) {
		return BCrypt.hashpw(pw, BCrypt.gensalt()); // 암호화 
	}
}
