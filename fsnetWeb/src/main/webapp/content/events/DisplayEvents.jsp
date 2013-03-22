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
				<td><a href='<s:url action="ExportAllEvents" /> '> <img
						src="images/download.png" alt="<s:text name="events.export" />"
						title="<s:text name="events.export" />" /> <s:text
							name="events.exportAll" />
				</a></td>
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
							<td></td>
							<td><s:url action="DisplayEvent" var="varDisplayEvent">
									<s:param name="eventId" value="%{#attr.event.id}" />
								</s:url> <a href="<s:property value="#varDisplayEvent"/>">${event.title}</a>
								<span style="color: gray"> : <ili:substring
										beginIndex="0" endIndex="30">
										<ili:noxml>${event.content}</ili:noxml>
									</ili:substring>
							</span></td>
							<td class="left"><s:date name="%{#attr.event.startDate}"
									format="dd/MM/yyyy" /></td>
							<td class="left"><s:date name="%{#attr.event.endDate}"
									format="dd/MM/yyyy" /></td>
							<td></td>
							<td><ili:getSocialEntityInfosFirstname
									socialEntity="${event.creator}" /></td>
							<td><ili:getSocialEntityInfosName
									socialEntity="${event.creator}" /></td>
							<td>
							
								<s:url action="ExportEventById" var="varExportEventById">
									<s:param name="eventId" value="%{#attr.event.id}" />
								</s:url> 
								
								<a href='<s:property value="varExportEventById"/>'> <img
									src="images/download.png" alt="<s:text name="events.export" />"
									title="<s:text name="events.export" />" />
							</a> <c:if test="${userId eq event.creator.id}">


									<s:url action="DisplayUpdateEvent" var="varDisplayUpdateEvent">
										<s:param name="eventId" value="%{#attr.event.id}" />
									</s:url>
									<a href='<s:property value="varDisplayUpdateEvent"/> '> <img
										src="images/edit.png"
										alt="<s:text name="events.button.update" />"
										title="<s:text name="events.button.update" />" />
									</a>

									<s:url action="DeleteEvent" var="varDeleteEvent">
										<s:param name="eventId" value="%{#attr.event.id}" />
									</s:url>
									<a href="<s:property value="varDeleteEvent"/>"> <img
										onclick="return confirm('<s:text name="message.confirmation.delete" />');"
										src="images/del.png"
										alt="<s:text name="events.button.delete" />"
										title="<s:text name="events.button.delete" />" />
									</a>

								</c:if></td>
						</ili:interactionRow>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>
