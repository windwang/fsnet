
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${not empty hubResults}">
	<h3>
		<bean:message key="hubs.modify" />
	</h3>
	<c:set var="hub" value="${hubResults}" />
	<html:form action="/ModifyYourHub" method="post">
		<html:hidden property="communityId" value="${param.communityId}" />
		<div class="errorMessage"><html:errors property="hubId" /></div>
		<div class="errorMessage"><html:errors property="hubAlreadyExistsErrors" /></div>
		<br />
		<html:select property="hubId" styleClass="select">
			<html:option value="">
				<bean:message key="hubs.hub" />
			</html:option>
			<c:forEach var="hub" items="${hubResults}">
				<c:if test="${sessionScope.userId eq hub.creator.id}">
					<html:option value="${hub.id}">${hub.title}</html:option>
				</c:if>
			</c:forEach>
		</html:select>
		<html:text property="modifiedHubName" />
		<html:submit styleClass="button">
			<bean:message key="hubs.modify" />
		</html:submit>
	</html:form>
</c:if>
