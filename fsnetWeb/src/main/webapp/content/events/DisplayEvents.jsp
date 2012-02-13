<%-- 
    Document   : ListEvents
    Created on : 18 janv. 2010, 21:05:18
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<bean:define id="searchMessage">
	<bean:message key="events.search" />
</bean:define>

<h3>
	<bean:message key="events.8" />
</h3>
<table class="inLineTable">
	<tr>
		<td><html:form action="/Events" method="GET">
				<div id="SearchEvent">
					<html:text styleId="searchTexte" property="searchString" />
					<ili:placeHolder id="searchTexte" value="${searchMessage}" />
					<html:submit styleClass="button">
						<bean:message key="events.11" />
					</html:submit>
				</div>
			</html:form></td>
	</tr>
</table>

<h3>
	<bean:message key="events.9" />
	:
</h3>

<c:choose>

	<c:when test="${empty requestScope.eventsList}">
		<table class="inLineTable">
			<tr>
				<td><bean:message key="search.noResults" /></td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			$(document).ready(
					function pagination() {
						var nomTable = "eventsTable";
						var idColonneATrier = 2;
						var sensDeTri = "asc";
						var aoColumns = [ {
							"bSortable" : false
						}, null, null, {
							"sType" : "date-euro"
						}, {
							"bSortable" : false
						} ];
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, false);
					});
		</script>
		<table id="eventsTable" class="tablesorter inLineTable">
			<thead>
				<tr>
					<th></th>
					<th><bean:message key="tableheader.name" /></th>
					<th><bean:message key="tableheader.willoccur" /></th>
					<th><bean:message key="tableheader.by" /></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="event" items="${requestScope.eventsList}">
					<ili:interactionRow
						unreadInteractionsId="${requestScope.unreadInteractionsId}"
						currentInteractionId="${event.id}">
						<td><c:import url="/FavoriteFragment.do">
								<c:param name="interactionId" value="${event.id}" />
							</c:import></td>
						<td><html:link action="/DisplayEvent">
		                    ${event.title}
		                    <html:param name="eventId" value="${event.id}" />
							</html:link></td>
						<td class="left"><bean:write name="event"
								property="startDate" format="dd/MM/yyyy" /></td>
						<td><ili:getSocialEntityInfos socialEntity="${event.creator}" /></td>
						<td class="tableButton"><ili:substring beginIndex="0"
								endIndex="30">
								<ili:noxml>${event.content}</ili:noxml>
							</ili:substring></td>
					</ili:interactionRow>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>