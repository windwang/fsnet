<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="hubs.title.create" />
	</legend>
<<<<<<< HEAD
	<table id="CreateHub" class="inLineTable tableStyle">
		<s:form action="/CreateHub">
			<s:hidden name="communityId" value="%{param.communityId}" />
=======
	<table id="CreateHub"
		class="inLineTable tableStyle">
		<html:form action="/CreateHub">
			<s:hidden name="communityId" value="${param.communityId}" />
>>>>>>> 75d3d39903713564d348b5ead824e237db25c0ee
			<tr>
				<td><label for="hubName"> <s:text name="hubs.form.name" />
				</label></td>
				<td><s:text name="hubName" var="hubName" /></td>
			</tr>
			<tr>
				<td colspan="2"><c:import url="/InterestCheckBoxes.do" /></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><s:submit
						styleClass="btn btn-inverse">
						<s:text name="hubs.button.create" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
</fieldset>
<div class="clear"></div>