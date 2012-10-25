<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Suppression du compte Boooks</title>
</head>
<body>

	<h1>Suppression du compte Boooks</h1>
    
   <div >
      <div class="alert alert-block alert-error fade in">
        <h4 class="alert-heading">Attention !</h4>
        <p>Fermer le compte, et supprimer l'ensemble des livres publiés et des informations associés ?</p>
        <form action="settings/deleteAccount.htm" method="post">
	        <p>
	       		<button class="btn btn-danger">Supprimer définitivement le compte</button>
		       	<a class="btn" href="settings/index.htm">Annuler</a>
	        </p>
        </form>
      </div>
    </div>
    
	
</body>
</html>