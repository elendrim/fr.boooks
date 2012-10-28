<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Rechercher</title>
</head>
    
<body>


	<h1>Liste des Commentaires</h1>
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<td>title</td>
				<td>comment</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${commentList}" var="mainComment">
				<tr data-id="${mainComment.id}">
					<td>
						<table>
							<tr>
								<td>at <fmt:formatDate value="${mainComment.modifDate}" pattern="dd MMMMM yyyy HH:mm:ss"/></td>
								<td>from  lastname</td>
								<td>${mainComment.title}</td>
							</tr>
							<tr>
								<td colspan="3">${mainComment.text}</td>
							</tr>
						<td>${book.title}</td>
					</table>
					<br/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	

</body>
</html>