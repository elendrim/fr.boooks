<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Rechercher</title>
	<script type="text/javascript">
		$(document).ready(function() {
			$('.table > tbody > tr').click(function() {
				var id = $(this).attr('data-id');
				window.location.href = 'book/view.htm?id=' + id;
			});
		});
	</script>
</head>
    
<body>

	

	<h1>Liste des Boooks</h1>
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<td>#</td>
				<td>Titre</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bookList}" var="book">
				<tr data-id="${book.id}">
					<td>${book.id}</td>
					<td>${book.title}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	

</body>
</html>