<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan, Cerelia Besnainou et Zhu rui <zrhurey@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html:form action="/CreateTopic">
    <h3><bean:message key="hubs.createTopic"/></h3>
    <table>
        <tr>
            <td><label><bean:message key="hubs.subjectTopic"/></label></td>
            <td><html:text property="topicSujet" styleId="topicSujet" /></td>
            <td><html:hidden property="hubId" value="${hubResult.id}"/></td>
            <td><html:submit styleClass="button"><bean:message key="hubs.createTopic"/></html:submit></td>
        </tr>
    </table>
</html:form>

<h3><bean:message key="hubs.searchTopic"/></h3>
<table>
    <html:form action="/SearchTopic">
        <tr>
            <td><label><bean:message key="hubs.subjectTopic"/></label></td>
            <td><html:text property="topicSujetSearch" styleId="topicSujet" /></td>
            <td><html:hidden property="hubId" value="${hubResult.id}"/></td>
            <td><html:submit styleClass="button"><bean:message key="hubs.searchTopic"/></html:submit></td>
        </tr>
    </html:form>
    <c:if test="${not empty resRearchTopics}">
        <tr>
            <th><bean:message key="hubs.resultSearchTopic"/></th>
        </tr>
        <c:forEach var="topic" items="${resRearchTopics}">
            <tr>
                <td> <html:link action="/DisplayTopic">
                        <html:param name="topicId" value="${topic.id}"/>
                        ${topic.sujet}
                    </html:link></td>
                <td>
                    <c:if test="${sessionScope.user.id eq topic.propTopic.id}">
                        <html:link action="/DeleteTopic" styleClass="button">
                            <html:param name="topicId" value="${topic.id}"/>
                            <html:param name="hubId" value="${hubResult.id}"/>
		    			<bean:message key="hubs.deleteTopic"/>
                        </html:link>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>


<h3><bean:message key="hubs.hub"/> ${hubResult.nomCommunaute} - <bean:message key="hubs.topics"/></h3>
<table class="inLineTable">
    <c:forEach var="couple" items="${topicsLastMessage}">
        <tr>
            <td>
                <img src="images/message.png"/>
            </td>
            <td>
                <html:link action="/DisplayTopic">
                    <html:param name="topicId" value="${couple.key.id}"/>
                    ${couple.key.sujet}
                </html:link>
                <br/>
                <bean:message key="hubs.createdOn"/>
                <bean:write name="hubResult" property="dateCreation" format="dd/MM/yyyy"/>
                <bean:message key="hubs.by"/>
                <html:link action="/DisplayProfile">
                    <html:param name="id" value="${hubResult.createur.id}"/>
                    ${hubResult.createur.prenom} ${hubResult.createur.nom}
                </html:link>
            </td>

            <td style="background-color: #C7E5F8;">
                <logic:notEmpty name="couple" property="value">
                    <c:set var="lastMessage" value="${couple.value}"/>
                    <bean:write name="lastMessage" property="dateMessage" format="dd/MM/yyyy"/>
                    <br/>
                     <bean:message key="hubs.by"/> ${lastMessage.propMsg.prenom} ${lastMessage.propMsg.nom}
                </logic:notEmpty>
                <logic:empty name="couple" property="value">
                    <bean:message key="hubs.noMessage"/>
                </logic:empty>
            </td>
            <c:if test="${sessionScope.user.id eq couple.key.propTopic.id}">
                <td class="tableButton">
                    <html:link action="/DeleteTopic" styleClass="button">
                        <html:param name="topicId" value="${couple.key.id}"/>
                        <html:param name="hubId" value="${hubResult.id}"/>
		    			 <bean:message key="hubs.deleteTopic"/>
                    </html:link>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>