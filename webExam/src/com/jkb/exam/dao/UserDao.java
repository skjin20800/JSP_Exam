package com.jkb.exam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jkb.exam.config.DB;
import com.jkb.exam.dto.JoinDto;
import com.jkb.exam.dto.LoginDto;
import com.jkb.exam.model.Users;

public class UserDao {
	public int deleteById(int id) { // 회원가입
		String sql = "DELETE FROM user WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}
	public Users findById(int id){

		String sql = "SELECT id, username, password, email, userRole, createDate FROM  user WHERE id = ?"; // 0,4   4,4   8,4
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs =  pstmt.executeQuery();

			// Persistence API
			if(rs.next()) { // 커서를 이동하는 함수
				Users dto = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.password(rs.getString("password"))
						.email(rs.getString("email"))
						.role(rs.getString("userRole"))
						.createDate(rs.getTimestamp("createDate"))
						.build();
				
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int findAllPage() { // 
		String sql = "SELECT count(*) pageAll FROM  user";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int pageAll = rs.getInt("pageAll");
				return pageAll;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return 0;
	}
	
	public List<Users> findAll(int page) { 
		String sql = "SELECT id, username FROM  user WHERE username != 'admin' ORDER BY id DESC LIMIT ?, 4"; // 0,4   4,4   8,4
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page*4); // 0 -> 0, 1 ->4, 2->8
			rs = pstmt.executeQuery();
	
			List<Users> users = new ArrayList();
			
			while(rs.next()) {
				Users user = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.build();
				users.add(user);
			}
			System.out.println("Dao성공");
			return users;
			
		} catch (Exception e) {
			System.out.println("Dao실패");
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return null;
	}
	
	
	public Users findByUsernameAndPassword(LoginDto dto) {
		String sql = "SELECT id, username, email, userRole FROM user WHERE username = ? AND password = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			rs =  pstmt.executeQuery();

			// Persistence API
			if(rs.next()) {
				Users user = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.role(rs.getString("userRole"))
						.build();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}

		return null;
	}
	
	
	
	public int save(JoinDto dto) { // 회원가입
		String sql = "INSERT INTO user(username, password, email, userRole, createDate) VALUES(?,?,?, 'USER', now())";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			System.out.println(dto);
			
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
}
}
