<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<h3>Ajouter un intérêt</h3>
<c:choose>
	<c:when test="${not empty listInterests}">
		<html:javascript formName="/AddInterest"/>
		<html:form action="/AddInterest">
			<html:select property="addedInterestId">
				<html:option value="0">Intérêt</html:option>
				<c:forEach var="interest" items="${listInterests}">
					<html:option value="${interest.id}">${interest.nomInteret}</html:option>	
				</c:forEach>
			</html:select>
			<html:submit/>
		</html:form>
		<html:errors property="addedInterestId"/>
	</c:when>
	<c:otherwise>
		Vous disposez de tous les intérêts disponibles
	</c:otherwise>
</c:choose>