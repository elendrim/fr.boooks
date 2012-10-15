<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Connexion</title>
</head>
<body onload='document.f.j_username.focus();'>

	
	<c:if test="${not empty error}">
		<div class="alert">
		  <button type="button" class="close" data-dismiss="alert">×</button>
		  Le nom d'utilisateur ou le mot de passe saisi est incorrect.
		</div>
	</c:if>
 
	 <form name="f" action="<c:url value='j_spring_security_check' />" method="POST" class="form-horizontal">
	 
	 	<div class="modal" style="position: relative; top: auto; left: auto; margin: 0 auto 20px; z-index: 1; max-width: 100%;">
	  		<div class="modal-header">
	    		<h3>Connexion</h3>
	  		</div>
	  		<div class="modal-body">
	    		<div class="control-group">
				    <label class="control-label" for="inputEmail">Adresse e-mail</label>
				    <div class="controls">
				      <input type="text" id="inputEmail" placeholder="Email" name="j_username" value="">
				    </div>
				</div>
		 
				<div class="control-group">
				    <label class="control-label" for="inputPassword">Mot de passe</label>
				    <div class="controls">
				      <input type="password" id="inputPassword" placeholder="Password"  name="j_password" >
				    </div>
				</div>
				
		    	<div class="control-group">
				    <div class="controls">
				      <label class="checkbox">
				        <input type="checkbox"> Rester connecté
				      </label>
				     
				    </div>
				</div>
				<div class="control-group">
				    <div class="controls">
				    	<a>Vous ne pouvez pas accéder à votre compte ?</a>
				    </div>
				</div>
	    	</div>
	  		<div class="modal-footer">
	     		<button name="submit" type="submit" class="btn btn-primary">Connexion</button>
	  		</div>
		</div>
 
	</form>
</body>
</html>