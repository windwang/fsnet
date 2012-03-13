<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="communities.title.create" />
	</legend>

	<table id="CreateCommunity" class="fieldsetTableAdmin">
		<html:form action="/CreateCommunity">
			<tr>
				<td><label for="name"> <bean:message
							key="communities.form.name" />
				</label></td>
				<td><html:text property="name" styleId="name"
						errorStyleClass="error" />
					<div class="errorMessage">
						<html:errors property="name" />
						<html:errors property="createdCommunityName" />
					</div></td>
			</tr>

			<tr>
				<td><label for="socialEntityId"><bean:message
							key="communities.form.creator" /></label></td>
				<td><html:select property="socialEntityId" styleClass="select">
						<html:option value="">
						</html:option>
						<c:forEach var="socialEntity" items="${allMembers}">
							<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
						</c:forEach>
					</html:select>
					<div class="errorMessage">
						<html:errors property="socialEntityId" />
					</div></td>
			</tr>

			<tr>
				<td colspan="2" align="right"><html:submit styleClass="button">
						<bean:message key="communities.button.create" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>