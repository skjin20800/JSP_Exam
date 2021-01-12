<%@page import="java.sql.Connection"%>
<%@page import="com.jkb.exam.config.DB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/layout/header.jsp" %>

<% DB.getConnection(); %>
  <style>
            .flex-container{
                display:flex; /* display:flex는 내부요소들을 바꿔줌,, 아마 inline형태로 바꿔주는듯 */
                 justify-content: space-between; 
                
            }
            .item{
                display: flex;
                justify-content: flex-end;
                align-items: center;
                
                height: 100px;
                width: 200px;
            }
        </style>

<div id="mcontainer">

<div class="m-2">
		<form class="form-inline d-flex justify-content-end" action="/blog/board">
			<input type="hidden" name="cmd" value="search" />
			<input type="hidden" name="page" value="0" />
			
			<input type="text" name="keyword" class="form-control mr-sm-2" placeholder="Search">			
			<button class="btn btn-primary m-1">검색</button>
		
		</form>
	</div>


<c:forEach var="user" items="${users}" >
			<div class="progress col-md-12 m-2">
		<div class="progress-bar" style="width: ${currentPosition}%"></div>
	</div>
		<div class="card col-md-12 m-2">
			<div class="card-body flex-container"  >
			<div class = "item">
				<h3>아이디 : ${user.username}</h3></div>			
				<div  class = "item">	
				
		<c:choose>
			<c:when test="${sessionScope.principal.role eq 'admin'}">
				<a href="/webExam/front?cmd=detail&id=${user.id}" class="btn btn-primary">상세보기</a>
			</c:when>
			<c:when test="${sessionScope.principal.id == user.id}">
				<a href="/webExam/front?cmd=detail&id=${user.id}" class="btn btn-primary">상세보기</a>
			</c:when>
	  </c:choose>	
				</div>				
			</div> 
		</div>
			
</c:forEach>
 
	<br />
	
	<ul class="pagination justify-content-center">
	
	
	<c:set var ="pagePrev" value="front?cmd=userList&page=${param.page-1}"/>
				<c:set var="pageNext" value="front?cmd=userList&page=${param.page+1}"/>
				
	<c:choose>
			<c:when test="${ 0 == param.page}">
				<li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
			</c:when>
			<c:otherwise>
	<li class="page-item"><a class="page-link" href="${pageScope.pagePrev}">Previous</a></li>
	</c:otherwise>
	</c:choose>
	
	<c:choose>
	<c:when test="${lastPage == param.page}">
	<li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
	</c:when>
	<c:otherwise>
	<li class="page-item"><a class="page-link" href="${pageScope.pageNext}">Next</a></li>
	</c:otherwise>
	</c:choose>
	</ul>
 <div class="container">
</div>
</div>


<script>

</script>
</body>
</html>



