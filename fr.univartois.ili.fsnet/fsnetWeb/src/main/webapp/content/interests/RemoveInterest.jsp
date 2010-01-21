<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<c:if test="${not empty user.lesinterets}">
	<h2>Retirer un intérêt</h2>
	<html:javascript formName="/RemoveInterest"/>
	<html:form action="/RemoveInterest">
		<html:select property="interestId">
			<html:option value="0">Intérêt</html:option>
			<c:forEach var="interest" items="${user.lesinterets}">
				<html:option value="${interest.id}">${interest.nomInteret}</html:option>	
			</c:forEach>
		</html:select>
		<html:submit/>
	</html:form>
	<html:errors property="error.interest.remove"/>
</c:if>
