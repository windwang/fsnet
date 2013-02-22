<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<%@ include file="DisplayYourEvents.jsp"%>
<%@ include file="SearchEvent.jsp"%>

<c:if test="${!empty requestScope.eventsList}">
	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="events.leftMenu.exportEvents" />
		</legend>


		<table id="exportEvents" class="inLineTable tableStyle ">
			<tr>
				<td><s:a href="/ExportAllEvents">
						<img src="images/download.png"
							alt="<s:text name="events.export" />"
							title="<s:text name="events.export" />" />
						<s:text name="events.exportAll" />
					</s:a></td>
			</tr>
		</table>
	</fieldset>
</c:if>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="events.title.list" />
	</legend>

	<c:choose>

		<c:when test="${empty requestScope.eventsList}">
			<table class="inLineTable tableStyle">
				<tr>
					<td><s:text name="events.search.empty" /></td>
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
							}, null, null, {
								"bSortable" : false
							} ];
							miseEnPageTable(nomTable, idColonneATrier,
									sensDeTri, aoColumns, false, 10);
						});
			</script>
			<table id="eventsTable" class="tablesorter inLineTable tableStyle">
				<thead>
					<tr>
						<th></th>
						<th><s:text name="tableheader.eventname" /></th>
						<th><s:text name="tableheader.willoccur" /></th>
						<th><s:text name="tableheader.expirdate" /></th>
						<th><s:text name="tableheader.by" /></th>
						<th><s:text name="tableheader.firstname" /></th>
						<th><s:text name="tableheader.name" /></th>
						<th><s:text name="tableheader.actions" /></th>
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
							<td><s:a href="/DisplayEvent">
		                    ${event.title}
		                    <s:param name="eventId" value="%{event.id}" />
								</s:a> <span style="color: gray"> : <ili:substring
										beginIndex="0" endIndex="30">
										<ili:noxml>${event.content}</ili:noxml>
									</ili:substring>
							</span></td>
							<td class="left"><s:property name="event"
									property="startDate" format="dd/MM/yyyy HH:mm" /></td>
							<td class="left"><s:property name="event" property="endDate"
									format="dd/MM/yyyy HH:mm" /></td>
							<td></td>
							<td><ili:getSocialEntityInfosFirstname
									socialEntity="${event.creator}" /></td>
							<td><ili:getSocialEntityInfosName
									socialEntity="${event.creator}" /></td>
							<td><s:a href="/ExportEventById">
									<s:param name="eventId" value="%{event.id}" />
									<img src="images/download.png"
										alt="<s:text name="events.export" />"
										title="<s:text name="events.export" />" />
								</s:a> <c:if test="${userId eq event.creator.id}">
									<s:a href="/DisplayUpdateEvent">
										<s:param name="eventId" value="%{event.id}" />
										<img src="images/edit.png"
											alt="<s:text name="events.button.update" />"
											title="<s:text name="events.button.update" />" />
									</s:a>
									<s:a href="/DeleteEvent">
										<s:param name="eventId" value="%{event.id}" />
										<img
											onclick="return confirm('<s:text name="message.confirmation.delete" />');"
											src="images/del.png"
											alt="<s:text name="events.button.delete" />"
											title="<s:text name="events.button.delete" />" />
									</s:a>
								</c:if></td>
						</ili:interactionRow>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>
