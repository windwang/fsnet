<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<link type="text/css" href="css/jquery.qtip.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery.qtip.js"></script>

<div id=formImportEvent>

	<fieldset class="fieldsetCadre">
		<legend>
			<bean:message key="events.form.importIcsFile" />
		</legend>

		<html:form styleId="formImportEventsFromFile"
			action="/importEventsFromFile" enctype="multipart/form-data">
			<table class="inLineTable  tableStyle">
				<tr>
					<td><label for="icsFile"><bean:message
								key="events.form.browseIcsFile" /></label></td>
					<td><input size="40%" type="file" name="icsFile" id="icsFile" />
						<logic:messagesPresent property="icsFile">
							<div class="errorMessage">
								<html:errors property="icsFile" />
							</div>
						</logic:messagesPresent></td>
				</tr>
				<tr>
					<td colspan="2" class="tableButton"><input
						class="button btn btn-inverse" type="submit"
						value="<bean:message key="events.button.import" />" /></td>
				</tr>
			</table>
		</html:form>
	</fieldset>

</div>