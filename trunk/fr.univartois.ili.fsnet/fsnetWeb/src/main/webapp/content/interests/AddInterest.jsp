<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<logic:present name="listInterests">
	<h2>Ajouter un intérêt</h2>
	<html:javascript formName="/AddInterest"/>
	<html:form action="/AddInterest">
		<html:select property="interestId">
			<c:forEach var="interest" items="${listInterests}">
				<html:option value="${interest.id}">${interest.nomInteret}</html:option>	
			</c:forEach>
		</html:select>
		<html:submit/>
	</html:form>
	<html:errors property="error.interest.add"/>
</logic:present>