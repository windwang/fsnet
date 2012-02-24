<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="groups.Modify" />
	</legend>
	
	<html:form action="/ModifyGroup" onsubmit="Valider()">

		<table id="CreateGroup" class="fieldsetTableAdmin">
			<tr>
				<td><label for="name"> <bean:message key="groups.name" />
						:
				</label></td>
				<td colspan="3"><html:text property="name" styleId="name"
						errorStyleClass="error" />
					<html:hidden property="id" styleId="id" /></td>
			</tr>

			<tr class="errorMessage">
				<td colspan="2"><html:errors property="name" /></td>
			</tr>

			<tr>
				<td><label for="description"> <bean:message
							key="groups.description" /> :
				</label></td>


				<td colspan="3"><c:set var="welcomeMain">
						<bean:message key="groups.description.message" /> :
				</c:set> <html:textarea property="description" styleId="description"
						errorStyleClass="error" cols="36" rows="6" /></td>
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
					<c:if test="${socialEntity.isEnabled}">
						<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
					</c:if>
				</c:forEach>
			</html:select></td>

			<td><html:button property=""
				onclick="DeplacerDroit(this.form.memberListLeft,this.form.memberListRight)">
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
				onclick="DeplacerDroit(this.form.memberListRight,this.form.memberListLeft)">
				<bean:message key="groups.removeMembers" />
			</html:button></td>
		</tr>

			<tr>
				<td><label for="socialEntityId"> <bean:message
							key="groups.owner" /> :
				</label></td>
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

			<td><html:button property=""
				onclick="DeplacerDroit(this.form.rigthListLeft,this.form.rigthListRight)">
				<bean:message key="groups.addGroups" />
			</html:button></td>

				<td ROWSPAN="2">
					<div>
						<bean:message key="groups.members.refused" />
					</div> <html:select property="memberListLeft" styleClass="select"
						size="5" multiple="multiple">
						<c:forEach var="socialEntity" items="${refusedMembers}">
							<c:if test="${socialEntity.isEnabled}">
								<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
							</c:if>
						</c:forEach>
					</html:select>
				</td>

				<td><html:button property=""
						onclick="Deplacer(this.form.memberListLeft,this.form.memberListRight)">
						<bean:message key="groups.addMembers" />
					</html:button></td>

		<tr>
			<td><html:button property=""
				onclick="DeplacerDroit(this.form.rigthListRight,this.form.rigthListLeft)">
				<bean:message key="groups.removeGroups" />
			</html:button></td>
		</tr>
		<tr>
			<td colspan="2"><html:submit styleClass="button"
				onclick="ValiderModGroup();">
				<bean:message key="groups.validate" />
			</html:submit></td>
		</tr>

	</table>

			<tr>
				<td ROWSPAN="2"><label for="groups"> <bean:message
							key="groups.right" />
				</label></td>

				<td ROWSPAN="2">
					<div>
						<bean:message key="groups.right.notGranted" />
					</div> <html:select property="rigthListLeft" styleClass="select" size="5"
						multiple="multiple">
						<c:forEach var="rigth" items="${refusedRigths}">
							<html:option value="${rigth}">
								<bean:message key="groups.rights.${rigth}" />
							</html:option>
						</c:forEach>
					</html:select>
				</td>

				<td><html:button property=""
						onclick="Deplacer(this.form.rigthListLeft,this.form.rigthListRight)">
						<bean:message key="groups.addGroups" />
					</html:button></td>

				<td ROWSPAN="2">
					<div>
						<bean:message key="groups.right.Granted" />
					</div> <html:select property="rigthListRight" styleClass="select"
						size="5" multiple="multiple">

						<c:forEach var="rigth" items="${acceptedRigths}">
							<html:option value="${rigth}">
								<bean:message key="groups.rights.${rigth}" />
							</html:option>
						</c:forEach>
					</html:select>
				</td>
			</tr>

			<tr>
				<td><html:button property=""
						onclick="Deplacer(this.form.rigthListRight,this.form.rigthListLeft)">
						<bean:message key="groups.removeGroups" />
					</html:button></td>
			</tr>

			<tr>
				<td colspan="2"><html:submit styleClass="button"
						onclick="Valider();">
						<bean:message key="groups.validate" />
					</html:submit></td>
			</tr>

		</table>


	</html:form>
</fieldset>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="groups.logo.change" />
	</legend>

	<html:form enctype="multipart/form-data" action="/ChangeLogo">
		<table id="changeLogo" class="fieldsetTableAdmin">
			<tr>
				<td><html:file property="Logo"></html:file></td>
				<td><html:submit styleClass="button">
						<bean:message key="groups.logo.button" />
					</html:submit></td>
			</tr>
			<logic:messagesPresent property="Logo">
				<tr class="errorMessage">
					<td colspan="2"><html:errors property="Logo" /></td>
				</tr>
			</logic:messagesPresent>
		</table>
	</html:form>
</fieldset>
