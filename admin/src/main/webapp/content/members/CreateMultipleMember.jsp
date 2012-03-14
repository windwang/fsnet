<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="members.createMultiple" />
	</legend>

	<html:form action="/CreateMultipleMember">
		<table class="inLineTable fieldsetTableAdmin">
			<tr>
				<td colspan="2"><bean:message
						key="members.createMultipleIndications" /></td>
			</tr>

			<tr>
				<td colspan="2"><label for="multipleMember2"><bean:message
							key="members.createMultipleFormat" /></label></td>
			</tr>

			<tr>
				<td colspan="2"><html:textarea property="multipleMember"
						styleId="multipleMember2" errorStyleClass="error" cols="80"
						rows="6" />
					<div class="errorMessage">
						<html:errors property="multipleMember" />
					</div></td>
			</tr>

			<%@ include file="SamePartForMember.jsp"%>
		</table>
	</html:form>
</fieldset>