package com.jkb.exam.Service;

import java.util.List;


import com.jkb.exam.dao.UserDao;
import com.jkb.exam.dto.JoinDto;
import com.jkb.exam.dto.LoginDto;
import com.jkb.exam.model.Users;


public class UserService {

	private UserDao userDao; 

	public UserService() {
		userDao = new UserDao();
	}
	
	public Users 로그인(LoginDto dto) {
		return userDao.findByUsernameAndPassword(dto);
	}
	
	public int 회원가입(JoinDto dto) {
		int result = userDao.save(dto);
		return result; 
	}
	public List<Users> 목록보기(int page) {
		return userDao.findAll(page);
	}
	public int 전체게시글수() {
		return userDao.findAllPage();
	}
	public Users 유저정보보기(int id) {
		// 조회수 업데이트치기
			return userDao.findById(id);	
	}
	public int 글삭제(int id) {
		return userDao.deleteById(id);
	}
	
}
