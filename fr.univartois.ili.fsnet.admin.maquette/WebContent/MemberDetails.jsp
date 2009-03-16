<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="boxtop"></div>
<div class="box"><admin:inscription var="member"
	entite="${param.ent}">
	<h3>${member.entite.nom} ${member.entite.prenom}</h3>

	 <span class="item"> <span class="sidedate">Email<br />
	</span> <strong>${member.entite.email}</strong><br />
	 
	<c:if test="${member.etat eq 'Inscrit'}">
		 <span class="sidedate">Date d'entrée<br /></span>
		  <strong>${member.entite.dateNaissance}</strong><br />
		<span class="sidedate">Date de naissance<br /></span>
		<strong>${member.entite.dateNaissance}</strong><br />
		<span class="sidedate">Adresse<br /></span>
		<strong>${member.entite.adresse}</strong><br /> 
		<span class="sidedate">Tel<br /></span>
		<strong>${member.entite.numTel}</strong><br />
		<span class="sidedate">Profession<br /></span>
		<strong>${member.entite.profession}</strong><br />
		
	</c:if>
	</span>
</admin:inscription></div>
<div class="boxbottom"></div>

</body>
</html>