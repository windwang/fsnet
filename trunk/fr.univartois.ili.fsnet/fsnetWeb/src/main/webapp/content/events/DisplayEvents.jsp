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


<h3>Search Event</h3>
<html:form action="/Events">
    <div id="SearchEvent">
        <html:text property="searchString" />
        <html:submit styleClass="button" />
    </div>
</html:form>

<h3>Events :</h3>
<logic:messagesPresent property="searchString">
    <p class="errorMessage"><html:errors property="searchString"/></p>
</logic:messagesPresent>
<table  class="inLineTable">
    <c:forEach var="event" items="${events}">
        <tr>
            <th>
                <html:link action="/DisplayEvent">
                    ${event.nom}
                    <html:param name="eventId" value="${event.id}"/>
                </html:link>
            </th>
            <td class="left">
                will occur on
                <bean:write name="event" property="dateManifestation" format="dd/MM/yyyy"/>, by

            </td>
            <td  class="tableButton">
                ${event.contenu}
            </td>
        </tr>
    </c:forEach>
</table>