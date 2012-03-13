<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="communities.title.create" />
	</legend>

	<html:form action="/CreateCommunity">

		<table id="CreateCommunity" class="fieldsetTableAdmin">
			<tr>
				<td><label for="name"> <bean:message
							key="communities.form.name" />
				</label></td>
				<td><html:text property="name" styleId="name"
						errorStyleClass="error" /></td>
			</tr>
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="name" /> <html:errors
						property="createdCommunityName" /></td>
			</tr>
			<tr>
				<td><label><bean:message key="communities.form.creator" /></label></td>
				<td><html:select property="socialEntityId" styleClass="select">
						<html:option value="">
						</html:option>
						<c:forEach var="socialEntity" items="${allMembers}">
							<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
						</c:forEach>
					</html:select></td>
			</tr>
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="socialEntityId" /></td>
			</tr>

			<tr>
				<td colspan="2"><html:submit styleClass="button">
						<bean:message key="communities.button.create" />
					</html:submit></td>
			</tr>
		</table>
	</html:form>
</fieldset>