<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="communities.title.create" />
	</legend>

	<table class="inLineTableDashBoardFieldset fieldsetTable">
		<tr>
			<td><html:form action="/CreateCommunity">
					<table id="CreateCommunity">
						<tr>
							<td><label for="name"> <bean:message
										key="communities.form.name" /> :
							</label></td>
							<td><html:text property="name" styleId="name"
									errorStyleClass="error" /></td>
						</tr>
						<tr class="errorMessage">
							<td colspan="2"><html:errors property="name" /> <html:errors
									property="createdCommunityName" /></td>
						</tr>
						<tr>
							<td colspan="2"><c:import url="/InterestCheckBoxes.do" /></td>
						</tr>
						<tr>
							<td colspan="2"><html:submit styleClass="button">
									<bean:message key="communities.button.validate" />
								</html:submit></td>
						</tr>
					</table>
				</html:form></td>
		</tr>
	</table>
</fieldset>
