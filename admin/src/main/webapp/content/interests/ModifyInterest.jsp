<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if test="${not empty requestScope.allInterests}">
	<fieldset class="fieldsetAdmin">
		<legend class="legendAdmin">
			<bean:message key="interests.title.modify" />
		</legend>

		<html:javascript formName="/ModifyInterest" />
		<div id="modify">
			<html:form action="/ModifyInterest">
				<table class="fieldsetTableAdmin">
					<tr>
						<td><label for="modifiedInterestId"><bean:message
									key="interests.form.oldName" /></label></td>
						<td><html:select property="modifiedInterestId"
								styleClass="select" onchange="updateParentInterest()">
								<html:option value="">
								</html:option>
								<c:forEach var="interest" items="${requestScope.allInterests}">
									<html:option value="${interest.id}">${interest.name}</html:option>
								</c:forEach>
							</html:select>
							<div class="errorMessage">
								<html:errors property="modifiedInterestId" />
							</div></td>
					</tr>

					<tr>
						<td><label for="parentInterestId"><bean:message
									key="interests.form.parent" /></label></td>
						<td><html:select property="parentInterestId"
								styleClass="select">
								<html:option value="">
								</html:option>
								<c:forEach var="interest" items="${requestScope.allInterests}">
									<html:option value="${interest.id}">${interest.name}</html:option>
								</c:forEach>
							</html:select></td>
					</tr>

					<tr>
						<td><label for="modifiedInterestName"><bean:message
									key="interests.form.newName" /></label></td>
						<td><html:text property="modifiedInterestName" />
							<div class="errorMessage">
								<html:errors property="modifiedInterestName" />
							</div></td>
					</tr>

					<tr>
						<td colspan="2" align="right"><html:hidden
								property="allInterestsId" value="${ allInterestsId }" /> <html:submit
								styleClass="button">
								<bean:message key="interests.button.modify" />
							</html:submit></td>
						<td></td>
					</tr>
				</table>
			</html:form>
		</div>
	</fieldset>
</c:if>