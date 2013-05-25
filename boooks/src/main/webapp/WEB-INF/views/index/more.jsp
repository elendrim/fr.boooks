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
	<div class="row">
		<div class="span6">
			<h2>Vous êtes écrivain ?</h2>
			<p>Etre publié par un éditeur est un véritable parcours du combattant. Si vous sortez du carcan très rigide de l’écriture grand public, vos chances sont infimes de faire parler votre talent. 
Saviez vous que moins de 3% des ouvrages envoyés sont édités en librairie ?</p>
			<p>Sur Boooks, tous les talents sont les bienvenus. Vous êtes votre propre éditeur, et nous nous chargeons de vous mettre en avant sur cette plateforme.</p>
			<p>Nous fixons le prix du livre en fonction de la taille de l'oeuvre en nombre de mots</p>
			<table class="table table-striped">
   				
				 <thead>
					<tr>
						<th>Mots</th>
						<th>Prix</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Moins de 2500 mots</td>
						<td>0,99€</td>
					</tr>
					<tr>
						<td>Entre 2501 et 12500 mots</td>
						<td>1,99€</td>
					</tr>
					<tr>
						<td>Entre 12501 et 25000 mots</td>
						<td>2,99€</td>
					</tr>
					<tr>
						<td>Entre 25001 et 37500 mots</td>
						<td>3,99€</td>
					</tr>
					<tr>
						<td>37501 mots et plus</td>
						<td>4,99€</td>
					</tr>
				</tbody>
    		</table>
			
			<p><strong>Devenez ce que vous êtes : Un écrivain !</strong></p>
		</div>
		<div class="span6">
			<h2>Vous êtes lecteur ? </h2>
			<p>N’avez-vous jamais rêvé de tomber sur une « pépite littéraire », un ouvrage que vous seul connaitriez et qui répond à votre soif d’émotion ? 
N’avez-vous jamais espéré pouvoir faire connaître et mettre en avant ce livre et cet auteur grâce à votre sens critique ?
Chez Boooks, nous rêvons d’un mode artistique sans limite ou chacun pourrait lire le livre dont il rêve.</p>
			<p>Sur Boooks, vous êtes à la fois critique littéraire, éditeur avisé et un lecteur écouté.</p>
			<p><strong>Bienvenue dans votre librairie préférée !</strong></p>
		</div>
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
		    		<input type="text" class="span2 search-query" name="q">
		    		<button type="submit" class="btn">Rechercher</button>
		    	</div>
		    </form>
		</div>
	</div>


</body>
</html>