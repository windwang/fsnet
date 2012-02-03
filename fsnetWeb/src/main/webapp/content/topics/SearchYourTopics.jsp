<%-- 
    Document   : SearchYourTopics
    Author     : Audrey Ruellan, Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>



<h3><bean:message key="hubs.searchTopic"/></h3>
<table class="inLineTable">
    <html:form action="/SearchYourTopics" method="GET">
        <tr>
            <td><label><bean:message key="hubs.subjectTopic"/></label></td>
            <td><html:text property="searchText" styleId="topicSujet" /></td>
            <td><html:hidden property="hubId" value="${hubResult.id}"/></td>
            <td><html:submit styleClass="button"><bean:message key="hubs.searchTopic"/></html:submit></td>
        </tr>
    </html:form>
</table>


<h3><bean:message key="hubs.hub"/> ${hubResult.title} - <bean:message key="topics.yourTopics"/></h3>
<table class="inLineTable">
<logic:empty name="topicsLastMessage">
    <bean:message key="hubs.notopics"/>
</logic:empty>
    <c:forEach var="couple" items="${topicsLastMessage}">
        <tr>
            <td>
                <!-- TODO gerer les favoris -->
                <img src="images/non-favorite.png" alt="Favorite" onclick="this.src='images/favorite.png';" onmouseover="this.style.cursor='pointer'"/>
                <img src="images/message.png"/>
            </td>
            <td>
                <html:link action="/DisplayTopic" title='${empty couple.key.interests? "" : couple.key.interests}'>
                    <html:param name="topicId" value="${couple.key.id}"/>
                    ${couple.key.title}
                </html:link>
                <br/>
                <bean:message key="hubs.createdOn"/>
                <bean:write name="hubResult" property="creationDate" format="dd/MM/yyyy"/>
                <bean:message key="hubs.by"/>
                <ili:getSocialEntityInfos socialEntity="${couple.key.creator}"/>
            </td>

            <td style="background-color: #C7E5F8;">
                <logic:notEmpty name="couple" property="value">
                    <c:set var="lastMessage" value="${couple.value}"/>
                    <bean:write name="lastMessage" property="creationDate" format="dd/MM/yyyy"/>
                    <br/>
                    <bean:message key="hubs.by"/> ${lastMessage.from.firstName} ${lastMessage.from.name}
                </logic:notEmpty>
                <logic:empty name="couple" property="value">
                    <bean:message key="hubs.noMessage"/>
                </logic:empty>
            </td>
            <c:if test="${sessionScope.userId eq couple.key.creator.id}">
                <td class="tableButton">
                    <a class="button" onclick="confirmDelete('DeleteYourTopic.do?topicId='+${couple.key.id}+'&hubId='+${hubResult.id})">
                        <bean:message key="hubs.deleteTopic"/>
                    </a>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>