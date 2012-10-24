<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Inscription</title>
</head>
<body>

	<h1>Inscription</h1>
    
    <form:form id="addUser" cssClass="form-horizontal" commandName="userForm" method="POST">
    	
    	<div class="control-group">
    		<label class="control-label" for="firstname">Prénom</label>
    		<div class="controls">
    			<form:input path="firstname" id="firstname" placeholder="Prénom" cssClass="span7"  />
    			<form:errors path="firstname" cssClass="help-inline"/>
    		</div>
    	</div>
    	<div class="control-group">
    		<label class="control-label" for="lastname">Nom</label>
    		<div class="controls">
    			<form:input path="lastname" id="firstname" placeholder="Nom" cssClass="span7"  />
    			<form:errors path="lastname" cssClass="help-inline"/>
    		</div>
    	</div>
    	<div class="control-group">
    		<label class="control-label" for="email">Adresse e-mail</label>
    		<div class="controls">
    			<form:input path="email" id="email" placeholder="Email" cssClass="span7"   />
    			<form:errors path="email" cssClass="help-inline"/>
    		</div>
    	</div>
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
    		<label class="control-label" for="birthdate">Date de naissance</label>
    		<div class="controls">
    			<form:input path="day" id="day" placeholder="Jour" cssClass="span1"  />
    			<form:errors path="day" cssClass="help-inline"/>
    			
    			<form:select path="month"  cssClass="span2" >
    				<form:option value="0">Janvier</form:option>
    				<form:option value="1">Février</form:option>
    				<form:option value="2">Mars</form:option>
    				<form:option value="3">Avril</form:option>
    				<form:option value="4">Mai</form:option>
    				<form:option value="5">Juin</form:option>
    				<form:option value="6">Juillet</form:option>
    				<form:option value="7">Août</form:option>
    				<form:option value="8">Septembre</form:option>
    				<form:option value="9">Octobre</form:option>
    				<form:option value="10">Novembre</form:option>
    				<form:option value="11">Décembre</form:option>
    			</form:select>
    			<form:errors path="month" cssClass="help-inline"/>
    			<form:input path="year" id="year" placeholder="Année" cssClass="span1"  />
    			<form:errors path="year" cssClass="help-inline"/>
    		</div>
    	</div>
    	<div class="control-group">
    		<label class="control-label" for="sex">Sexe</label>
    		<div class="controls">
    			<form:select path="sex"  cssClass="span2">
    				<form:option value="0">Femme</form:option>
    				<form:option value="1">Homme</form:option>
    				<form:option value="2">Autre</form:option>
    			</form:select>
    		</div>
    	</div>
    	
    	<div class="control-group">
    		<div class="controls">
	    		<form:button class="btn" >Etape suivante</form:button>
    		</div>
    	</div>
    </form:form>
	
</body>
</html>