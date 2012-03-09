<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if test="${not empty requestScope.myCommunitiesPaginator.resultList}">
	<fieldset class="fieldsetAppli">
		<legend class="legendHome">
			<bean:message key="commmunities.title.modify" />
		</legend>

		<table id="ModifyCommunity"
			class="inLineTableDashBoardFieldset fieldsetTable">
			<html:form action="/ModifyCommunity">
				<tr>
					<td colspan="3">
						<div class="errorMessage">
							<html:errors property="modifierCommunityName" />
						</div>
						<div class="errorMessage">
							<html:errors property="modifiedCommunityName" />
							<br />
						</div>
					</td>
				</tr>
				<tr>
					<td><html:select property="modifierCommunityName"
							styleClass="select">
							<html:option value="">
							</html:option>
							<c:forEach var="community"
								items="${requestScope.myCommunitiesPaginator.resultList}">
								<html:option value="${community.title}">${community.title}</html:option>
							</c:forEach>
						</html:select>
					<td><bean:message key="communities.form.newName" /></td>
					<td><html:text property="modifiedCommunityName" /></td>
				</tr>
				<tr>
					<td colspan="3" align="right"><html:submit styleClass="button">
							<bean:message key="communities.button.update" />
						</html:submit></td>
				</tr>
			</html:form>
		</table>
	</fieldset>
</c:if>