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


<h3>
    <bean:message key="events.8"/>
</h3>

<html:form action="/Events" method="GET">
    <div id="SearchEvent">
        <html:text property="searchString" />
        <html:submit styleClass="button" >
            <bean:message key="events.11"/>
        </html:submit>
    </div>
</html:form>

<h3>
    <bean:message key="events.9"/> :
</h3>

<c:choose>

	<c:when test="${empty requestScope.eventsListPaginator.resultList}">
	  <table  class="inLineTable"><tr><td>
        <bean:message key="search.noResults"/>
      </td></tr></table>
	</c:when>
	
	<c:otherwise>
		<table  class="inLineTable">
		    <c:forEach var="event" items="${requestScope.eventsListPaginator.resultList}">
		    	<ili:interactionRow unreadInteractionsId="${requestScope.unreadInteractionsId}" currentInteractionId="${event.id}">
		            <th>
		                <c:import url="/FavoriteFragment.do">
		                    <c:param name="interactionId" value="${event.id}"/>
		                </c:import>
		                <html:link action="/DisplayEvent">
		                    ${event.title}
		                    <html:param name="eventId" value="${event.id}"/>
		                </html:link>
		            </th>
		            <td class="left">
		                <bean:message key="events.10"/>
		                <bean:write name="event" property="startDate" format="dd/MM/yyyy"/>,
		                <bean:message key="events.16"/>
						<ili:getSocialEntityInfos socialEntity="${event.creator}"/>
		            </td>
		            <td  class="tableButton">
		                <ili:substring beginIndex="0" endIndex="30"><ili:noxml>${event.content}</ili:noxml></ili:substring>
		            </td>
		        </ili:interactionRow>
		    </c:forEach>
		</table>
		<c:set var="paginatorInstance" value="${requestScope.eventsListPaginator}" scope="request"/>
		<c:set var="paginatorAction" value="/Events" scope="request"/>
		<c:set var="paginatorTile" value="eventsLists" scope="request"/>
		<c:import url="/content/pagination/Pagination.jsp"/>
	</c:otherwise>
</c:choose>