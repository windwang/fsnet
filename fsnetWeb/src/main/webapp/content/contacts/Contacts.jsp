<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<c:if
	test="${empty requestScope.paginatorContacts && empty requestScope.paginatorAsked && empty requestScope.paginatorRequested}">
	<fieldset class="fieldsetCadre">
		<legend>
			<bean:message key="contact.title.allEmpty" />
		</legend>

		<table class="inLineTable tableStyle">
			<tr>
				<td><bean:message key="contact.title.empty" /></td>
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
			<bean:message key="contact.title.received" />
		</legend>

		<table id="tableAsked" class="tablesorter inLineTable tableStyle">
			<thead>
				<tr>
					<th><bean:message key="tableheader.member" /></th>
					<th><bean:message key="members.firstName" /></th>
					<th><bean:message key="members.name" /></th>
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
						<td class="tableButton"><c:if test="${contact.isEnabled}">
								<html:link action="/DisplayCreatePrivateMessage"
									styleClass="btn btn-inverse">
									<bean:message key="showProfile.send" />
									<html:param name="receiver" value="${contact.email}" />
								</html:link>
								<html:link action="/AcceptContact" styleClass="btn btn-inverse">
									<html:param name="entityAccepted" value="${contact.id}" />
									<bean:message key="contact.button.accept" />
								</html:link>
							</c:if> <html:link action="/RefuseContact" styleClass="btn btn-inverse">
								<html:param name="entityRefused" value="${contact.id}" />
								<bean:message key="contact.button.refuse" />
							</html:link></td>
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
			<bean:message key="contact.title.list" />
			: ${fn:length(requestScope.paginatorContacts)}
			<bean:message key="contact.nbrContacts" />
		</legend>

		<table id="tableContacts" class="tablesorter inLineTable tableStyle">
			<thead>
				<tr>
					<th><bean:message key="tableheader.member" /></th>
					<th><bean:message key="members.firstName" /></th>
					<th><bean:message key="members.name" /></th>
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
						<td class="tableButton"><c:if test="${contact.isEnabled}">
								<html:link action="/DisplayCreatePrivateMessage"
									styleClass="btn btn-inverse">
									<bean:message key="showProfile.send" />
									<html:param name="receiver" value="${contact.email}" />
								</html:link>
							</c:if> <html:link action="/DeleteContact" styleClass="btn btn-inverse">
								<bean:message key="contact.button.delete" />
								<html:param name="entityDeleted" value="${contact.id}" />
							</html:link></td>
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
			<bean:message key="contact.title.do" />
		</legend>

		<table id="tableRequested" class="tablesorter inLineTable tableStyle">
			<thead>
				<tr>
					<th><bean:message key="tableheader.member" /></th>
					<th><bean:message key="members.firstName" /></th>
					<th><bean:message key="members.name" /></th>
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
						<td class="tableButton"><c:if test="${contact.isEnabled}">
								<html:link action="/DisplayCreatePrivateMessage"
									styleClass="btn btn-inverse">
									<bean:message key="showProfile.send" />
									<html:param name="receiver" value="${contact.email}" />
								</html:link>
							</c:if> <html:link action="/CancelAskContact"
								styleClass="btn btn-inverse">
								<html:param name="id" value="${contact.id}" />
								<bean:message key="contacts.cancel" />
							</html:link></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</c:if>