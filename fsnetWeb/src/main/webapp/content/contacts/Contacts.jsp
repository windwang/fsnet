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
	<h3>
		<bean:message key="contact.conts" />
	</h3>
	<table class="inLineTable">
		<tr>
			<td><bean:message key="contact.noContact" /></td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty requestScope.paginatorAsked}">
	<script type="text/javascript">
		$(document).ready(
				function pagination() {
					var idColonneATrier = 1;
					var sensDeTri = "asc";
					var aoColumns = [ {
						"bSortable" : false
					}, null, {
						"bSortable" : false
					} ];
					miseEnPageTable("tableAsked", idColonneATrier, sensDeTri,
							aoColumns, false);
				});
	</script>
	<h3>
		<bean:message key="contact.re" />
	</h3>
	<table id="tableAsked" class="tablesorter inLineTable">
		<thead>
			<tr>
				<th></th>
				<th><bean:message key="tableheader.member" /></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="contact" items="${requestScope.paginatorAsked}">
				<tr>
					<td class="miniatureContainer"><ili:getMiniature
							socialEntity="${contact}" /></td>
					<td><ili:getSocialEntityInfos socialEntity="${contact}" /></td>
					<td class="tableButton"><html:link
							action="/DisplayCreatePrivateMessage" styleClass="button">
							<bean:message key="showProfile.send" />
							<html:param name="receiver" value="${contact.email}" />
						</html:link> <html:link action="/AcceptContact" styleClass="button">
							<html:param name="entityAccepted" value="${contact.id}" />
							<bean:message key="contact.accept" />
						</html:link> <html:link action="/RefuseContact" styleClass="button">
							<html:param name="entityRefused" value="${contact.id}" />
							<bean:message key="contact.refuse" />
						</html:link></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

<c:if test="${not empty requestScope.paginatorContacts}">
	<script type="text/javascript">
		$(document).ready(
				function pagination() {
					var idColonneATrier = 1;
					var sensDeTri = "asc";
					var aoColumns = [ {
						"bSortable" : false
					}, null, {
						"bSortable" : false
					} ];
					miseEnPageTable("tableContacts", idColonneATrier,
							sensDeTri, aoColumns, false);
				});
	</script>
	<h3>
		<bean:message key="contact.listContact" />
		: ${fn:length(requestScope.paginatorContacts)}
		<bean:message key="contact.contacts" />
	</h3>

	<table id="tableContacts" class="tablesorter inLineTable">
		<thead>
			<tr>
				<th></th>
				<th><bean:message key="tableheader.member" /></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="contact" items="${requestScope.paginatorContacts}">
				<tr>
					<td class="miniatureContainer"><ili:getMiniature
							socialEntity="${contact}" /></td>
					<td><ili:getSocialEntityInfos socialEntity="${contact}" /></td>
					<td class="tableButton"><html:link
							action="/DisplayCreatePrivateMessage" styleClass="button">
							<bean:message key="showProfile.send" />
							<html:param name="receiver" value="${contact.email}" />
						</html:link> <html:link action="/DeleteContact" styleClass="button">
							<bean:message key="contact.delete" />
							<html:param name="entityDeleted" value="${contact.id}" />
						</html:link></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

<c:if test="${not empty requestScope.paginatorRequested}">
	<script type="text/javascript">
		$(document).ready(
				function pagination() {
					var idColonneATrier = 1;
					var sensDeTri = "asc";
					var aoColumns = [ {
						"bSortable" : false
					}, null, {
						"bSortable" : false
					} ];
					miseEnPageTable("tableRequested", idColonneATrier,
							sensDeTri, aoColumns, false);
				});
	</script>
	<h3>
		<bean:message key="contact.eff" />
	</h3>
	<table id="tableRequested" class="tablesorter inLineTable">
		<thead>
			<tr>
				<th></th>
				<th><bean:message key="tableheader.member" /></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="contact" items="${requestScope.paginatorRequested}">
				<tr>
					<td class="miniatureContainer"><ili:getMiniature
							socialEntity="${contact}" /></td>
					<td><ili:getSocialEntityInfos socialEntity="${contact}" /></td>
					<td class="tableButton"><html:link
							action="/DisplayCreatePrivateMessage" styleClass="button">
							<bean:message key="showProfile.send" />
							<html:param name="receiver" value="${contact.email}" />
						</html:link> <html:link action="/CancelAskContact" styleClass="button">
							<html:param name="id" value="${contact.id}" />
							<bean:message key="contacts.cancel" />
						</html:link></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>