<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="groups.create" />
	</legend>

	<html:form action="/CreateGroup" onsubmit="Valider()">
		<table id="CreateGroup"
			class="inLineTableDashBoardFieldset fieldsetTable">
			<tr>
				<td><label for="name"> <bean:message key="groups.name" />
				</label></td>
				<td colspan="3"><html:text property="name" styleId="name"
						errorStyleClass="error" /> <html:hidden property="id"
						styleId="id" />
					<div class="errorMessage">
						<html:errors property="name" />
					</div></td>
			</tr>

			<tr>
				<td><label for="description"> <bean:message
							key="groups.description" />
				</label></td>
				<td colspan="3"><c:set var="welcomeMain">
						<bean:message key="groups.description.message" />
					</c:set> <html:textarea property="description" styleId="description"
						errorStyleClass="error" cols="36" rows="6">
					</html:textarea>
					<div class="errorMessage">
						<html:errors property="description" />
					</div></td>
			</tr>
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="description" /></td>
			</tr>

			<tr>
				<td><label> <bean:message key="groups.parent" />
				</label></td>
				<td colspan="3"><html:select property="parentId"
						styleClass="select">

						<html:option value="" disabled="true">
							<bean:message key="groups.listGroup" />
						</html:option>
						<c:forEach var="socialGroup" items="${allGroups}">
							<html:option value="${socialGroup.id}">${socialGroup.name}</html:option>
						</c:forEach>
					</html:select>
					<div class="errorMessage">
						<html:errors property="parentId" />
					</div></td>
			</tr>

			<tr>
				<td><label> <bean:message key="groups.owner" />
				</label></td>
				<td colspan="3"><html:select property="socialEntityId"
						styleClass="select" value="${ masterGroup.id }">
						<html:option value="" disabled="true">
							<bean:message key="groups.listMember" />
						</html:option>
						<c:forEach var="socialEntity" items="${allMembers}">
							<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
						</c:forEach>
					</html:select>
					<div class="errorMessage">
						<html:errors property="socialEntityId" />
					</div></td>
			</tr>

			<tr>
				<td rowspan="2"><label> <bean:message
							key="groups.members" />
				</label></td>

				<td rowspan="2">
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
						onclick="DeplacerDroit(this.form.memberListLeft,this.form.memberListRight)">
						<bean:message key="groups.addMembers" />
					</html:button></td>

				<td rowspan="2">
					<div>
						<bean:message key="groups.members.accepted" />
					</div> <html:select property="memberListRight" styleClass="select"
						size="5" multiple="multiple">
						<c:forEach var="socialEntity" items="${acceptedMembers}">
							<html:option value="${socialEntity.id}">${socialEntity.name} ${socialEntity.firstName}</html:option>
						</c:forEach>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><html:button property=""
						onclick="DeplacerDroit(this.form.memberListRight,this.form.memberListLeft)">
						<bean:message key="groups.removeMembers" />
					</html:button></td>
			</tr>

			<tr>
				<td rowspan="2"><label> <bean:message
							key="groups.right" />
				</label></td>

				<td rowspan="2">
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
						onclick="DeplacerDroit(this.form.rigthListLeft,this.form.rigthListRight)">
						<bean:message key="groups.addGroups" />
					</html:button></td>

				<td rowspan="2">
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
						onclick="DeplacerDroit(this.form.rigthListRight,this.form.rigthListLeft)">
						<bean:message key="groups.removeGroups" />
					</html:button></td>
			</tr>
			
			<tr>
				<td colspan="4" align="right"><html:submit styleClass="button"
						onclick="CreateGroup();">
						<bean:message key="groups.validate" />
					</html:submit></td>
			</tr>
		</table>
	</html:form>
</fieldset>


