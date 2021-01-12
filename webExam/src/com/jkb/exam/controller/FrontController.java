package com.jkb.exam.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jkb.exam.Service.UserService;
import com.jkb.exam.dto.CommonRespDto;
import com.jkb.exam.dto.JoinDto;
import com.jkb.exam.dto.LoginDto;
import com.jkb.exam.model.Users;


@WebServlet("/front")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public FrontController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doProcess(request, response);
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doProcess(request, response);
	}


	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		

		UserService userService = new UserService();
		// http://localhost:8080/blog/user?cmd=loginForm
		if (cmd.equals("login")) {
			// 서비스 호출
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			LoginDto dto = new LoginDto();
			dto.setUsername(username);
			dto.setPassword(password);
			Users userEntity = userService.로그인(dto);
			if (userEntity != null) {
				HttpSession session = request.getSession();
				session.setAttribute("principal", userEntity); // 인증주체
				response.sendRedirect("index.jsp");
			} else {
				System.out.println("로그인 실패");
			}	
		}else if(cmd.equals("loginForm")){
			RequestDispatcher dis = 
					request.getRequestDispatcher("login.jsp");
				dis.forward(request, response);
		}
		else if(cmd.equals("joinForm")) {
			RequestDispatcher dis = 
					request.getRequestDispatcher("join.jsp");
				dis.forward(request, response);
		} else if(cmd.equals("join")) {
System.out.println("호출됨");
			// 서비스 호출
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			JoinDto dto = new JoinDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			
			System.out.println("회원가입 : " + dto);
			int result = userService.회원가입(dto);
			if (result == 1) {
				response.sendRedirect("index.jsp");
			} else {
				System.out.println("회원가입실패");
			}
		}
		else if(cmd.equals("userList")) {
			int page = Integer.parseInt(request.getParameter("page")); // 최초 : 0, Next : 1, Next: 2

			List<Users> users = userService.목록보기(page);
			int userAll = userService.전체게시글수();
			int remainBoard = userAll % 4;
			int remainPage = 0;
			if (remainBoard > 0) {
				remainPage = 1;
			}
			int lastPage = (((userAll / 4) + remainPage) - 1);
			double currentPosition = (double) page / (lastPage) * 100;

			request.setAttribute("currentPosition", currentPosition);
			request.setAttribute("users", users);
			request.setAttribute("lastPage", lastPage);
			RequestDispatcher dis = request.getRequestDispatcher("userList.jsp");
			dis.forward(request, response);
		}else if (cmd.equals("detail")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Users user = userService.유저정보보기(id); // board테이블+user테이블 = 조인된 데이터!!
			request.setAttribute("user", user);
			RequestDispatcher dis = request.getRequestDispatcher("userInfo.jsp");
			dis.forward(request, response);
		} 
		
		else if (cmd.equals("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("index.jsp");
		} else if (cmd.equals("delete")) {
			// 1. 요청 받은 json 데이터를 자바 오브젝트로 파싱
			int id = Integer.parseInt(request.getParameter("id"));

			// 2. DB에서 id값으로 글 삭제
			int result = userService.글삭제(id);

			// 3. 응답할 json 데이터를 생성
			CommonRespDto<String> commonRespDto = new CommonRespDto<>();
			commonRespDto.setStatusCode(result);
			commonRespDto.setData("성공");

			Gson gson = new Gson();
			String respData = gson.toJson(commonRespDto);
			System.out.println("respData : "+respData);
			PrintWriter out = response.getWriter();
			out.print(respData);
			out.flush();
		}
		
	}
	


}
