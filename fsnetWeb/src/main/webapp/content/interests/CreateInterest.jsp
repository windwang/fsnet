<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="interests.title.create2" />
	</legend>
	<div class="space"></div>
	<html:javascript formName="/CreateInterest" />
	<html:form action="/CreateInterest">
		<table id="CreateInterest" class="inLineTableDashBoardFieldset">
			<tr>
				<bean:message key="interests.message.create" />
			</tr>
			<tr>
				<td><bean:message key="interests.title.parent" /></td>
				<td><html:select property="parentInterestId"
						styleClass="select">
						<html:option value="">
							<bean:message key="interests.list.no" />
						</html:option>
						<c:forEach var="interest" items="${requestScope.allInterests}">
							<html:option value="${interest.id}">${interest.name}</html:option>
						</c:forEach>
					</html:select></td>
				<td><html:text property="createdInterestName"
						styleId="createdInterestName" /></td>
				<td><html:submit styleClass="button">
						<bean:message key="interests.button.create" />
					</html:submit></td>
			</tr>
			<tr class="errorMessage">
				<td></td>
				<td colspan="2"><html:errors property="createdInterestName" /></td>
			</tr>
		</table>
	</html:form>
</fieldset>