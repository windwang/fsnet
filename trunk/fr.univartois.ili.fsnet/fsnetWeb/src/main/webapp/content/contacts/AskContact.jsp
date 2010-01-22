<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<h2>Ajouter un contacts:</h2>
<html:form method="get" action="/ContactDemand">
    <html:select property="entitySelected">
        <c:forEach var="contact" items="${listEntities}">
            <html:option value="${contact.id}">${contact.nom}</html:option>
        </c:forEach>
    </html:select>
    <html:submit >Envoyer demande</html:submit>
</html:form>