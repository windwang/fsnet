<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="members.title.search" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td><s:form action="SearchMember" method="post">
					<div id="SearchMember">
						<s:textfield property="searchText" />
						<s:submit type="button" styleClass="button" />
					</div>
				</s:form></td>
		</tr>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="members.title.searchResult" />
	</legend>

	<c:if
		test="${empty membersContactsResult && empty membersRequestedResult 
	&& empty membersAskedResult && empty membersResult}">
		<table class="inLineTable tableStyle">
			<tr>
				<td><s:text name="members.noResult" /></td>
			</tr>
		</table>
	</c:if>


	<c:if test="${! empty membersContactsResult}">
		<h4>
			<s:text name="members.listContacts" />
		</h4>
		<table class="inLineTable tableStyle">
			<c:forEach var="member" items="${membersContactsResult}">
				<tr class="content">
					<td class="miniatureContainer"><ili:getMiniature
							socialEntity="${member}" /></td>
					<td><ili:getSocialEntityInfos socialEntity="${member}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<c:if test="${! empty membersRequestedResult}">
		<h4>
			<s:text name="members.listContactsAsked" />
		</h4>
		<table class="inLineTable tableStyle">
			<c:forEach var="member" items="${membersRequestedResult}">
				<tr class="content">
					<td class="miniatureContainer"><ili:getMiniature
							socialEntity="${member}" /></td>
					<td><ili:getSocialEntityInfos socialEntity="${member}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<c:if test="${! empty membersAskedResult}">
		<h4>
			<s:text name="members.listContactsReceived" />
		</h4>
		<table class="inLineTable tableStyle">
			<c:forEach var="member" items="${membersAskedResult}">
				<tr class="content">
					<td class="miniatureContainer"><ili:getMiniature
							socialEntity="${member}" /></td>
					<td><ili:getSocialEntityInfos socialEntity="${member}" /></td>
					<td class="tableButton"><s:a href="/AcceptContact"
							cssClass="button">
							<s:text name="members.button.accept" />
							<s:param name="entityAccepted" value="%{member.id}" />
						</s:a> <s:a href="/RefuseContact" styleClass="button">
							<s:text name="members.button.refuse" />
							<s:param name="entityRefused" value="%{member.id}" />
						</s:a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<c:if test="${! empty membersResult}">

		<h4>
			<s:text name="members.othersMembers" />
		</h4>
		<table class="inLineTable tableStyle">
			<c:forEach var="member" items="${membersResult}">
				<tr class="content">
					<td class="miniatureContainer"><ili:getMiniature
							socialEntity="${member}" /></td>
					<td><ili:getSocialEntityInfos socialEntity="${member}" /></td>
					<td class="tableButton"><s:a href="/ContactDemand"
							cssClass="" Class="button">
							<s:text name="members.button.add" />
							<s:param name="entitySelected" value="%{member.id}" />
						</s:a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</fieldset>

