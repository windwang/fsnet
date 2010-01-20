<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h2>Liste de vos contacts:</h2>
<form>
<select name="entitySelected">
<c:forEach var="contact" items="${requestScope.listEntities}">
    <option value="${contact}">${contact.nom}</option>
</c:forEach>
</select>
</form>