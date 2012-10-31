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

	<div class="hero-unit">
		<h1>Révolutionnons notre façon de lire !</h1>
		<p>Nous allons révolutionner l'écriture et la lecture. Nous mettons en contact direct le lecteur et l'auteur, sans intermédiaire pour 
		décider qui doit être édité ou non. Vous avez écrit un ouvrage ? Ici, vous êtes un artiste que nous soutenons. Vous aimez découvrir 
		de nouveaux talents ? Ici, vous êtes à la fois un critique, un éditeur et un lecteur écouté. Nous allons créer la plus grande 
		librairie jamais envisagée.</p>
		<p><strong>Bienvenue, vous êtes sur Boooks.</strong></p>
		<p>
			<a class="btn btn-primary btn-large" href="index/more.htm" >En savoir plus &raquo;</a>
		</p>
	</div>

	<div class="row">
		<div class="span6">
			<h2>Publier</h2>
			<p>Vous avez écrit une nouvelle, un essai ou un roman ? Bienvenue dans votre propre maison d’édition.</p>
			
			<form class="form-search" action="book/add.htm" method="get">
				<button type="submit" class="btn">Publier</button>
			</form>
			
		</div>
		<div class="span6">
			<h2>Découvrir</h2>
			<p>Soif d’émotions ? Découvrez vos futurs auteurs favoris.</p>
			<form class="form-search" action="book/search.htm">
		    	<div class="input-append">
		    		<input type="text" class="span2 search-query">
		    		<button type="submit" class="btn">Rechercher</button>
		    	</div>
		    </form>
		</div>
	</div>


</body>
</html>