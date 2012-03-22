<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<link type="text/css" href="css/jquery.qtip.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery.qtip.js"></script>

<div id=formImportEvent>

	<fieldset class="fieldsetAppli">
		<legend class="legendHome">
			<bean:message key="events.form.importIcsFile" />
		</legend>

		<html:form styleId="formImportEventsFromFile"
			action="/importEventsFromFile" enctype="multipart/form-data">
			<table class="inLineTable fieldsetTableAppli">
				<tr>
					<td><label for="icsFile"><bean:message
								key="events.form.browseIcsFile" /></label></td>
					<td><input size="40%" type="file" name="icsFile" id="icsFile" />
					<logic:messagesPresent
						property="icsFile">
						<div class="errorMessage">
							<html:errors property="icsFile" />
						</div>
					</logic:messagesPresent>
					</td>
				</tr>
				<tr>	
					<td colspan="2" class="tableButton"><input class="button" type="submit"
						value="<bean:message key="events.button.import" />" /></td>
				</tr>
			</table>
		</html:form>
	</fieldset>

</div>