<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>

	<script>
		//alert("Payment Successful");
		// add relevant message above or remove the line if not required
		window.onload = function(){
			if(window.opener){ 
				window.close();
			} else { 
				if( top.dg.isOpen() == true){ 
					top.dg.closeFlow();
					return true;
				}
			}
		};
	</script>
</body>
</html>
			