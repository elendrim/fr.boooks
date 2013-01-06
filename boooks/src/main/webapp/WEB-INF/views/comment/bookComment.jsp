<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<tbody>
			<c:forEach items="${mainCommentList}" var="mainComment">
				<tr data-id="main_comment_${mainComment.id}">
					<td>
						<span class="text-info" style="font-size:30px">${mainComment.title}</span> - 
						<a href="user/id=${subComment.user.id}">
							${mainComment.user.firstname} ${mainComment.user.lastname}
						</a> <span class="muted">
							<fmt:formatDate	value="${mainComment.modifDate}" pattern="dd MMMMM yyyy HH:mm:ss" />
						</span>
					</td>
				</tr>
				<tr>
					<td>${mainComment.text}</td>
				</tr>

				<c:forEach items="${mainComment.subComments}" var="subComment">
					<tr>
						<td>${subComment.text} - <a
							href="user/id=${subComment.user.id}">
								${subComment.user.firstname} ${subComment.user.lastname} </a> <span
							class="muted"><fmt:formatDate
									value="${subComment.modifDate}"
									pattern="dd MMMMM yyyy HH:mm:ss" /></span>
							
						</td>
					</tr>
				</c:forEach>

				<tr>
					<td>
						<button class="btn btn-mini btn-primary" type="button">Respond</button>
						<br/><br/><br/><br/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


</body>
</html>