<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"  %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><decorator:title /></title>
	<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
	<link href="static/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
	</style>
    <link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
    <script src="static/js/jquery-1.8.0.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<decorator:head />
</head>
<body>

	
	<div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          
          <a class="brand" href="#">Boooks <sup>beta</sup></a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Publications <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#">Publier</a></li>
                  <li><a href="#">Mes publications</a></li>
                  <li><a href="#">Suivi des ventes</a></li>
                  <li class="divider"></li>
                  <li class="nav-header">Nav header</li>
                  <li><a href="#">Separated link</a></li>
                  <li><a href="#">One more separated link</a></li>
                </ul>
              </li>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Achats <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#">Mes achats</a></li>
                  <li><a href="#">Something else here</a></li>
                </ul>
              </li>
            </ul>
            
            <sec:authorize  access="isAuthenticated()"> 
	            <ul class="nav pull-right">
	                <li class="dropdown">
	                	<a data-toggle="dropdown" class="dropdown-toggle" href="#"><sec:authentication property="principal.username"/> <b class="caret"></b></a>
	                	<ul class="dropdown-menu">
		                  	<li class="dropdown-submenu">
								<a tabindex="-1" >Profil</a>
			    				<ul class="dropdown-menu">
				    				<li>
				      					<a href="settings/account.htm" >Compte</a>
				      				</li>
				    			</ul>
				    		</li>
				    		<li><a href="j_spring_security_logout" >Déconnexion</a></li>
				    	</ul>
		  			</li>
                	
                </ul>
	            
            </sec:authorize>
            <sec:authorize  access="isAnonymous()">
            	<decorator:usePage id="pageDecorated" />
            	
            	<c:choose>
            		<c:when test="${!empty pageDecorated && pageDecorated.properties['meta.registration'] == 1}">
	            		<form class="navbar-form pull-right" method="get" action="user/registration.htm">
						  <button type="submit" class="btn btn-danger" >INSCRIPTION</button>
						</form>
            		</c:when>
            		<c:otherwise>
            			<form class="navbar-form pull-right" method="get" action="login.htm">
					  		<button type="submit" class="btn">Connexion</button>
						</form>
            		</c:otherwise>
            	</c:choose>
				
            </sec:authorize>
            <ul class="nav pull-right">
               <li>&nbsp;</li>
            </ul>
           	<form class="navbar-search pull-right" action="book/list.htm">
			    <input type="text" class="search-query" placeholder="Search">
			</form>
			
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
	
	<div class="container">
		
		<div class="alert">
	  		<strong>Attention!</strong> Boooks est en version beta. 
		</div>
		
		<c:if test="${!empty messageSuccess}">
			<div class="alert alert-success">${messageSuccess}</div>
		</c:if>
		
	
	    <decorator:body />
	    
		<hr />
	
	   	<footer>
	      	<p>&copy; Boooks 2012</p>
	   	</footer>
	
	</div> <!-- /container -->
    
</body>
</html>