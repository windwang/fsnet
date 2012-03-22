<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if test="${not empty requestScope.myCommunities}">
	<fieldset class="fieldsetAppli">
		<legend class="legendHome">
			<bean:message key="commmunities.title.modify" />
		</legend>

		<table id="ModifyCommunity" class="inLineTable fieldsetTableAppli">
			<html:form action="/ModifyCommunity">
				<tr>
					<td><label for="oldCommunityName"><bean:message
								key="communities.form.oldName" /></label></td>
					<td><html:select property="oldCommunityName"
							styleClass="select" styleId="oldCommunityName">
							<html:option value="">
							</html:option>
							<c:forEach var="community" items="${requestScope.myCommunities}">
								<html:option value="${community.title}">${community.title}</html:option>
							</c:forEach>
						</html:select>
						<div class="errorMessage">
							<html:errors property="oldCommunityName" />
						</div></td>

				</tr>
				<tr>
					<td><label for="newCommunityName"><bean:message
								key="communities.form.newName" /></label></td>
					<td><html:text property="newCommunityName"
							styleId="newCommunityName" />
						<div class="errorMessage">
							<html:errors property="newCommunityName" />
						</div></td>
				</tr>

				<tr>
					<td colspan="2" class="tableButton"><html:submit
							styleClass="button">
							<bean:message key="communities.button.modify" />
						</html:submit></td>
				</tr>
			</html:form>
		</table>
	</fieldset>
</c:if>