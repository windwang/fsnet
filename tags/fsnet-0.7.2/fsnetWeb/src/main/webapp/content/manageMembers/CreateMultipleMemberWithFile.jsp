<%--
	author : SAID Mohamed
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>



<h3><bean:message key="members.multipleWithFile" /></h3>

<html:form action="/CreateMultipleMemberFile">
	<table>
	<tr>
		<td colspan="2">
			<bean:message key="members.createMultipleFileFormat" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<bean:message key="members.createMultipleFileIndications" />
		</td>
	</tr>
	<tr class="errorMessage">
		<td colspan="2">
			<html:errors property="fileMultipleMember" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<html:file property="fileMultipleMember" size="60" styleId="multipleMember" errorStyleClass="error"/>
		</td>
	<jsp:include page="/content/manageMembers/SamePartForMember.jsp"/>
	</table>
</html:form>

