<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>
    <c:import url="/FavoriteFragment.do">
        <c:param name="interactionId" value="${event.id}"/>
    </c:import>
    ${event.title}
</h3>

<div class="interactionDisplay">
    <table style="width: 100%">
        <tr class="authorDate">
            <td>
                <bean:message key="events.5"/>
                <html:link action="/DisplayProfile">
                    <html:param name="id" value="${event.creator.id}"/>
                    ${event.creator.firstName} ${event.creator.name}
                </html:link>, 
                <bean:message key="events.willoccur"/>
                <bean:write name="event" property="startDate" format="dd/MM/yyyy hh:mm" />
                <bean:message key="events.to"/>
                <bean:write name="event" property="endDate" format="dd/MM/yyyy hh:mm" />
                <c:if test="${subscriber}">,&nbsp;&nbsp;"<bean:message key="events.19"/>"</c:if>
            </td>
        </tr>
        <tr>
            <td>
                ${event.content}
            </td>
        </tr>
        
        <tr>
            <td  class="alignRight">
                <c:if test="${not subscriber}">
                <html:link  action="/SubscribeEvent" styleClass="button">
                    <html:param name="eventId" value="${event.id}"/>
                    <bean:message key="events.17"/>
                </html:link>
            </c:if>

            <c:if test="${subscriber}">
                <html:link  action="/UnsubscribeEvent" styleClass="button">
                    <html:param name="eventId" value="${event.id}"/>
                    <bean:message key="events.18"/>
                </html:link>
            </c:if>
                
                <c:if test="${userId eq event.creator.id}">
                    <html:link  action="/DeleteEvent" styleClass="button">
                        <html:param name="eventId" value="${event.id}"/>
                        <bean:message key="events.7"/>
                    </html:link>
                </c:if>
            </td>
        </tr>
    </table>
</div>

<c:set var="theInteraction" value="${event}" scope="request"/>
<jsp:include page="/content/interactions/InteractionInfo.jsp" />
