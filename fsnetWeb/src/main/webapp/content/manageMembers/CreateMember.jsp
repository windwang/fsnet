<%--
	author : SAID Mohamed
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel=stylesheet type="text/css" href="css/osx.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.simplemodal.js"></script>
<script type="text/javascript" src="js/osx.js"></script>

<script type="text/javascript" src="js/definePassword.js"></script>

<h3><bean:message key="members.create" /></h3>

<html:form action="/CreateMember">
	<table id="CreateMember">
		<tr>
			<td>
				<label for="name"> 
					<bean:message key="members.name" /> :
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
				<label for="firstName"> 
					<bean:message key="members.firstName" /> : 
				</label>
			</td>
			<td>
				<html:text property="firstName" styleId="firstName"	errorStyleClass="error" />
			</td>
		</tr>
		<tr class="errorMessage" >
			<td colspan="2">
				<html:errors property="firstName" />
			</td>
		</tr>
		<tr>
			<td>
				<label for="email"> 
					<bean:message key="members.email" /> :
				</label>
			</td>
			<td>
				<html:text property="email" styleId="email"	errorStyleClass="error" />
			</td>
		</tr>
		<tr class="errorMessage">
			<td colspan="2">
				<html:errors property="email" />
			</td>
		</tr>
		<tr>
			<td><label for="parentId"> <bean:message
				key="members.group" /> : </label></td>
			<td colspan="3"><html:select property="parentId"
				styleClass="select">

				<html:option value="" disabled="true">
					<bean:message key="groups.listGroup" />
				</html:option>
				<c:forEach var="socialGroup" items="${sessionScope.allGroups}">
					<html:option value="${socialGroup.id}">${socialGroup.name}</html:option>
				</c:forEach>
			</html:select></td>
		</tr>
		<tr class="errorMessage">
			<td colspan="2"><html:errors property="parentId" /></td>
		</tr>
		<tr>
			<td><label> <bean:message key="members.generatePassword" />
					:
			</label></td>
			<td><html:radio property="typePassword" styleId="typePassword"
					value="generatePassword" onclick="definePasword()" /></td>
		</tr>
		<tr>
			<td><label> <bean:message key="members.definePassword" />
					:
			</label></td>
			<td><html:radio property="typePassword" styleId="typePassword"
					value="definePassword" onclick="definePasword()" /></td>
		</tr>
		<tr>
			<td>
				<label for="password"> 
					<bean:message key="members.password" /> :
				</label>
			</td>
			<td>
				<html:password property="password" styleId="password" errorStyleClass="error" />
			</td>
		</tr>
		<tr class="errorMessage">
			<td colspan="2">
				<html:errors property="password" />
			</td>
		</tr>
		<tr>
			<td>
				<label for="passwordConfirmation"> 
					<bean:message key="members.passwordConfirmation" /> :
				</label>
			</td>
			<td>
				<html:password property="passwordConfirmation" styleId="passwordConfirmation" errorStyleClass="error" />
			</td>
		</tr>
		<tr class="errorMessage">
			<td colspan="2">
				<html:errors property="passwordConfirmation" />
			</td>
		</tr>
		<jsp:include page="/content/manageMembers/SamePartForMember.jsp"/>
	</table>
</html:form>


