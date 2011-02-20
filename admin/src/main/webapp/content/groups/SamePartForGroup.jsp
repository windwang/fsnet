<%-- 
		 Author : SAID Mohamed 
--%>



<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table id="CreateGroup">
	<tr>
		<td><label for="name"> <bean:message key="groups.name" />
		</label></td>
		<td colspan="3"><html:text property="name" styleId="name"
			errorStyleClass="error" /></td>
	</tr>

	<tr class="errorMessage">
		<td colspan="2"><html:errors property="name" /></td>
	</tr>

	<tr>
		<td><label for="description"> <bean:message
			key="groups.description" /> </label></td>
		<td colspan="3"><c:set var="welcomeMain">
			<bean:message key="groups.description.message" />
		</c:set>
				<html:textarea property="description" styleId="description"
					errorStyleClass="error" cols="36" rows="6">
				</html:textarea>
			</td>
	</tr>

	<tr class="errorMessage">
		<td colspan="2"><html:errors property="description" /></td>
	</tr>

	<tr>
		<td><label for="socialEntityId"> <bean:message
			key="groups.owner" /> </label></td>
		<td colspan="3"><html:select property="socialEntityId"
			styleClass="select" value="${ masterGroup.id }">
			<html:option value="" disabled="true">
				<bean:message key="groups.listMember" />
			</html:option>
			<c:forEach var="socialEntity" items="${allMembers}">
				<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
			</c:forEach>
		</html:select></td>
	</tr>

	<tr class="errorMessage">
		<td colspan="2"><html:errors property="socialEntityId" /></td>
	</tr>

	<tr>
		<td ROWSPAN="2"><label for="members"> <bean:message
			key="groups.members" /> </label></td>

		<td ROWSPAN="2">
		<div><bean:message key="groups.members.refused" /></div>
		<html:select property="memberListLeft" styleClass="select" size="5"
			multiple="multiple">
			<c:forEach var="socialEntity" items="${refusedMembers}">
				<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
			</c:forEach>
		</html:select></td>

		<td><html:button property=""
			onclick="Deplacer(this.form.memberListLeft,this.form.memberListRight)">
			<bean:message key="groups.addMembers" />
		</html:button></td>

		<td ROWSPAN="2">
		<div><bean:message key="groups.members.accepted" /></div>
		<html:select property="memberListRight" styleClass="select" size="5"
			multiple="multiple">
			<c:forEach var="socialEntity" items="${acceptedMembers}">
				<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
			</c:forEach>
		</html:select></td>
	</tr>

	<tr>
		<td><html:button property=""
			onclick="Deplacer(this.form.memberListRight,this.form.memberListLeft)">
			<bean:message key="groups.removeMembers" />
		</html:button></td>
	</tr>


	<tr>
		<td ROWSPAN="2"><label for="groups"> <bean:message
			key="groups.groups" /> </label></td>

		<td ROWSPAN="2">
		<div><bean:message key="groups.groups.refused" /></div>
		<html:select property="groupListLeft" styleClass="select" size="5"
			multiple="multiple">
			<c:forEach var="socialGroup" items="${refusedGroups}">
				<html:option value="${socialGroup.id}">${socialGroup.name}</html:option>
			</c:forEach>
		</html:select></td>

		<td><html:button property=""
			onclick="Deplacer(this.form.groupListLeft,this.form.groupListRight)">
			<bean:message key="groups.addGroups" />
		</html:button></td>

		<td ROWSPAN="2">
		<div><bean:message key="groups.groups.accepted" /></div>
		<html:select property="groupListRight" styleClass="select" size="5"
			multiple="multiple">
			<c:forEach var="socialGroup" items="${acceptedGroups}">
				<html:option value="${socialGroup.id}">${socialGroup.name}</html:option>
			</c:forEach>
		</html:select></td>
	</tr>

	<tr>
		<td><html:button property=""
			onclick="Deplacer(this.form.groupListRight,this.form.groupListLeft)">
			<bean:message key="groups.removeGroups" />
		</html:button></td>
	</tr>

	<tr>
		<td colspan="2"><html:submit styleClass="button"
			onclick="Valider(this.form.memberListRight,this.form.groupListRight);">
			<bean:message key="groups.validate" />
		</html:submit></td>
	</tr>

</table>

<style>
select {
	min-width: 200px;
}
</style>

<script type="text/javascript">

function Deplacer(l1,l2) {
	
if (l1.options.selectedIndex>=0)
	for (var i = l1.options.length-1; i >=0 ; i--) {
		if (l1.options[ i ].selected){
			o=new Option(l1.options[i].text,l1.options[i].value);
			l2.options[l2.options.length]=o;
			l1.options[i]=null;
		}
	}
else{
	alert("Aucun membre sélectionnée");
}	
}
</script>
<script type="text/javascript">
function Valider(){
	var memberListLeft = document.getElementsByName('memberListRight').item(0);
	var groupListLeft = document.getElementsByName('groupListRight').item(0);
	for ( var i = 0; i < memberListLeft.options.length; i++) {
		memberListLeft.options[ i ].selected = "true";
	}
	
	for ( var i = 0; i < groupListLeft.options.length; i++) {
		groupListLeft.options[ i ].selected = "true";
	}
	return true;
}
</script>