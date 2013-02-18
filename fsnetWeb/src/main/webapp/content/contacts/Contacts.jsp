<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<c:iftest="${empty requestScope.paginatorContacts && empty requestScope.paginatorAsked && empty requestScope.paginatorRequested}">
	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="contact.title.allEmpty" />
		</legend>

		<table class="inLineTable tableStyle">
			<tr>
				<td><s:text name="contact.title.empty" /></td>
			</tr>
		</table>
	</fieldset>
</c:if>

<c:if test="${not empty requestScope.paginatorAsked}">
	<script type="text/javascript">
		$(document).ready(
				function pagination() {
					var idColonneATrier = 2;
					var sensDeTri = "asc";
					var aoColumns = [ {
						"bSortable" : false
					}, null, null, {
						"bSortable" : false
					} ];
					miseEnPageTable("tableAsked", idColonneATrier, sensDeTri,
							aoColumns, false, 5);
				});
	</script>

	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="contact.title.received" />
		</legend>

		<table id="tableAsked" class="tablesorter inLineTable tableStyle">
			<thead>
				<tr>
					<th><s:text name="tableheader.member" /></th>
					<th><s:text name="members.firstName" /></th>
					<th><s:text name="members.name" /></th>
					<th></th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="contact" items="${requestScope.paginatorAsked}">
					<tr>
						<td class="miniatureContainer"><ili:getMiniature
								socialEntity="${contact}" /></td>
						<td><ili:getSocialEntityInfosFirstname
								socialEntity="${contact}" /></td>
						<td><ili:getSocialEntityInfosName socialEntity="${contact}" /></td>

						<td class="tableButton"><c:if test="${contact.isEnabled}"><s:url
								action="/DisplayCreatePrivateMessage"
								styleClass="btn btn-inverse">
								<s:text name="showProfile.send" />
								<s:param name="receiver" value="%{contact.email}" />
							</s:url> <s:url action="/AcceptContact" styleClass="btn btn-inverse">
								<s:param name="entityAccepted" value="%{contact.id}" />
								<s:text name="contact.button.accept" /></c:if>
							</s:url> <s:url action="/RefuseContact" styleClass="btn btn-inverse">
								<s:param name="entityRefused" value="%{contact.id}" />
								<s:text name="contact.button.refuse" />
							</s:url></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</c:if>

<c:if test="${not empty requestScope.paginatorContacts}">
	<script type="text/javascript">
		$(document).ready(
				function pagination() {
					var idColonneATrier = 2;
					var sensDeTri = "asc";
					var aoColumns = [ {
						"bSortable" : false
					}, null, null, {
						"bSortable" : false
					} ];
					miseEnPageTable("tableContacts", idColonneATrier,
							sensDeTri, aoColumns, false, 5);
				});
	</script>

	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="contact.title.list" />
			: ${fn:length(requestScope.paginatorContacts)}
			<s:text name="contact.nbrContacts" />
		</legend>

		<table id="tableContacts" class="tablesorter inLineTable tableStyle">
			<thead>
				<tr>
					<th><s:text name="tableheader.member" /></th>
					<th><s:text name="members.firstName" /></th>
					<th><s:text name="members.name" /></th>
					<th></th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="contact" items="${requestScope.paginatorContacts}">
					<tr>
						<td class="miniatureContainer"><ili:getMiniature
								socialEntity="${contact}" /></td>

						<td><ili:getSocialEntityInfosFirstname
								socialEntity="${contact}" /></td>
						<td><ili:getSocialEntityInfosName socialEntity="${contact}" /></td>

						<td class="tableButton"><c:if test="${contact.isEnabled}"><s:url
								action="/DisplayCreatePrivateMessage"
								styleClass="btn btn-inverse">
								<s:text name="showProfile.send" />
								<s:param name="receiver" value="%{contact.email}" />
							</s:url></c:if> <s:url action="/DeleteContact" styleClass="btn btn-inverse">
								<s:text name="contact.button.delete" />
								<s:param name="entityDeleted" value="%{contact.id}" />
							</s:url></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</c:if>

<c:if test="${not empty requestScope.paginatorRequested}">
	<script type="text/javascript">
		$(document).ready(
				function pagination() {
					var idColonneATrier = 2;
					var sensDeTri = "asc";
					var aoColumns = [ {
						"bSortable" : false
					}, null, null, {
						"bSortable" : false
					} ];
					miseEnPageTable("tableRequested", idColonneATrier,
							sensDeTri, aoColumns, false, 5);
				});
	</script>

	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="contact.title.do" />
		</legend>

		<table id="tableRequested" class="tablesorter inLineTable tableStyle">
			<thead>
				<tr>
					<th><s:text name="tableheader.member" /></th>
					<th><s:text name="members.firstName" /></th>
					<th><s:text name="members.name" /></th>
					<th></th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="contact" items="${requestScope.paginatorRequested}">
					<tr>
						<td class="miniatureContainer"><ili:getMiniature
								socialEntity="${contact}" /></td>
						<td><ili:getSocialEntityInfosFirstname
								socialEntity="${contact}" /></td>
						<td><ili:getSocialEntityInfosName socialEntity="${contact}" /></td>

						<td class="tableButton"><c:if test="${contact.isEnabled}"><s:url
								action="/DisplayCreatePrivateMessage"
								styleClass="btn btn-inverse">
								<s:text name="showProfile.send" />
								<s:param name="receiver" value="%{contact.email}" />
							</s:url></c:if> <s:url action="/CancelAskContact" styleClass="btn btn-inverse">
								<s:param name="id" value="%{contact.id}" />
								<s:text name="contacts.cancel" />
							</s:url></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</c:if>