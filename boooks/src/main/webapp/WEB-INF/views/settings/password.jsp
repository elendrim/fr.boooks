<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Mot de passe</title>
</head>
<body>

	<h1>Mot de passe</h1>
    
    <form:form id="passwordForm" cssClass="form-horizontal" commandName="passwordForm" method="POST">
    	
    	<div class="control-group">
    		<label class="control-label" for="password">Créez un mot de passe</label>
    		<div class="controls">
    			<form:password path="password" id="password" placeholder="Password" cssClass="span7" />
    			<form:errors path="password" cssClass="help-inline"/>
    		</div>
    	</div>
    	<div class="control-group">
    		<label class="control-label" for="confirmPassword">Confirmez votre mot de passe</label>
    		<div class="controls">
    			<form:password path="confirmPassword" id="confirmPassword" placeholder="Password" cssClass="span7" />
    			<form:errors path="confirmPassword" cssClass="help-inline"/>
    		</div>
    	</div>
    	
    	<div class="control-group">
    		<div class="controls">
	    		<form:button class="btn btn-primary" ><i class="icon-ok icon-white"></i> Enregistrer</form:button>
    		</div>
    	</div>
    </form:form>
	
</body>
</html>