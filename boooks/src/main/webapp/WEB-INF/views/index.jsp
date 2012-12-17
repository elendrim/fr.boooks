<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Boooks</title>
</head>
<body>

	<div class="accueil-beta">
		<strong>Attention!</strong>
		Boooks est en version beta.
	</div>
	<div id="accueil-bandeau">
		<div class="container">
	 		<div class="row-fluid accueil-bandeau-img"> 
	 			<div class="span8">
		       	  	<div class="row-fluid">
		 				<div class="span12">
		 					<br />
		 					<h1>Révolutionnons le livre !</h1>
		               		<p>Révolutionner l'écriture et la lecture, c'est mettre en contact direct le lecteur et l'auteur, sans intermédiaire pour décider qui doit être édité ou non.</p>
		               	</div>
		           	</div>
		           	<div class="row-fluid">
		           		<div class="span3">
		           			<h2>Vous avez écrit<br/>un ouvrage ?</h2><p>Ici, vous êtes un artiste<br/>que nous soutenons.</p>
		           		</div>
		               	<div class="span5">
		               		<h2>Vous aimez découvrir<br/>de nouveaux talents ?</h2><p>Ici, vous êtes à la fois un critique,<br/>un éditeur et un lecteur écouté.</p>
		               	</div>
		               	<div class="span4">
		               		<form action="index/more.htm" method="get" ><button type="submit" class="btn btn-primary btn-large btn-bottom">En savoir plus »</button></form>
		               	</div>
		           	</div>
		         </div>
		         <div class="span4">
		         	<div id="accueil-bandeau-img"></div>
		         </div>
		         <div class="row-fluid">
		         	<div class="span4">
		         		<div id="bloc-publier" class="accueil-bloc">
			            	<img src="static/img/picto-boooks-publier.png" alt="Publier un livre" />
			              <h2>Publier</h2>
			              <p>Vous avez écrit une nouvelle, un essai ou un roman&nbsp;? <strong>Bienvenue dans votre propre maison d’édition.</strong></p>
			              <form class="form-search" action="book/add.htm" method="get">
			                <button type="submit" class="btn btn-primary">Publier un livre</button>
			              </form>
			            </div>
		         	</div>
		         	<div class="span4">
		         		  <div id="bloc-decouvrir"  class="accueil-bloc">
				            	<img src="static/img/picto-boooks-decouvrir.png" alt="Publier un livre" />
				              <h2>Découvrir</h2>
				              <p>Soif d'émotions&nbsp;? <strong>Découvrez vos futurs auteurs favoris.</strong></p>
				              <form class="form-search" action="book/search.htm" method="get">
				                <button type="submit" class="btn btn-primary ">Découvrir un ouvrage</button>
				              </form>
			            </div>
		         	</div>
		         	<div class="span4">
		         		<div id="bloc-selection"  class="accueil-bloc">
			              <h2>La sélection Boooks</h2>
			              <ul>
		              		<li><a href="#"><span>Titre du livre</span><br/>Type d'ouvrage et auteur</a></li>
		              		<li><a href="#"><span>Titre du livre</span><br/>Type d'ouvrage et auteur</a></li>
		              		<li><a href="#"><span>Titre du livre</span><br/>Type d'ouvrage et auteur</a></li>
		              		<li><a href="#"><span>Titre du livre</span><br/>Type d'ouvrage et auteur</a></li>
		              		<li><a href="#"><span>Titre du livre</span><br/>Type d'ouvrage et auteur</a></li>
			              </ul>
			            </div>
		         	</div>
	         	 </div> 
	         </div>
	        
	    </div>
 	</div>
 	
</body>
</html>