<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<s:text name="members.createMultiple" />
	</legend>

	<s:form action="/CreateMultipleMember">
		<table class="inLineTable fieldsetTableAppli">
			<tr>
				<td colspan="2"><s:text name="members.createMultipleIndications"/></td>
			</tr>

			<tr>
				<td colspan="2"><s:text name="members.createMultipleFormat"/></td>
			</tr>

			<tr>
				<td colspan="2"><s:textarea property="multipleMember"
						styleId="multipleMember" errorStyleClass="error" cols="80"
						rows="6" />
					<div class="errorMessage">
						<s:fielderror property="multipleMember" />
					</div></td>
			</tr>
			
			<%@ include file="SamePartForMember.jsp" %>
		</table>
	</s:form>
</fieldset>
