<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel=stylesheet type="text/css" href="css/osx.css" />
<script type="text/javascript" src="js/jquery.simplemodal.1.4.4.min.js"></script>
<script type="text/javascript" src="js/osx.js"></script>
<script type="text/javascript" src="js/definePassword.js"></script>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<s:text name="members.create" />
	</legend>
	<table id="CreateMember" class="inLineTable fieldsetTableAppli">
		<s:form action="/CreateMember">

			<tr>
				<td><label for="name"> <s:text name="members.name" />
				</label></td>
				<td><s:textfield property="name" styleId="name"
						errorStyleClass="error" />
					</td>
			</tr>

			<tr>
				<td><label for="firstName"> <s:text name="members.firstName" />
				</label></td>
				<td><s:textfield property="firstName" styleId="firstName"
						errorStyleClass="error" />
					</td>
			</tr>

			<tr>
				<td><label for="email"> <s:text name="members.email" />
				</label></td>
				<td><s:textfield property="email" styleId="email"
						errorStyleClass="error" />
					</td>
			</tr>

			<tr>
				<td><label for="parentId"> <s:text name="members.group" />
				</label></td>
			<td colspan="3">
			<s:select property="parentId"
						styleClass="select" styleId="parentId" list="%{socialGroup}" listkey="%{socialGroup.id}" listValue="%{socialGroup.name}">
<!-- 						<html:option value="" disabled="true" /> -->
<%-- 						<c:forEach var="socialGroup" items="${sessionScope.allGroups}"> --%>
<%-- 							<html:option value="${socialGroup.id}">${socialGroup.name}</html:option> --%>
<%-- 						</c:forEach> --%>
					</s:select>
					</td>
			</tr>

			<tr>
				<td><label for="typePassword1"> <s:text name="members.generatePassword" />
				</label></td>
				<td>
				<s:radio property="typePassword" styleId="typePassword1"
						value="generatePassword" onclick="definePasword()" list="" />
						
<!-- 						<html:radio property="typePassword" styleId="typePassword1"/>  -->
<!-- 						value="generatePassword" onclick="definePasword()" /> -->
						</td>
			</tr>

			<tr>
				<td><label for="typePassword2"> <s:text name="members.definePassword" />
				</label></td>
				<td>
				<s:radio property="typePassword" styleId="typePassword2"
						value="definePassword" onclick="definePasword()" list="" />
<%-- <html:radio property="typePassword" styleId="typePassword2" --%>
<%-- 						value="definePassword" onclick="definePasword()"  /> --%>
						</td>
			</tr>
			<tr>
				<td><label for="password"> <s:text name="members.password" />
				</label></td>
				<td><s:password property="password" styleId="password"
						errorStyleClass="error" />
					</td>
			</tr>
			<tr>
				<td><label for="passwordConfirmation"> <s:text name="members.passwordConfirmation" />
				</label></td>
				<td><s:password property="passwordConfirmation"
						styleId="passwordConfirmation" errorStyleClass="error" />
					</td>
			</tr>

			<%@ include file="SamePartForMember.jsp"%>
		</s:form>
	</table>
</fieldset>


