<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="interests.title.create" />
	</legend>

	<html:javascript formName="/CreateInterest" />

	<html:form action="/CreateInterest">
		<table class="inLineTable fieldsetTableAdmin">
			<tr>
				<td colspan="2"><bean:message key="interests.from.multiple" /></td>
			</tr>

			<tr>
				<td><label for="parentInterestId"><bean:message
							key="interests.form.parent" /></label></td>
				<td><html:select property="parentInterestId"
						styleClass="select" styleId="parentInterestId">
						<html:option value="" />
						<c:forEach var="interest" items="${requestScope.allInterests}">
							<html:option value="${interest.id}">${interest.name}</html:option>
						</c:forEach>
					</html:select></td>
			</tr>

			<tr>
				<td><label for="createdInterestName"><bean:message
							key="interests.form.name" /></label></td>
				<td><html:text property="createdInterestName"
						styleId="createdInterestName" />
					<div class="errorMessage">
						<html:errors property="createdInterestName" />
					</div></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><html:submit styleClass="button">
						<bean:message key="interests.button.create" />
					</html:submit></td>
			</tr>
		</table>
	</html:form>
</fieldset>