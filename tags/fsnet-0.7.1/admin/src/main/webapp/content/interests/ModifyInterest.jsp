<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if test="${not empty requestScope.allInterests}">
	<h3><bean:message key="interests.5" /></h3>
	<html:javascript formName="/ModifyInterest" />
	<div id="modify">
	<html:form action="/ModifyInterest">
		<html:errors property="modifiedInterestId" />
		<br />
		<html:errors property="modifiedInterestName" />
		<br />
		<html:select property="modifiedInterestId" styleClass="select" onchange="updateParentInterest()">
			<html:option value="">
				<bean:message key="interests.1" />
			</html:option>
			<c:forEach var="interest" items="${requestScope.allInterests}">
				<html:option value="${interest.id}">${interest.name}</html:option>
			</c:forEach>
		</html:select>
		<bean:message key="interests.15"/>
		<html:select property="parentInterestId" styleClass="select">
			<html:option value="">
				<bean:message key="interests.8"/>
			</html:option>
			<c:forEach var="interest" items="${requestScope.allInterests}">
				<html:option value="${interest.id}">${interest.name}</html:option>
			</c:forEach>
		</html:select>
		<html:text property="modifiedInterestName" />
		<html:hidden property="allInterestsId" value="${ allInterestsId }"/>
		<html:submit styleClass="button" >
      		<bean:message key="interest.validate"/>
      	</html:submit>
	</html:form>
	</div>
</c:if>