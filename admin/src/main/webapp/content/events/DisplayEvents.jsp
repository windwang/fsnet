<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<bean:define id="searchEvent">
	<bean:message key="events.placeholder.search" />
</bean:define>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="events.title.search" />
	</legend>

	<html:form action="/Events" method="get">
		<table class="inLineTable fieldsetTableAdmin">
			<tr>
				<td><html:text property="searchString" styleId="searchTexte" />
					<ili:placeHolder id="searchTexte" value="${searchEvent}" /> <html:submit
						styleClass="button">
						<bean:message key="events.button.search" />
					</html:submit></td>
			</tr>
		</table>
	</html:form>
</fieldset>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="events.title.all" />
	</legend>

	<c:choose>
		<c:when test="${empty requestScope.eventsList}">
			<table class="inLineTable fieldsetTableAdmin">
				<tr>
					<td><bean:message key="events.noResults" /></td>
				</tr>
			</table>
		</c:when>

		<c:otherwise>
			<table id="eventsTable"
				class="tablesorter inLineTable fieldsetTableAdmin">
				<thead>
					<tr>
						<th><bean:message key="tableheader.eventname" /></th>
						<th><bean:message key="tableheader.willoccuron" /></th>
						<th><bean:message key="tableheader.by" /></th>
						<th><bean:message key="tableheader.firstname" /></th>
						<th><bean:message key="tableheader.name" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="event" items="${requestScope.eventsList}">
						<tr>
							<td><html:link action="/DisplayEvent">
		                    ${event.title}
		                    <html:param name="eventId" value="${event.id}" />
								</html:link></td>
							<td class="left"><bean:write name="event"
									property="startDate" format="dd/MM/yyyy" /></td>
							<td></td>
							<td><html:link action="/DisplayMember">
									<html:param name="idMember" value="${event.creator.id}" />
	                    	${event.creator.firstName} 
	              </html:link></td>
							<td><html:link action="/DisplayMember">
									<html:param name="idMember" value="${event.creator.id}" />
	                    	 ${event.creator.name}
	              </html:link></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>

<script type="text/javascript">
	$(document).ready(
			function pagination() {
				var nomTable = "eventsTable";
				var idColonneATrier = 0;
				var sensDeTri = "asc";
				var aoColumns = [ null, {
					"sType" : "date-euro"
				}, {
					"bSortable" : false
				}, null, null ];
				miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
						aoColumns, false);
			});
</script>
