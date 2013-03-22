<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend> ${event.title} </legend>

	<div class="interactionDisplay">
		<table class="inLineTable">
			<tr class="authorDate">
				<td><s:text name="events.createdBy" /> <ili:getSocialEntityInfos
						socialEntity="${event.creator}" /> , <s:text name="events.to" />
					<s:text name="startDate" /> <s:text name="events.from" /> <s:text
						name="endDate" /> <c:if
						test="${not empty event.address.address or not empty event.address.city}">
						<s:text name="events.in" />
                	${event.address.address} ${event.address.city}
                </c:if> <c:if test="${subscriber}">,&nbsp;&nbsp;"<s:text
							name="events.message.subsribe" />"</c:if></td>
			</tr>
			<tr>
				<td>${event.content}</td>
			</tr>
			<tr>
				<td class="alignRight"><ili:interactionFilter
						user="${ socialEntity }" right="${ rightRegisterEvent }">
						<c:if test="${not subscriber}">
							<s:url action="SubscribeEvent" var="varSubscribeEvent">
								<s:param name="eventId" value="%{#attr.event.id}" />
							</s:url>
							<a href="<s:property value="#varSubscribeEvent"/>"><s:text
									name="events.button.subscribe" /></a>
						</c:if>
						<c:if test="${subscriber}">
							<s:url action="UnsubscribeEvent" var="varUnsubscribeEvent">
								<s:param name="eventId" value="%{#attr.event.id}" />
							</s:url>
							<a href="<s:property value="#varUnsubscribeEvent"/>"><s:text
									name="events.button.unsubscribe" /></a>
						</c:if>
					</ili:interactionFilter> <c:if test="${userId eq event.creator.id}">

						<s:url action="DisplayUpdateEvent" var="varDisplayUpdateEvent">
							<s:param name="eventId" value="%{#attr.event.id}" />
						</s:url>
						<a href="<s:property value="#varDisplayUpdateEvent"/>"><s:text
								name="events.button.update" /></a>


					</c:if> <s:url action="ExportEventById" var="varExportEventById">
						<s:param name="eventId" value="%{#attr.event.id}" />
					</s:url> <a href="<s:property value="#varExportEventById"/>"><s:text
							name="events.export" /></a> <c:if
						test="${userId eq event.creator.id}">

						<s:form action="DeleteEvent" method="post"
							styleId="eventid${event.id}" styleClass="deleteEventForm">

							<s:hidden name="eventId" value="%{event.id}" />
							<span class="button btn btn-inverse"
								onclick="confirmDelete2('eventid${event.id}', '<s:text name="message.confirmation.delete" />');">
								<s:text name="events.button.delete" />
							</span>
						</s:form>
					</c:if></td>
			</tr>
		</table>
	</div>

	<c:set var="theInteraction" value="${event}" scope="request" />
	<jsp:include page="/content/interactions/InteractionInfo.jsp" />
	<c:if test="${not empty event.address.city}">
		<jsp:include page="/content/geolocalisation/GeolocalisationWidget.jsp" />
	</c:if>
</fieldset>

<div class="clear"></div>

<c:if test="${fn:length(subscribers) gt 0}">
	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="events.title.participate" />
		</legend>

		<table class="inLineTable ">
			<tr>
				<td><s:iterator id="subscriber" status="collectionStatus">
						<span class="tagSE"> <ili:getMiniature
								socialEntity="${subscriber}" /> <ili:getSocialEntityInfos
								socialEntity="${subscriber}" />
						</span>
					</s:iterator></td>
			</tr>
		</table>
	</fieldset>
</c:if>

