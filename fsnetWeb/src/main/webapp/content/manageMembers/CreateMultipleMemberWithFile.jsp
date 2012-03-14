<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="members.multipleWithFile" />
	</legend>

	<html:form action="/CreateMultipleMemberFile">
		<table class="inLineTable fieldsetTableAppli">
			<tr>
				<td colspan="2"><bean:message
						key="members.createMultipleFileFormat" /></td>
			</tr>
			<tr>
				<td colspan="2"><bean:message
						key="members.createMultipleFileIndications" /></td>
			</tr>
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="fileMultipleMember" /></td>
			</tr>
			<tr>
				<td colspan="2"><html:file property="fileMultipleMember"
						size="60" styleId="multipleMember" errorStyleClass="error" /></td>
			</tr>
			
			<%@ include file="SamePartForMember.jsp"%>
		</table>
	</html:form>
</fieldset>

