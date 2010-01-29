<%-- 
    Document   : Topic
    Created on : 19 janv. 2010 23:51
    Author     : Zhu rui <zrhurey@gmail.com>, Cerelia Besnainou et Audrey Ruellan
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<h3>${requestScope.topic.title} : Messages </h3>
<c:forEach var="msg" items="${requestScope.topic.messages}">

    <table class="topicTable">
        <tr class="topicHeader">
            <td colspan="2">
                <bean:write name="msg" property="creationDate" format="dd/MM/yyyy HH:mm"/>
            </td>
        </tr>
        <tr>
            <td class="topicOwner">
                <html:link action="/DisplayProfile">
                    <html:param name="id" value="${msg.from.id}"/>
                    ${msg.from.firstName} ${msg.from.name}
                </html:link>
                <br/>
                <img src="images/emblem-personal.png">
            </td>
            <td class="topicMessage">
                <c:if test="${not empty msg.body}">
                    ${msg.body}
                </c:if>
            </td>
        </tr>

    </table>
    <div class="topicButton">
        <c:if test="${sessionScope.user.id eq msg.from.id}">
            <html:link action="/DisplayModifyTopicMessage" styleClass="button">
                <html:param name="topicId" value="${topic.id}" />
                <html:param name="messageId" value="${msg.id}" />
                <bean:message key="topics.modifyMsg"/>
            </html:link>
        </c:if>

        <!--
        <c:if test="${sessionScope.user.id eq msg.from.id}">
            <html:link action="/DeleteTopicMessage" styleClass="button">
                <html:param name="topicId" value="${topic.id}" />
                <html:param name="messageId" value="${msg.id}" />
                <bean:message key="topics.deleteMsg"/>
            </html:link>
        </c:if>

        -->
    </div>
</c:forEach>


<html:link action="/DisplayCreateTopicMessage" styleClass="button">
    <html:param name="topicId" value="${topic.id}" />
    <bean:message key="topics.answerMsg"/>
</html:link>



