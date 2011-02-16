<%-- 
		 Author : Morad LYAMEN
		
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>

<h3><bean:message key="groups.create" /></h3>



<html:form action="/CreateGroup">
	<table id="CreateGroup">
		<tr>
			<td>
				<label for="name"> 
					<bean:message key="groups.name" />
				</label>
			</td>
			<td>
				<html:text property="name" styleId="name" errorStyleClass="error" />
			</td>
		</tr>
		
		<tr class="errorMessage">
			<td colspan="2">
				<html:errors property="name" />
			</td>
		</tr>
		
		<tr>
			<td>
				<label for="owner"> 
					<bean:message key="groups.owner" />
				</label>
			</td>
			<td>
				<html:text property="owner" styleId="owner" errorStyleClass="error" />
			</td>
			
		</tr>
		
		<tr class="errorMessage" >
			<td colspan="2">
				<html:errors property="owner" />
			</td>
		</tr>
				
		<tr>
			<td colspan="2">
				<html:submit styleClass="button">
					<bean:message key="members.validate" />
				</html:submit>
			</td>
		</tr>
		
	</table>
</html:form>

<script type="text/javascript">
	
</script>