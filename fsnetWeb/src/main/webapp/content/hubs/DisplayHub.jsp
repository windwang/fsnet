<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan, Cerelia Besnainou and Zhu rui <zrhurey@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<h3>
    <c:import url="/FavoriteFragment.do">
        <c:param name="interactionId" value="${hubResult.id}"/>
    </c:import>

    <bean:write name="hubResult" property="title" />
</h3>

<c:set var="theInteraction" value="${hubResult}" scope="request"/>
<jsp:include page="/content/interactions/LargeInteractionInfo.jsp" />
<div class="clear"></div>

<h3><bean:message key="hubs.searchTopic"/></h3>
<table  class="inLineTable">
    <html:form action="/SearchTopic" method="GET">
        <tr>
            <td><label><bean:message key="hubs.subjectTopic"/> :</label></td>
            <td><html:text property="topicSujetSearch" styleId="topicSujet" /></td>
            <td><html:hidden property="hubId" value="${hubResult.id}"/></td>
            <td><html:submit styleClass="button"><bean:message key="hubs.searchTopic"/></html:submit></td>
        </tr>
    </html:form>
</table>

<h3>
    <html:link action="/DisplayCommunity">
        <html:param name="communityId" value="${hubResult.community.id}"/>
        ${hubResult.community.title}
    </html:link>
	-&gt;
    <html:link action="/DisplayHub">
        <html:param name="hubId" value="${hubResult.id}"/>
        ${hubResult.title}
    </html:link>
	-&gt;
    <bean:message key="hubs.topics"/>
</h3>
<table class="inLineTable">
<logic:empty name="topicsLastMessage">
    <bean:message key="hubs.notopics"/>
</logic:empty>
    <c:forEach var="couple" items="${topicsLastMessage}">
        <c:set var="theTopic" value="${couple.key}"/>
        <tr>
            <td>
                <c:import url="/FavoriteFragment.do">
                    <c:param name="interactionId" value="${theTopic.id}"/>
                </c:import>
            </td>
            <td>
                <html:link action="/DisplayTopic" title='${empty theTopic.interests? "" : theTopic.interests}'>
                    <html:param name="topicId" value="${theTopic.id}"/>
                    ${theTopic.title}
                </html:link>
                (${fn:length(theTopic.messages)} messages)
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
                    <bean:message key="hubs.by"/> 
                    <ili:getSocialEntityInfos socialEntity="${lastMessage.from}"/>
                </logic:notEmpty>
                <logic:empty name="couple" property="value">
                    <bean:message key="hubs.noMessage"/>
                </logic:empty>
            </td>
            <c:if test="${sessionScope.userId eq couple.key.creator.id}">
                <td class="tableButton">
                    <a class="button" onclick="confirmDelete('DeleteTopic.do?topicId='+${couple.key.id}+'&hubId='+${hubResult.id})">
                        <bean:message key="hubs.deleteTopic"/>
                    </a>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>

<html:link action="/DisplayCreateTopic" styleClass="button" >
    <html:param name="hubId" value="${hubResult.id}"/>
    <bean:message key="hubs.createTopic"/>
</html:link>
