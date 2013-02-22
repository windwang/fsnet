<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tr>
	<td colspan="2"><label for="message"> <s:text name="members.message" />
	</label></td>
</tr>

<tr>
	<td colspan="2"><c:set var="welcomeMain">
			<s:text name="members.welcomeMessage.main" />
		</c:set> <c:if test="${!empty CreateMemberForm.map.message}">
			<c:set var="welcomeMain">${CreateMemberForm.map.message}</c:set>
		</c:if> <c:if test="${!empty CreateMultipleMemberForm.map.message}">
			<c:set var="welcomeMain">${CreateMultipleMemberForm.map.message}</c:set>
		</c:if> <s:textarea property="message" styleId="message"
			errorStyleClass="error" value="%{welcomeMain}" rows="5" cols="50" />
		<div class="errorMessage">
			<s:fielderror name="message" />
		</div></td>
</tr>

<tr>
	<td colspan="2"><s:text name="members.welcomeMessage.warning" />
	</td>
</tr>

<tr>
	<td colspan="2" class="tableButton"><s:submit type="button" cssClass="button">
			<s:text name="members.validate" />
		</s:submit></td>
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