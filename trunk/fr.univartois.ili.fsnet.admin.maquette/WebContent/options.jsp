<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>    
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<html>
<head>
<link rel="icon" type="image/png" href="images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="300;options.jsp?option=current">
<meta name="author" content="Luka Cvrk - www.solucija.com" />
<meta name="description" content="Site Description" />
<meta name="keywords" content="site, keywords" />
<meta name="robots" content="index, follow" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<title>FSNet : Options</title>
<script language="JavaScript" src="admin.js">
</script>
<script type="text/javascript">
</script>
</head>
<body onload="showMenu();${param.showHide}('listToDeploy');${param.recherche}('rechercheVide')">
<jsp:include page="header.jsp"></jsp:include>


<div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>

<div id="left">
<h2><a
	href="options.jsp?option=current"
	title="configuration des options d'envoie de mail">Options</a></h2>
<jsp:include page="date.jsp"></jsp:include></div>


<html:javascript formName="/lesoptions"/>
<div id="tableauprincipal">
<p id="informationsOptions">Nb:Ce formulaire permet de configurer vos préférences pour l'envoie de mails. Par exemple: quand vous enregistrer un membre un mail lui ait automatiquement envoyé afin qu'il puisse finaliser son inscription.</p>


<html:form action="/lesoptions.do" method="post">
	<table>
			<tr>
				<th class="entete"></th>
				<th class="entete" colspan="2" scope="col">
				<h2>Configuration</h2>
				</th>
			</tr>

			<tr class="champ">
				<th scope="row"><label for="serveurSMTP">serveur SMTP : </label></th>
				<td><html:text property="serveursmtp" errorStyleClass="error" styleId="serveurSMTP" value="${parameters[0]}" />
					<html:errors property="serveursmtp" />
				</td>
			</tr>
			<tr class="champ">
				<th scope="row"> <label for="hote">Hôte : </label></th>
				<td><html:text property="hote" errorStyleClass="error" styleId="hote" value="${parameters[1]}" />
					<html:errors property="hote" />  
				</td>
			</tr>
			<tr class="champ">
				<th scope="row"><label for="pass">Mot de passe : </label></th>
				<td><html:password property="motdepasse" errorStyleClass="error" styleId="pass" value="${parameters[2]}" />
					<html:errors property="motdepasse" />   
				</td>
			</tr>
			<tr class="champ">
				<th scope="row"><label for="adresseFSNet">Adresse site FSNet : </label></th>
				<td><html:text property="adressefsnet" errorStyleClass="error" styleId="adresseFSNet" value="${parameters[3]}" />
					<html:errors property="adressefsnet" />   
				</td>
			</tr>		
			<tr class="champ">
				<th scope="row"><label for="port">Port : </label></th>
				<td><html:text property="port" errorStyleClass="error" styleId="port" value="${parameters[4]}" />
					<html:errors property="port" /> 
				</td>
			</tr>
		</table>
		<label class="button"> 
			<html:submit title="Enregistrer" >Enregistrer</html:submit>
		<!-- <input type="submit" name="Submit"
			value="Enregistrer" title="Enregistrer" />-->
		 </label>
</html:form>
</div>
<div id="side"></div>
</div>


<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>