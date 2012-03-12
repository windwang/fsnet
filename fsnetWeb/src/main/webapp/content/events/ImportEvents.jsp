<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<link type="text/css" href="css/jquery.qtip.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.qtip.js"></script>

<div id=formImportEvent>
<html:form styleId="formImportEventsFromFile" action="/importEventsFromFile" enctype="multipart/form-data">
		<fieldset class="fieldsetAppli">
			<legend class="legendHome"><bean:message key="events.form.importIcsFile" /></legend>
			<label for="icsFile"><bean:message key="events.form.browseIcsFile" /></label><input size="58%" type="file" name="icsFile" id="icsFile" />
			<input type="submit" value="<bean:message key="events.button.import" />"/>
		</fieldset>
</html:form>
</div>