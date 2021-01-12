<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
				<c:choose>
			<c:when test="${sessionScope.principal.role eq 'admin'}">
				<button onClick="deleteById(${user.id})" class="btn btn-danger">삭제</button>
			</c:when>
			<c:when test="${sessionScope.principal.id == user.id}">
				<button onClick="deleteById(${user.id})" class="btn btn-danger">삭제</button>
			</c:when>
			
	</c:choose>
	
	<br />
	<br />
	<br />
	<h3 class="m-2">
		<b>아이디 : ${user.username}</b>
	</h3>
	<h3 class="m-2">
		<b>비밀번호 : ${user.password}</b>
	</h3>
	<h3 class="m-2">
		<b>이메일 : ${user.email}</b>
	</h3>
	<h3 class="m-2">
		<b>사용등급 : ${user.role}</b>
	</h3>
	<h3 class="m-2">
		<b>ID생성일 : ${user.createDate}</b>
	</h3>
	
	<hr />
	<div class="form-group">
		<div class="m-2">${user.createDate}</div>
	</div>
	<hr />

</div>
	<script>
	function deleteById(userId){

		$.ajax({
			type: "post",
			url: "/webExam/front?cmd=delete&id="+userId,
			dataType: "json"
		}).done(function(result){
			console.log(result);
			if(result.statusCode == 1){
				location.href="index.jsp";
			}else{
				alert("삭제에 실패하였습니다.");
			}
		});
	} 
	</script>
</body>
</html>


