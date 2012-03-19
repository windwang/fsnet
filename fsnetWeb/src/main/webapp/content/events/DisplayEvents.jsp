<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<%@ include file="SearchEvent.jsp" %>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="events.title.list" />
	</legend>

	<c:choose>

		<c:when test="${empty requestScope.eventsList}">
			<table class="inLineTable fieldsetTableAppli">
				<tr>
					<td><bean:message key="events.search.empty" /></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<div class="space"></div>
			<script type="text/javascript">
				$(document).ready(
						function pagination() {
							var nomTable = "eventsTable";
							var idColonneATrier = 2;
							var sensDeTri = "desc";
							var aoColumns = [ {
								"bSortable" : false
							}, null, {
								"sType" : "date"
							}, {
								"sType" : "date"
							}, {
								"bSortable" : false
							}, null, null ];
							miseEnPageTable(nomTable, idColonneATrier,
									sensDeTri, aoColumns, false);
						});
			</script>
			<table id="eventsTable"
				class="tablesorter inLineTable fieldsetTableAppli">
				<thead>
					<tr>
						<th></th>
						<th><bean:message key="tableheader.eventname" /></th>
						<th><bean:message key="tableheader.willoccur" /></th>
						<th><bean:message key="tableheader.expirdate" /></th>
						<th><bean:message key="tableheader.by" /></th>
						<th><bean:message key="tableheader.firstname" /></th>
						<th><bean:message key="tableheader.name" /></th>
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
								</html:link> <span style="color: gray"> : <ili:substring
										beginIndex="0" endIndex="30">
										<ili:noxml>${event.content}</ili:noxml>
									</ili:substring>
							</span></td>
							<td class="left"><bean:write name="event"
									property="startDate" format="dd/MM/yyyy HH:mm" /></td>
							<td class="left"><bean:write name="event" property="endDate"
									format="dd/MM/yyyy HH:mm" /></td>
							<td></td>
							<td><ili:getSocialEntityInfosFirstname
									socialEntity="${event.creator}" /></td>
							<td><ili:getSocialEntityInfosName
									socialEntity="${event.creator}" /></td>
						</ili:interactionRow>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>
