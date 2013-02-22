<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="groups.Modify" />
	</legend>

	<s:form action="/ModifyGroup" onsubmit="Valider()">

		<table id="ModifyGroup" class="tableStyle">
			<tr>
				<td><label for="name"> <s:text name="groups.name" />
				</label></td>
				<td colspan="3"><s:textfield property="name" styleId="name"
						errorStyleClass="error" /> <s:hidden property="id"
						styleId="id" />
					</td>
			</tr>

			<tr>
				<td><label for="description"> <s:text
							name="groups.description" />
				</label></td>


				<td colspan="3"><c:set var="welcomeMain">
						<s:text name="groups.description.message" />
					</c:set> <s:textarea property="description" styleId="description"
						errorStyleClass="error" cols="36" rows="6" />
					</td>
			</tr>
			<tr>
				<td><label for=colorpickerField1> <s:text
							name="groups.color" />
				</label></td>
				<td colspan="3">#<s:textfield property="color" value="%{color}"
						styleId="colorpickerField1" />

				</td>
			</tr>
			<tr>
				<td><label for="socialEntityId"> <s:text
							name="groups.owner" />
				</label></td>
				<td colspan="3"><s:select property="socialEntityId"
						styleClass="select" value="%{ masterGroup.id }"
						styleId="socialEntityId" list="%{allMembers}" listKey="%{allMembers.id}" listValue="%{socialEntity.name} %{socialEntity.firstName}">
					</s:select>
					</td>
			</tr>

			<tr>
				<td rowspan="2"><label for="members"> <s:text
							name="groups.members" />
				</label></td>

				<td rowspan="2">
					<div>
						<s:text name="groups.members.refused" />
					</div> <s:select property="memberListLeft" styleClass="select"
						size="5" multiple="multiple" list="%{refusedMembers}" listKey="%{refusedMembers.id}" listValue="%{refusedMembers.name} %{refusedMembers.firstName}">
					</s:select>
				</td>

				<td><s:submit type="button"property="" styleClass="btn btn-inverse"
						onclick="DeplacerDroit(this.form.memberListLeft,this.form.memberListRight)">
						<s:text name="groups.addMembers" />
					</s:submit></td>

				<td rowspan="2">
					<div>
						<s:text name="groups.members.accepted" />
					</div> <s:select property="memberListRight" styleClass="select"
						size="5" multiple="multiple" list="%{acceptedMembers}" listKey="%{acceptedMembers.id}" listValue="%{acceptedMembers.name} %{acceptedMembers.firstName}">
					</s:select>
				</td>
			</tr>

			<tr>
				<td><s:submit type="button" property="" styleClass="btn btn-inverse"
						onclick="DeplacerDroit(this.form.memberListRight,this.form.memberListLeft)">
						<s:text name="groups.removeMembers" />
					</s:submit></td>
			</tr>

			<tr>
				<td rowspan="2"><label for="groups"> <s:text
							name="groups.right" />
				</label></td>

				<td rowspan="2">
					<div>
						<s:text name="groups.right.notGranted" />
					</div> <s:select property="rigthListLeft" styleClass="select" size="5"
						multiple="multiple" list="%{refusedRigths}" listKey="%{rigth}" listValue="groups.rights.%{rigth}">
					</s:select>
				</td>

				<td><s:submit type="button" property="" styleClass="btn btn-inverse"
						onclick="DeplacerDroit(this.form.rigthListLeft,this.form.rigthListRight)">
						<s:text name="groups.addGroups" />
					</s:submit></td>

				<td rowspan="2">
					<div>
						<s:text name="groups.right.Granted" />
					</div> <s:select property="rigthListRight" styleClass="select"
						size="5" multiple="multiple" list="%{acceptedRigths}" listKey="%{rigth}" listValue="groups.rights.%{rigth}">
					</s:select>
				</td>
			</tr>
			<tr>
				<td><s:submit type="button"property="" styleClass="btn btn-inverse"
						onclick="DeplacerDroit(this.form.rigthListRight,this.form.rigthListLeft)">
						<s:text name="groups.removeGroups" />
					</s:submit></td>
			</tr>

			<tr>
				<td colspan="4" class="tableButton"><s:submit
						styleClass="btn btn-inverse" onclick="ModifyGroup();">
						<s:text name="groups.validate" />
					</s:submit></td>
			</tr>
		</table>
	</s:form>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="groups.logo.change" />
	</legend>

	<s:form enctype="multipart/form-data" action="/ChangeLogo">
		<table id="changeLogo" class="tableStyle">
			<tr>
				<td><s:file property="Logo"></s:file>
					</td>

				<td class="tableButton"><s:submit styleClass="btn btn-inverse">
						<s:text name="groups.logo.button" />
					</s:submit></td>
			</tr>
		</table>
	</s:form>
</fieldset>