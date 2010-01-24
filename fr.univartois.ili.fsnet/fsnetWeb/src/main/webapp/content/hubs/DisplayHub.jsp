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
    <h3>Create Topic :</h3>
    <table>
        <tr>
            <td><label>Topic's subjet :</label></td>
            <td><html:text property="topicSujet" styleId="topicSujet" /></td>
            <td><html:hidden property="hubId" value="${hubResult.id}"/></td>
            <td><html:submit styleClass="button">Create Topic</html:submit></td>
        </tr>
    </table>
</html:form>

<h3>Search Topic</h3>
<table>
    <html:form action="/SearchTopic">
        <tr>
            <td><label>Topic's subjet :</label></td>
            <td><html:text property="topicSujetSearch" styleId="topicSujet" /></td>
            <td><html:hidden property="hubId" value="${hubResult.id}"/></td>
            <td><html:submit styleClass="button">Search Topic</html:submit></td>
        </tr>
    </html:form>
    <c:if test="${not empty resRearchTopics}">
        <tr>
            <th>Result of search</th>
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
		    			Delete Topic
                        </html:link>
                    </c:if>
                </td>
            </tr>
        </c:forEach>

    </c:if>
</table>


<h3><bean:message key="hubs.hub"/> ${hubResult.nomCommunaute} - Topics</h3>
<table class="inLineTable">
    <c:forEach var="couple" items="${topicsLastMessage}">
        <tr>
            <td>
                <img src="images/message.svg" alt="Message Icon"/>
            </td>
            <td>
                <html:link action="/DisplayTopic">
                    <html:param name="topicId" value="${couple.key.id}"/>
                    ${couple.key.sujet}
                </html:link>
                <br/>
                Created on
                <bean:write name="hubResult" property="dateCreation" format="dd/MM/yyyy"/>
                by
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
                    par ${lastMessage.propMsg.prenom} ${lastMessage.propMsg.nom}
                </logic:notEmpty>
                Aucun Message
            </td>
            <c:if test="${sessionScope.user.id eq couple.key.propTopic.id}">
                <td class="tableButton">
                    <html:link action="/DeleteTopic" styleClass="button">
                        <html:param name="topicId" value="${couple.key.id}"/>
                        <html:param name="hubId" value="${hubResult.id}"/>
		    			Delete Topic
                    </html:link>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>