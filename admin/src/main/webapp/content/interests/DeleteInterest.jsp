<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if test="${not empty requestScope.allInterests}">
	<fieldset class="fieldsetAdmin">
		<legend class="legendAdmin">
			<bean:message key="interests.title.delete" />
		</legend>

		<html:javascript formName="/DeleteInterest" />

		<html:form action="/DeleteInterest">
			<table class="fieldsetTableAdmin">
				<tr>
					<td><html:select property="deletedInterestId"
							styleClass="select">
							<html:option value="" />
							<c:forEach var="interest" items="${requestScope.allInterests}">
								<html:option value="${interest.id}">${interest.name}</html:option>
							</c:forEach>
						</html:select>
						<div class="errorMessage">
							<html:errors property="deletedInterestId" />
						</div></td>
				</tr>

				<tr>
					<td align="right"><html:submit styleClass="button">
							<bean:message key="interests.button.delete" />
						</html:submit></td>
				</tr>
			</table>
		</html:form>
	</fieldset>
</c:if>
