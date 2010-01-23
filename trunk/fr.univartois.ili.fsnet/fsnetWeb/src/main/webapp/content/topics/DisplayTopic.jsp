<%-- 
    Document   : Topic
    Created on : 19 janv. 2010 23:51
    Author     : Zhu rui <zrhurey@gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<h3>${topic.sujet} : Messages </h3>

<table>
    <c:forEach var="msg" items="${topic.lesMessages}">
        <tr><td><html:link action="">
                    <html:param name="msgId" value="${msg.id}" />
                    ${msg.dateMessage} ${msg.propMsg.prenom} ${msg.propMsg.nom}</html:link></td></tr>
                <c:if test="${not empty msg.contenu}">
            <tr><td>${msg.contenu}</td></tr>
        </c:if>
    </c:forEach>
</table>

