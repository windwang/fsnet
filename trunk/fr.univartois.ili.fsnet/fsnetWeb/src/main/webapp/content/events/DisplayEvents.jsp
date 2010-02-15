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

<html:form action="/Events">
    <div id="SearchEvent">
        <html:text property="searchString" />
        <html:submit styleClass="button" >
            <bean:message key="events.11"/>
        </html:submit>
    </div>
</html:form>

<h3>
    <bean:message key="events.9"/>
</h3>
<logic:messagesPresent property="searchString">
    <html:errors property="searchString"/>
</logic:messagesPresent>
<table  class="inLineTable">
    <c:forEach var="event" items="${events}">
        <tr>
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
                <html:link action="/DisplayProfile">
                    <html:param name="id" value="${event.creator.id}"/>
                    ${event.creator.firstName} ${event.creator.name}
                </html:link>
            </td>
            <td  class="tableButton">
                <ili:substring beginIndex="0" endIndex="30"><ili:noxml>${event.content}</ili:noxml></ili:substring>
            </td>
        </tr>
    </c:forEach>
</table>