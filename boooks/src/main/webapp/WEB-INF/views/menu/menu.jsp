<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"  %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>

	<div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          
          <a class="brand" href="#"><img alt="Boooks" src="static/img/logo-boooks-beta.png" /></a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Publications <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="book/add.htm">Publier</a></li>
                  <sec:authorize  access="isAuthenticated()">
                  	<li class="divider"></li> 
                  	<li><a href="book/myPublications.htm">Mes publications</a></li>
                  	<li><a href="#">Suivi des ventes</a></li>
                  </sec:authorize>
                </ul>
              </li>
              <sec:authorize  access="isAuthenticated()"> 
              	<li class="dropdown">
                	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Achats <b class="caret"></b></a>
                	<ul class="dropdown-menu">
                  		<li><a href="book/purchases.htm">Mes achats</a></li>
                	</ul>
              	</li>
              </sec:authorize>
            </ul>
            
            <sec:authorize  access="isAuthenticated()"> 
	            <ul class="nav pull-right">
	                <li class="dropdown">
	                	<a data-toggle="dropdown" class="dropdown-toggle"><i class="icon-user"></i> <sec:authentication property="principal.firstname"/> <sec:authentication property="principal.lastname"/> <b class="caret"></b></a>
	                	<ul class="dropdown-menu">
		                  	<li><a href="settings/index.htm" ><i class="icon-user"></i> Profil</a></li>
				    		<li><a href="j_spring_security_logout" ><i class="icon-off"></i> Déconnexion</a></li>
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
           	<form class="navbar-search pull-right" action="book/search.htm">
			    <input type="text" class="search-query" placeholder="Rechercher" name="q">
			</form>
			
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>