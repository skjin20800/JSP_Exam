<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	RequestDispatcher dis = request.getRequestDispatcher("front?cmd=userList&page=0");
	dis.forward(request, response);
%>