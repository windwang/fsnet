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

<h3>${topic.sujet} : Messages </h3>
<table class="inLineTable">
    <c:forEach var="msg" items="${topic.lesMessages}">
        <tr>
           
            <td>
        	  Created on
                <bean:write name="msg" property="dateMessage" format="dd/MM/yyyy"/>
                by
                <html:link action="/DisplayProfile">
                    <html:param name="id" value="${msg.propMsg.id}"/>
                    ${msg.propMsg.prenom} ${msg.propMsg.nom}
                </html:link>
            </td>
            <td class="tableButton">
                <c:if test="${sessionScope.user.id eq msg.propMsg.id}">
                    <html:link action="/DisplayModifyTopicMessage" styleClass="button">
                        <html:param name="topicId" value="${topic.id}" />
                        <html:param name="messageId" value="${msg.id}" />
		           Modify
                    </html:link>
                </c:if>
            </td>
            <td class="tableButton">
                <c:if test="${sessionScope.user.id eq msg.propMsg.id}">
                    <html:link action="/DeleteTopicMessage" styleClass="button">
                        <html:param name="topicId" value="${topic.id}" />
                        <html:param name="messageId" value="${msg.id}" />
		           Delete
                    </html:link>
                </c:if>
            </td>
        </tr>
        <c:if test="${not empty msg.contenu}">
            <tr><td>${msg.contenu}</td></tr>
        </c:if>
    </c:forEach>
</table>
<html:link action="/DisplayCreateTopicMessage" styleClass="button">
    <html:param name="topicId" value="${topic.id}" />
    Answer
</html:link>
