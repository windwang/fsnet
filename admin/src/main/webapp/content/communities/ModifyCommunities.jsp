<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if test="${not empty requestScope.communitiesListPaginator.resultList}">
	<fieldset class="fieldsetAdmin">
       <legend class="legendAdmin"><bean:message key="commmunities.modify" /></legend>
	   <table class="fieldsetTableAdmin"><tr><td>
	   <html:form action="/ModifyCommunity">
		<div class="errorMessage"><html:errors property="modifierCommunityName" /></div>
		<div class="errorMessage"><html:errors property="modifiedCommunityName" /></div>
		<p><html:select property="modifierCommunityName" styleClass="select">
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
		</p>
	  </html:form>
	  </td></tr></table>
	</fieldset>
</c:if>