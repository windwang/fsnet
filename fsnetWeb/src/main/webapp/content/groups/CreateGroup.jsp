<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<s:debug/>
<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="groups.create" />
	</legend>

	<s:form action="CreateGroup" onsubmit="Valider()">

		<table id="CreateGroup" class="inLineTable tableStyle">
			<tr>
				<td><label for="name"> <s:text name="groups.name" />
				</label></td>
				<td colspan="3"><s:textfield property="name" styleId="name"
						cssErrorClass="error" /> <s:hidden name="id" styleId="id" /></td>
			</tr>

			<tr>
				<td><label for="description"> <s:text
							name="groups.description" />
				</label></td>
				<td colspan="3"><c:set var="welcomeMain">
						<s:text name="groups.description.message" />
					</c:set> <s:textarea property="description" styleId="description"
						cssErrorClass="error" cols="36" rows="6">
					</s:textarea></td>
			</tr>
			<tr class="errorMessage">
				<td colspan="2"></td>
			</tr>

			<tr>
				<td><label for="parentId"> <s:text name="groups.parent" />
				</label></td>
				<td colspan="3">
					<!--   listValue="%{allGroups.name}"-->
					 <s:select name="parentId" cssClass="select" styleId="parentId" list="%{allGroups}" listValue="%{name}" listKey="%{id}"/>
				</td>
			</tr>

			<tr>
				<td><label for="socialEntityId"> <s:text
							name="groups.owner" />
				</label></td>
				<td colspan="3">
<!-- 					listKey="%{allMembers.id}" -->
<!-- 						listValue="%{allMembers.name} %{allMembers.firstName}" -->
				<s:select name="socialEntityId"
						cssClass="select" styleId="socialEntityId" list="%{allMembers}" listValue="%{name +' '+ firstName}" listKey="%{id}"/>
						</td>
			</tr>

			<tr>
				<td rowspan="2"><label> <s:text name="groups.members" />
				</label></td>

				<td rowspan="2">
					<div>
						<s:text name="groups.members.refused" />
					</div>
					 <s:select name="memberListLeft" cssClass="select" size="5"
						multiple="true" list="%{refusedMembers}" listValue="%{name +' '+ firstName}" listKey="%{id}"/>
				</td>

				<td><input type="button" class="btn btn-inverse" value="<s:text name="groups.addMembers" />"
						onclick="DeplacerDroit(this.form.memberListLeft,this.form.memberListRight)"/></td>

				<td rowspan="2">
					<div>
						<s:text name="groups.members.accepted" />
					</div> <s:select name="memberListRight" cssClass="select" size="5"
						multiple="true" list="%{acceptedMembers}" listValue="%{name +' '+ firstName}" listKey="%{id}" />
				</td>
			</tr>
			<tr>
				<td><input type="button" class="btn btn-inverse" value="<s:text name="groups.removeMembers" />"
						onclick="DeplacerDroit(this.form.memberListRight,this.form.memberListLeft);"/></td>
			</tr>

			<tr>
				<td rowspan="2"><label> <s:text name="groups.right" />
				</label></td>

				<td rowspan="2">
					<div>
						<s:text name="groups.right.notGranted" />
					</div>
					<s:select name="rigthListLeft" cssClass="select" size="5"
						multiple="true" list="%{refusedRigths}" />
				</td>

				<td><input type="button"  value="<s:text name="groups.addGroups" />"
						class="btn btn-inverse"
						onclick="DeplacerDroit(this.form.rigthListLeft,this.form.rigthListRight);"/>

				<td rowspan="2">
					<div>
						<s:text name="groups.right.Granted" />
					</div> <s:select name="rigthListRight" cssClass="select" size="5"
						multiple="true" list="%{acceptedRigths}"/>

				</td>
			</tr>
			<tr>
				<td><input type="button" class="btn btn-inverse" value="<s:text name="groups.removeGroups" />"
						onclick="DeplacerDroit(this.form.rigthListRight,this.form.rigthListLeft)"/>
						
			</tr>

			<tr>
				<td colspan="4" class="tableButton">
				<s:submit type="button" cssClass="btn btn-inverse" onclick="CreateGroup();">
						<s:text name="groups.validate" />
					</s:submit></td>
			</tr>
		</table>
	</s:form>
</fieldset>


