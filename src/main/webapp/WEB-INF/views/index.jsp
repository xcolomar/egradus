<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title><fmt:message bundle="${missatge}" key="index.titol"/></title>
	
	<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/resources/css/styles.css"/>" rel="stylesheet">
	
</head>
<body>
	
	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<a href="#" class="navbar-brand">EGRADUS</a>
			<button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<div class="collapse navbar-collapse navHeaderCollapse">
				<ul class="nav navbar-nav navbar-right">
					<li>
						<a href="<c:url value="/canviaIdioma"/>?idioma=ca"><fmt:message bundle="${missatge}" key="index.lang.catala"/></a>
					</li>
					<li>
						<a href="<c:url value="/canviaIdioma"/>?idioma=es"><fmt:message bundle="${missatge}" key="index.lang.castella"/></a>
					</li>
					<li>
						<a href="<c:url value="/canviaIdioma"/>?idioma=en"><fmt:message bundle="${missatge}" key="index.lang.angles"/></a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="jumbotron">
  		<h1><fmt:message bundle="${missatge}" key="index.jumbo"/></h1>
  		<h2><fmt:message bundle="${missatge}" key="index.jumbo.mis"/></h2>
		<div class="container">
			<div class="row">
				<div class="col-md-6">
					<p><fmt:message bundle="${missatge}" key="index.mis.login"/></p>
					<form class="form-horizontal" role="form" method="post" action="login">
						<div class="form-group">
							<label for="indexAlies" class="col-sm-3 control-label"><fmt:message bundle="${missatge}" key="index.label.alies"/></label>
							<div class="col-sm-5">
								<input class="form-control" id="indexAlies" name="alies" type="text" placeholder="<fmt:message bundle="${missatge}" key="index.label.placeholder.alies"/>"/>
							</div>
						</div>
						<div class="form-group">
							<label for="indexClau" class="col-sm-3 control-label"><fmt:message bundle="${missatge}" key="index.label.clau"/></label>
							<div class="col-sm-5">
								<input class="form-control" id="indexClau" name="clau" type="password" placeholder="<fmt:message bundle="${missatge}" key="index.label.placeholder.clau"/>"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-5">${errorLogin}</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-5">
								<button type="submit" class="btn btn-primary btn-md"><fmt:message bundle="${missatge}" key="index.button.login"/></button>
							</div>
						</div>
					</form>
				</div>
				<div class="col-md-6">
					<p><fmt:message bundle="${missatge}" key="index.mis.registre"/></p>
					<form class="form-horizontal" role="form" method="post" action="registre">
						<div class="form-group">
							<label for="indexNom" class="col-sm-3 control-label"><fmt:message bundle="${missatge}" key="index.label.nom"/></label>
							<div class="col-sm-5">
								<input class="form-control" id="indexNom" name="nom" type="text" placeholder="<fmt:message bundle="${missatge}" key="index.label.placeholder.nom"/>"/>
							</div>
							<div class="col-sm-4">${errorNom}</div>
						</div>
						<div class="form-group">
							<label for="indexPrimerLlinatge" class="col-sm-3 control-label"><fmt:message bundle="${missatge}" key="index.label.primerLlinatge"/></label>
							<div class="col-sm-5">
								<input class="form-control" id="indexPrimerLlinatge" name="primerLlinatge" type="text" placeholder="<fmt:message bundle="${missatge}" key="index.label.placeholder.primerLlinatge"/>"/>
							</div>
							<div class="col-sm-4">${errorPrimerLlinatge}</div>
						</div>
						<div class="form-group">
							<label for="indexSegonLlinatge" class="col-sm-3 control-label"><fmt:message bundle="${missatge}" key="index.label.segonLlinatge"/></label>
							<div class="col-sm-5">
								<input class="form-control" id="indexSegonLlinatge" name="segonLlinatge" type="text" placeholder="<fmt:message bundle="${missatge}" key="index.label.placeholder.segonLlinatge"/>"/>
							</div>
							<div class="col-sm-4">${errorSegonLlinatge}</div>
						</div>
						<div class="form-group">
							<label for="indexCorreu" class="col-sm-3 control-label"><fmt:message bundle="${missatge}" key="index.label.correu"/></label>
							<div class="col-sm-5">
								<input class="form-control" id="indexCorreu" name="correu" type="text" placeholder="<fmt:message bundle="${missatge}" key="index.label.placeholder.correu"/>"/>
							</div>
							<div class="col-sm-4">${errorCorreu}</div>
						</div>
						<div class="form-group">
							<label for="indexAliesRegistre" class="col-sm-3 control-label"><fmt:message bundle="${missatge}" key="index.label.alies"/></label>
							<div class="col-sm-5">
								<input class="form-control" id="indexAliesRegistre" name="alies" type="text" placeholder="<fmt:message bundle="${missatge}" key="index.label.placeholder.alies"/>"/>
							</div>
							<div class="col-sm-4">${errorAlies}</div>
						</div>
						<div class="form-group">
							<label for="indexClauRegistre" class="col-sm-3 control-label"><fmt:message bundle="${missatge}" key="index.label.clau"/></label>
							<div class="col-sm-5">
								<input class="form-control" id="indexClauRegistre" name="clau" type="password" placeholder="<fmt:message bundle="${missatge}" key="index.label.placeholder.clau"/>"/>
							</div>
							<div class="col-sm-4">${errorClau}</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-5">
								${errorRegistre}
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-5">
								<button type="submit" class="btn btn-primary btn-md"><fmt:message bundle="${missatge}" key="index.button.login"/></button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script src="<c:url value="/resources/js/jquery-1.11.0.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/bootstrap.js"/>" type="text/javascript"></script>
</body>
</html>