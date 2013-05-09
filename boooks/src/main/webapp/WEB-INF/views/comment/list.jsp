<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="util" uri="http://boooks.fr/functions"%>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<div id="comments">
	<ul class="media-list">
		<c:forEach items="${mainCommentPage.content}" var="mainComment">
	 		<li class="media"  data-id="main_comment_${mainComment.id}">
				
				<a class="pull-left" href="#">
					<img class="media-object" src="http://www.gravatar.com/avatar/${util:md5(mainComment.user.email)}.jpg?s=50r=g&d=identicon" />
				</a>
				<div class="media-body">
					${mainComment.text}
					<blockquote>
						<span class="muted">${mainComment.user.firstname} ${mainComment.user.lastname}
						– <fmt:formatDate	value="${mainComment.modifDate}" pattern="dd MMMMM yyyy HH:mm:ss" />
						</span>
					</blockquote>
					
	 			</div>
 			</li>
  		</c:forEach>
  	</ul>
</div>

<c:if test="${mainCommentPage.totalElements > 0}">
	<div id="comment-pagination">
		<h:pagination url="comment/list.htm?bookId=${bookId}" page="${mainCommentPage}" currentIndex="${currentIndex}" beginIndex="${beginIndex}" endIndex="${endIndex}" />
	</div>
</c:if>
	