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

<div style="float: left;width: 70%; background-color: #ecf8ff; padding: 3px;">
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
</div>

<c:set var="theInteraction" value="${event}" scope="request"/>
<div class="interactionInfo" style="width: 27%; text-align: left; float: left;
     background-color: #ecf8ff; border: 2px solid #bcd3e0; padding: 3px;">
    Owner :
    <html:link action="/DisplayProfile">
        <html:param name="id" value="${theInteraction.creator.id}"/>
        ${theInteraction.creator.firstName} ${theInteraction.creator.name}
    </html:link>
    <br/>
    X Subscribers
    <br/>
    Created on <bean:write name="theInteraction" property="creationDate" format="dd/MM/yyyy" />
    <br/>
    X Followers
    <br/>
    Visibility : public
    <br/>
    <div class="cloud">
        <c:forEach var="interest" items="${theInteraction.interests}">
            <span class="otag">
                <html:link action="/InterestInformations">
                    <html:param name="infoInterestId" value="${interest.id}"/>
                    ${interest.name}
                </html:link>
            </span>
        </c:forEach>
    </div>
</div>