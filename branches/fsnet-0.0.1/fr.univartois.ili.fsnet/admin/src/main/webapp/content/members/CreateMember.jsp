<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>



<h3><bean:message key="members.create" /></h3>

<html:form action="/CreateMember">
	<table id="CreateMember">
		<tr class="errorMessage">
			<td colspan="2"><html:errors property="name" /></td>
		</tr>
		<tr>
			<td><label for="name"> <bean:message key="members.name" />
			</label></td>
			<td><html:text property="name" styleId="name"
				errorStyleClass="error" /></td>
		</tr>
<tr class="errorMessage" >
			<td colspan="2"><html:errors property="firstName" /></td>
		</tr>
		<tr>
			<td><label for="firstName"> <bean:message
				key="members.firstName" /> </label></td>
			<td><html:text property="firstName" styleId="firstName"
				errorStyleClass="error" /></td>
		</tr>
		<tr class="errorMessage">
			<td colspan="2"><html:errors property="email" /></td>
		</tr>
		<tr>
			<td><label for="email"> <bean:message
				key="members.email" /> </label></td>
			<td><html:text property="email" styleId="email"
				errorStyleClass="error" /></td>
		</tr>
		
		<tr>
			<td colspan="2"><html:submit styleClass="button">
				<bean:message key="members.validate" />
			</html:submit></td>
		</tr>
	</table>
</html:form>

