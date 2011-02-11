<%-- 
		 Author : Alexandre Théry
		Modifief by Stephane Gronowski
--%>
	<%@page contentType="text/html" pageEncoding="UTF-8"%>
	<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
	<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<tr>
			<td colspan="2">
				<label for="message"> 
					<bean:message key="members.message" /> :
				</label>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<bean:message key="members.welcomeMessage.welcome" /> "<bean:message key="members.name" />" "<bean:message key="members.firstName" />"
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<html:textarea property="message" styleId="message"  errorStyleClass="error" rows="5" cols="50">
				</html:textarea>
				
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<bean:message key="members.welcomeMessage.url" /> "url". 
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<bean:message key="members.welcomeMessage.password" /> "<bean:message key="members.password" />" . 
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<bean:message key="members.welcomeMessage.donotreply" /> 
			</td>
		</tr>
		
		<tr class="errorMessage">
			<td colspan="2">
				<html:errors property="message" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<html:submit styleClass="button">
					<bean:message key="members.validate" />
				</html:submit>
			</td>
		</tr>