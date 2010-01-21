<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>



<a href="AskContact.do">Ajouter un contact</a>
<h3>Liste de vos contacts:</h3>
<c:forEach var="contact" items="${listContacts}">
    -${contact.nom}
    	<html:link action="/DeleteContact">
		<html:param name="entityDeleted" value="${contact.id}" />
    		Delete
    	</html:link>
</c:forEach>

<h3>Liste des demandes reçues:</h3>
<c:forEach var="contact" items="${listDemands}">
    -${contact.nom} 
    	<html:link action="/AcceptContact">
		<html:param name="entityAccepted" value="${contact.id}" />
    		Accept
    	</html:link> 
    		ou 
    	<html:link action="/RefuseContact">
		<html:param name="entityRefused" value="${contact.id}" />
    		Refuse
    	</html:link>
</c:forEach>

<h3>Liste de vos demandes effectuées:</h3>
<c:forEach var="contact" items="${listRequests}">
    -${contact.nom}
</c:forEach>

