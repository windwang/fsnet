<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="groups.Modify" />
	</legend>

	<html:form action="/ModifyGroup" onsubmit="Valider()">
		<%@ include file="SamePartForGroup.jsp" %>
	</html:form>
</fieldset>
