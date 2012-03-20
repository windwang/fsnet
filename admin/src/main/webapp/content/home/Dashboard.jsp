<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="pageTitle.0" />
	</legend>

	<table class="inLineTable fieldsetTableAdmin">
		<tr>
			<td><bean:message key="welcome.welcome" /></td>
		</tr>
		<tr>
			<td><bean:message key="welcome.message1" /></td>
		</tr>
		<tr>
			<td><bean:message key="welcome.message2" /></td>
		</tr>
	</table>
</fieldset>