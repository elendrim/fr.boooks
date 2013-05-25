<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Publier</title>
	<script type="text/javascript">
		$(document).ready(function(){
			 
		    var i = $('.author').size();
		 
		    $('#add').click(function() {
		        $('<div><input type="text" class="span12 author" name="authors[' + i + ']" /></div>').fadeIn('slow').appendTo('.controls-authors');
		        i++;
		    });
		 
		    $('#remove').click(function() {
		    if(i > 1) {
		        $('.author:last').remove();
		        i--;
		    }
		    });
		 
		    $('#reset').click(function() {
			    while(i > 2) {
			        $('.author:last').remove();
			        i--;
			    }
		    });
		 
		    // here's our click function for when the forms submitted
		 
		    $('.submit').click(function(){
			 
			    var answers = [];
			    $.each($('.author'), function() {
			        answers.push($(this).val());
			    });
			 
			    if(answers.length == 0) {
			        answers = "none";
			    }  
			 
			    alert(answers);
			 
			    return false;
		 
		    });
		    
		    
		    $('#nb-page-info').popover();
		    
		});

	</script>
</head>
<body>

	<h1>Book</h1>
    
    <div class="container-fluid">
	    <div class="row-fluid">
		    <div class="span8">
		    	<!--Body content-->
		    	
		    	<form:form id="addBook" cssClass="form-horizontal" commandName="bookForm"  enctype="multipart/form-data" method="POST">
			    	<div class="control-group">
			    		<label class="control-label" for="title">Titre</label>
			    		<div class="controls">
			    			<form:input path="title" id="title" placeholder="Titre" cssClass="span-12"  />
			    			<form:errors path="title" cssClass="help-inline"/>
			    		</div>
			    	</div>
			    	<div class="control-group">
			    		<label class="control-label" for="authors">Auteurs / Alias</label>
			    		<div class="controls">
				    		<div class="btn-toolbar">
								<div class="btn-group">
								    <a id="add" class="btn"><i class="icon-plus-sign" ></i></a>
								    <a id="remove" class="btn"><i class="icon-minus-sign" ></i></a>
								</div>
							</div>
			    			<div class="controls-authors">
			    				
			    				<c:forEach items="${bookForm.authors}" var="author" varStatus="status">
							    	<div><input type="text" name="authors[${status.index}]" class="span12 author" value="${author}"  /></div>    
							    </c:forEach>
			    			</div>
			    		</div>
			    	</div>
			    	<div class="control-group">
			    		<label class="control-label" for="Type">Type</label>
			    		<div class="controls">
			    			<form:select path="type" items="${typeList}" itemLabel="liType" itemValue="id" cssClass="span12" />
			    			<form:errors path="type" cssClass="help-inline"/>
			    		</div>
			    	</div>
			    	<div class="control-group">
			    		<label class="control-label" for="Genre">Genre</label>
			    		<div class="controls">
			    			<form:select path="genre" items="${genreList}" itemLabel="liGenre" itemValue="id" cssClass="span12"/>
			    			<form:errors path="genre" cssClass="help-inline"/>
			    		</div>
			    	</div>
			    	<div class="control-group">
			    		<label class="control-label" for="Resume">Resume</label>
			    		<div class="controls">
			    			<form:textarea path="resume" rows="6"  cssClass="span12"/>
			    			<form:errors path="resume" cssClass="help-inline"/>
			    		</div>
			    	</div>
			    	<div class="control-group">
			    		<label class="control-label" for="NbPage">Nombre de page</label>
			    		<div class="controls">
			    			<form:input path="nbPage" cssClass="span2" />
							<form:errors path="nbPage" cssClass="help-inline"/>
			    		</div>
			    	</div>
			    	<div class="control-group">
			    		<label class="control-label" for="fileData">Fichier PDF</label>
			    		<div class="controls">
			    			<form:input path="filePdf" type="file" cssClass="span12"/>
			    			<form:errors path="filePdf" cssClass="help-inline"/>
			    		</div>
			    	</div>
			    	<div class="control-group">
			    		<label class="control-label" for="fileData">Fichier E-Pub</label>
			    		<div class="controls">
			    			<form:input path="fileEpub" type="file" cssClass="span12"/>
			    			<form:errors path="fileEpub" cssClass="help-inline"/>
			    		</div>
			    	</div>
			    	<div class="control-group">
			    		<label class="control-label" for="fileData">Fichier Texte</label>
			    		<div class="controls">
			    			<form:input path="fileText" type="file" cssClass="span12"/>
			    			<form:errors path="fileText" cssClass="help-inline"/>
			    		</div>
			    	</div>
			    	
			    	<div class="control-group">
			    		<label class="control-label" for="fileCover">Couverture</label>
			    		<div class="controls">
			    			<form:input path="fileCover" type="file" cssClass="span12"/>
			    			<form:errors path="fileCover" cssClass="help-inline"/>
			    		</div>
			    	</div>       
			    	
			    	<div class="control-group">
			    		<div class="controls">
				    		<form:button class="btn btn-primary" ><i class="icon-ok icon-white"></i> Publier</form:button>
			    		</div>
			    	</div>
			    </form:form>
		    </div>
		    <div class="span4">
		    	<div class="alert alert-info">
			    	Les prix sont fixés en fonction de la taille de l’ouvrage :<br />
					- Moins de 2500 mots : 0,99€<br />
					- Entre 2501 et 12500 mots : 1,99€<br />
					- Entre 12501 et 25000 mots : 2,99€<br />
					- Entre 25001 et 37500 mots : 3,99€<br />
					- 37501 mots et plus : 4,99€<br />
					
			    </div>
		    </div>
	    </div>
    </div>
	
    
	
	<%-- <a	id="nb-page-info" 
				data-original-title="Nombre de page" 
				data-toggle="popover" 
				data-trigger="hover"
				data-html="true"
				data-content=""
				data-placement="right"> 
				<i class="icon-info-sign" ></i>
	</a> --%>
	
	
</body>
</html>