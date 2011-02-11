<%-- 
		 Author : Alexandre Théry
	
--%>
	<%@page contentType="text/html" pageEncoding="UTF-8"%>
	<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
	<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
		<tr>
			<td colspan="2">
				<label for="message"> 
					<bean:message key="members.message" /> :
				</label>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<html:textarea property="message" styleId="message" errorStyleClass="error" rows="5" cols="50"/>
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