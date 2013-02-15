<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<%@ include file="DisplayYourEvents.jsp" %>
<%@ include file="SearchEvent.jsp" %>

<c:if test="${!empty requestScope.eventsList}">
<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="events.leftMenu.exportEvents" />
	</legend>


	<table id="exportEvents" class="inLineTable tableStyle ">
		<tr>
			<td><html:link action="/ExportAllEvents">
					<img src="images/download.png" alt="<bean:message key="events.export" />" title="<bean:message key="events.export" />" /><bean:message key="events.exportAll" />
				</html:link>
			</td>
		</tr>
	</table>
</fieldset>
</c:if>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="events.title.list" />
	</legend>

	<c:choose>

		<c:when test="${empty requestScope.eventsList}">
			<table class="inLineTable tableStyle">
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
							}, null, null, {
								"bSortable" : false
							} ];
							miseEnPageTable(nomTable, idColonneATrier,
									sensDeTri, aoColumns, false, 10);
						});
			</script>
			<table id="eventsTable"
				class="tablesorter inLineTable tableStyle">
				<thead>
					<tr>
						<th></th>
						<th><bean:message key="tableheader.eventname" /></th>
						<th><bean:message key="tableheader.willoccur" /></th>
						<th><bean:message key="tableheader.expirdate" /></th>
						<th><bean:message key="tableheader.by" /></th>
						<th><bean:message key="tableheader.firstname" /></th>
						<th><bean:message key="tableheader.name" /></th>
						<th><bean:message key="tableheader.actions" /></th>
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
																<td>
								<html:link action="/ExportEventById">
									<html:param name="eventId" value="${event.id}" />
									<img src="images/download.png" alt="<bean:message key="events.export" />" title="<bean:message key="events.export" />" />
								</html:link>
								<c:if test="${userId eq event.creator.id}">
								<html:link action="/DisplayUpdateEvent">
									<html:param name="eventId" value="${event.id}" />
									<img src="images/edit.png" alt="<bean:message key="events.button.update" />" title="<bean:message key="events.button.update" />" />
								</html:link>
								<html:link action="/DeleteEvent">
									<html:param name="eventId" value="${event.id}" />
									<img onclick="return confirm('<bean:message key="message.confirmation.delete" />');" src="images/del.png" alt="<bean:message key="events.button.delete" />" title="<bean:message key="events.button.delete" />" />
								</html:link>
								</c:if>
							</td>
						</ili:interactionRow>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>
