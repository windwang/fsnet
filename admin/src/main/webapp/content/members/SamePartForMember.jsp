<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tr>
	<td colspan="2"><label for="message"> <bean:message
				key="members.message" /></label></td>
</tr>

<tr>
	<td colspan="2"><c:set var="welcomeMain">
			<bean:message key="members.welcomeMessage.main" />
		</c:set> <c:if test="${!empty CreateMemberForm.map.message}">
			<c:set var="welcomeMain">${CreateMemberForm.map.message}</c:set>
		</c:if> <c:if test="${!empty CreateMultipleMemberForm.map.message}">
			<c:set var="welcomeMain">${CreateMultipleMemberForm.map.message}</c:set>
		</c:if> <html:textarea property="message" styleId="message"
			errorStyleClass="error" value="${welcomeMain}" rows="5" cols="50" />
		<div class="errorMessage">
			<html:errors property="message" />
		</div></td>
</tr>

<tr>
	<td colspan="2"><bean:message key="members.welcomeMessage.warning" />
	</td>
</tr>

<tr>
	<td colspan="2" align="right"><html:submit styleClass="button">
			<bean:message key="members.validate" />
		</html:submit></td>
</tr>

<c:if test="${!empty success}">
	<script type="text/javascript">
		jQuery(function() {
			popup();
		});
		success = null;
	</script>
	<div id="osx-modal-content" class="simplemodal-data">
		<div id="osx-modal-title">Message</div>
		<div class="close">
			<a class="simplemodal-close" href="#">X</a>
		</div>
		<div id="osx-modal-data">
			<p>
				<c:out value="${success}" />
			</p>
		</div>
	</div>
</c:if>