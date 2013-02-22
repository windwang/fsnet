<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="interests.title.create2" />
	</legend>

	<div class="space"></div>
	<html:javascript formName="/CreateInterest" />
	<s:form action="/CreateInterest">
		<table id="CreateInterest" class="inLineTable tableStyle">
			<tr>
				<s:text name="interests.message.create" />
			</tr>

			<tr>
				<td><label for="parentInterestId"><s:text
							name="interests.title.parent" /></label></td>

				<td><select>
						<option value="">
							<s:text name="interests.list.no" />
						</option>
						<c:forEach var="interest" items="${requestScope.allInterests}">
							<option value="%{interest.id}">${interest.name}</option>
						</c:forEach>
				</select></td>

			</tr>

			<tr>
				<td><label for="createdInterestName"><s:property
							value="interests.form.name" /></label></td>
				<td><s:textfield property="createdInterestName"
						styleId="createdInterestName" />
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><s:submit
						styleClass="button">
						<s:text name="interests.button.create" />
					</s:submit></td>
			</tr>
		</table>
	</s:form>
</fieldset>