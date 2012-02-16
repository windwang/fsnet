<%--
	author : Bouragba Mohamed
	source : Matthieu Proucelle
 --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<fieldset class="fieldsetAdmin">
  <legend class="legendAdmin"><bean:message key="events.8"/></legend>

  <html:form action="/Events" method="get">
    <div>
      <table class="fieldsetTableAdmin"><tr><td>
        <html:text property="searchString" />
        <html:submit styleClass="button" >
            <bean:message key="events.11"/>
        </html:submit>
      </td></tr></table>
    </div>
</html:form>
</fieldset>

<fieldset class="fieldsetAdmin">
  <legend class="legendAdmin"><bean:message key="events.9"/></legend>

  <c:choose>

	<c:when test="${empty requestScope.eventsListPaginator.resultList}">
	    <table class="fieldsetTableAdmin"><tr><td>
	      <bean:message key="search.noResults"/>
	    </td></tr></table>
	</c:when>
	
	<c:otherwise>
		<table  class="inLineTable fieldsetTableAdmin">
		    <c:forEach var="event" items="${requestScope.eventsListPaginator.resultList}">
		        <tr>
		            <th>
		                <html:link action="/DisplayEvent">
		                    ${event.title}
		                    <html:param name="eventId" value="${event.id}"/>
		                </html:link>
		            </th>
		            <td class="left">
		                <bean:message key="events.10"/>
		                <bean:write name="event" property="startDate" format="dd/MM/yyyy"/>,
		                <bean:message key="events.16"/>
						<html:link action="/DisplayMember">
	                    	<html:param name="idMember" value="${event.creator.id}"/>
	                    	${event.creator.firstName} ${event.creator.name}
	              </html:link>
		            </td>
		        </tr>
		    </c:forEach>
		</table>
		<c:set var="paginatorInstance" value="${requestScope.eventsListPaginator}" scope="request"/>
		<c:set var="paginatorAction" value="/Events" scope="request"/>
		<c:set var="paginatorTile" value="eventsLists" scope="request"/>
		<c:import url="/content/pagination/Pagination.jsp"/>
	</c:otherwise>
  </c:choose>
</fieldset>