<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if test="${not empty requestScope.communitiesListPaginator.resultList}">
	<h3><bean:message key="commmunities.modify" /></h3>
	<html:form action="/ModifyCommunity">
		<html:errors property="modifierCommunityName" />
		<html:errors property="modifiedCommunityName" /><br/>
		<html:select property="modifierCommunityName" styleClass="select">
			<html:option value="">
				<bean:message key="community.default" />
			</html:option>
			<c:forEach var="community"
				items="${requestScope.communitiesListPaginator.resultList}">
				<html:option value="${community.title}">${community.title}</html:option>
			</c:forEach>
		</html:select>
		<bean:message key="communities.newName" />
		<html:text property="modifiedCommunityName" />
		<html:submit styleClass="button">
			<bean:message key="interest.validate" />
		</html:submit>
	</html:form>
</c:if>