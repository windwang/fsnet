<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="groups.Modify" />
	</legend>

	<s:form action="/ModifyGroup" onsubmit="Valider()" theme="simple">

		<table id="ModifyGroup" class="tableStyle">
			<tr>
				<td><label for="name"> <s:text name="groups.name" />
				</label></td>
				<td colspan="3"><s:textfield name="name" styleId="name"
						cssErrorClass="error" /> <s:hidden name="id" styleId="id" /></td>
			</tr>

			<tr>
				<td><label for="description"> <s:text
							name="groups.description" />
				</label></td>


				<td colspan="3"><c:set var="welcomeMain">
						<s:text name="groups.description.message" />
					</c:set> <s:textarea name="description" styleId="description"
						errorcssClass="error" cols="36" rows="6" /></td>
			</tr>
			<tr>
				<td><label for=colorpickerField1> <s:text
							name="groups.color" />
				</label></td>
				<td colspan="3">#<s:textfield name="color" value="%{color}"
						cssClass="colorpickerField1" />

				</td>
			</tr>
			<tr>
				<td><label for="socialEntityId"> <s:text
							name="groups.owner" />
				</label></td>
				<td colspan="3"><s:select name="socialEntityId"
						cssClass="select" styleId="socialEntityId" list="%{allMembers}"
						listKey="%{allMembers}" listValue="%{name +' ' + firstName}">
					</s:select></td>
			</tr>

			<tr>
				<td rowspan="2"><label for="members"> <s:text
							name="groups.members" />
				</label></td>

				<td rowspan="2">
					<div>
						<s:text name="groups.members.refused" />
					</div> <s:select name="memberListLeft" cssClass="select" size="5"
						multiple="true" list="%{refusedMembers}" listKey="%{id}"
						listValue="%{name +' '+ firstName}" />
				</td>

				<td><input type="button"
					value="<s:text name="groups.addMembers" />" class="btn btn-inverse"
					onclick="DeplacerDroit(this.form.memberListLeft,this.form.memberListRight)" /></td>

				<td rowspan="2">
					<div>
						<s:text name="groups.members.accepted" />
					</div> <s:select name="memberListRight" cssClass="select" size="5"
						multiple="true" list="%{acceptedMembers}"
						listValue="%{name +' '+ firstName}" listKey="%{id}" />
				</td>
			</tr>

			<tr>
				<td><input type="button"
					value="<s:text name="groups.removeMembers" />"
					class="btn btn-inverse"
					onclick="DeplacerDroit(this.form.memberListRight,this.form.memberListLeft)" />
				</td>
			</tr>

			<tr>
				<td rowspan="2"><label for="groups"> <s:text
							name="groups.right" />
				</label></td>

				<td rowspan="2">
					<div>
						<s:text name="groups.right.notGranted" />
					</div> <s:select name="rigthListLeft" cssClass="select" size="5"
						multiple="true" list="%{refusedRigths}" listKey="%{rigth}"
						listValue="%{rigth}">
					</s:select>
				</td>

				<td><input type="button"
					value="<s:text name="groups.addGroups" />" class="btn btn-inverse"
					onclick="DeplacerDroit(this.form.rigthListLeft,this.form.rigthListRight)" />
				</td>

				<td rowspan="2">
					<div>
						<s:text name="groups.right.Granted" />
					</div> <s:select name="rigthListRight" cssClass="select" size="5"
						multiple="true" list="%{acceptedRigths}" listKey="%{rigth}"
						listValue="%{rigth}">
					</s:select>
				</td>
			</tr>
			<tr>
				<td>
				<input type="button"
					value="<s:text name="groups.removeGroups" />" class="btn btn-inverse"
					onclick="DeplacerDroit(this.form.rigthListRight,this.form.rigthListLeft)" /></td>
			</tr>

			<tr>
				<td colspan="4" class="tableButton"><s:submit type="button"
						cssClass="btn btn-inverse" onclick="ModifyGroup();">
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
				<td><s:file name="Logo"></s:file></td>

				<td class="tableButton"><s:submit type="button" cssClass="btn btn-inverse">
						<s:text name="groups.logo.button" />
					</s:submit></td>
			</tr>
		</table>
	</s:form>
</fieldset>