<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<h2>Ajouter un contacts:</h2>
<form>
	<select name="entitySelected">
		<c:forEach var="contact" items="${listEntities}">
			<option value="${contact.id}">${contact.nom}</option>
		</c:forEach>
	</select>
</form>