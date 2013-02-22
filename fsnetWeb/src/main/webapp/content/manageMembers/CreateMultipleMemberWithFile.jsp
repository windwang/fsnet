<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<s:text name="members.multipleWithFile" />
	</legend>

	<s:form action="/CreateMultipleMemberFile">
		<table class="inLineTable fieldsetTableAppli">
			<tr>
				<td colspan="2"><s:text name="members.createMultipleFileFormat" /></td>
			</tr>
			<tr>
				<td colspan="2"><s:text name="members.createMultipleFileIndications" /></td>
			</tr>
<!-- 			<tr class="errorMessage"> -->
<%-- 				<td colspan="2"><s:fielderror property="fileMultipleMember" /></td> --%>
<!-- 			</tr> -->
			<tr>
				<td colspan="2"><s:file property="fileMultipleMember"
						size="60" styleId="multipleMember" errorStyleClass="error" /></td>
			</tr>
			
			<%@ include file="SamePartForMember.jsp"%>
		</table>
	</s:form>
</fieldset>

