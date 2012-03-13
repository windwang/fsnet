<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if
	test="${not empty requestScope.communitiesListPaginator.resultList}">
	<fieldset class="fieldsetAdmin">
		<legend class="legendAdmin">
			<bean:message key="commmunities.title.modify" />
		</legend>
		
		<table class="fieldsetTableAdmin">
			<html:form action="/ModifyCommunity">
				<div class="errorMessage">
					<html:errors property="modifierCommunityName" />
				</div>
				<div class="errorMessage">
					<html:errors property="modifiedCommunityName" />
				</div>
				<p>
					<html:select property="modifierCommunityName" styleClass="select">
						<html:option value="">
						</html:option>
						<c:forEach var="community"
							items="${requestScope.communitiesListPaginator.resultList}">
							<html:option value="${community.title}">${community.title}</html:option>
						</c:forEach>
					</html:select>
					<bean:message key="communities.form.newName" />
					<html:text property="modifiedCommunityName" />
					<html:submit styleClass="button">
						<bean:message key="communities.button.modify" />
					</html:submit>
				</p>
			</html:form>
		</table>
	</fieldset>
</c:if>