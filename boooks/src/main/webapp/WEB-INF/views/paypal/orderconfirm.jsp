<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<title>Redirect</title>
	<script>
		
		window.onload = function(){
			
			parent.location.reload();
			
			if(window.opener){
				window.close();
			} else {
				if(top.dg.isOpen() == true){
					top.dg.closeFlow();
					return true;
				}
			}
		}
	</script>
</head>
<body>
	<h3>Payment Successful</h3>
	TODO: Ajouter un lien vers la page a recharger si pas de javascript.
</body>
</html>
			