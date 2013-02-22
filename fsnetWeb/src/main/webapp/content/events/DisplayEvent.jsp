<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
		<c:import url="/FavoriteFragment.do">
			<c:param name="interactionId" value="${event.id}" />
		</c:import>
		${event.title}
	</legend>

	<div class="interactionDisplay">
		<table class="inLineTable">
			<tr class="authorDate">
				<td><s:text name="events.createdBy" /> <ili:getSocialEntityInfos
						socialEntity="${event.creator}" /> , <s:text name="events.to" />
					<s:property name="event" property="startDate"
						format="dd/MM/yyyy HH'h'mm" /> <s:text name="events.from" /> <s:property
						name="event" property="endDate" format="dd/MM/yyyy HH'h'mm" /> <c:if
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
							<s:a href="/SubscribeEvent" styleClass="button btn btn-inverse">
								<s:param name="eventId" value="%{event.id}" />
								<s:text name="events.button.subscribe" />
							</s:a>
						</c:if>
						<c:if test="${subscriber}">
							<s:a href="/UnsubscribeEvent" styleClass="button btn btn-inverse">
								<s:param name="eventId" value="%{event.id}" />
								<s:text name="events.button.unsubscribe" />
							</s:a>
						</c:if>
					</ili:interactionFilter> <c:if test="${userId eq event.creator.id}">
						<s:a href="/DisplayUpdateEvent"
							styleClass="button btn btn-inverse">
							<s:param name="eventId" value="%{event.id}" />
							<s:text name="events.button.update" />
						</s:a>
					</c:if> <s:a href="/ExportEventById" styleClass="button btn btn-inverse">
						<s:param name="eventId" value="%{event.id}" />
						<s:text name="events.export" />
					</s:a> <c:if test="${userId eq event.creator.id}">
						<s:form action="/DeleteEvent" method="post"
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
				<td><s:iterator id="subscriber"	 status="collectionStatus">
						<span class="tagSE"> <ili:getMiniature
								socialEntity="${subscriber}" /> <ili:getSocialEntityInfos
								socialEntity="${subscriber}" />
						</span>
					</s:iterator></td>
			</tr>
		</table>
	</fieldset>
</c:if>

