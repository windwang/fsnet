
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>
    <jsp:include page="/FavoriteFragment.do">
        <jsp:param name="interactionId" value="${event.id}"/>
    </jsp:include>
    ${event.title}
</h3>

<table id="DisplayEvent" style="border-left: 1px solid gray;">
    <tr class="authorDate">
        <td>
            <bean:message key="events.5"/>
            <html:link action="/DisplayProfile">
                <html:param name="id" value="${event.creator.id}"/>
                ${event.creator.firstName} ${event.creator.name}
            </html:link>, 
            <bean:message key="events.6"/> 
            <bean:write name="event" property="startDate" format="dd/MM/yyyy" />
        </td>
    </tr>
    <tr>
        <td>
            ${event.content}
        </td>
    </tr>
    <tr>
        <td  class="alignRight">
            <html:link  action="/SubscribeEvent" styleClass="button">
                <html:param name="eventId" value="${event.id}"/>
                <bean:message key="events.17"/>
            </html:link>
        </td>
    </tr>
    <tr>
        <td  class="alignRight">
            <c:if test="${user.id eq event.creator.id}">
                <html:link  action="/DeleteEvent" styleClass="button">
                    <html:param name="eventId" value="${event.id}"/>
                    <bean:message key="events.7"/>
                </html:link>
            </c:if>
        </td>
    </tr>
</table>