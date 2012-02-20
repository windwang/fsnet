<%-- 
    Document   : Topic
    Created on : 19 janv. 2010 23:51
    Author     : Zhu rui <zrhurey@gmail.com>, Cerelia Besnainou and Audrey Ruellan
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetAppli">
  <legend class="legendHome">
    <c:import url="/FavoriteFragment.do">
        <c:param name="interactionId" value="${requestScope.topic.id}"/>
    </c:import>
    ${requestScope.topic.title}
  </legend>

<c:set var="theInteraction" value="${topic}" scope="request"/>
<jsp:include page="/content/interactions/LargeInteractionInfo.jsp" />
</fieldset>
<div class="clear"></div>

<h3>

    <html:link action="/DisplayCommunity">
        <html:param name="communityId" value="${requestScope.topic.hub.community.id}"/>
        ${requestScope.topic.hub.community.title}
    </html:link>
	-&gt;
    <html:link action="/DisplayHub">
        <html:param name="hubId" value="${requestScope.topic.hub.id}"/>
        ${requestScope.topic.hub.title}
    </html:link>
	-&gt;
    <html:link action="/DisplayTopic">
        <html:param name="topicId" value="${requestScope.topic.id}"/>
        ${requestScope.topic.title}
    </html:link>
	-&gt;
	Messages
</h3>

<c:forEach var="msg" items="${requestScope.topicMessageDisplayPaginator.resultList}">
    <table class="topicTable">
        <tr class="topicHeader">
            <td>
                <bean:write name="msg" property="creationDate" format="dd/MM/yyyy HH:mm"/> 
            </td>
            <td style="text-align:right;">
                <c:if test="${sessionScope.userId eq msg.from.id}">
           
			        <html:link action="/DeleteTopicMessage" styleClass="button">
			            <html:param name="topicId" value="${topic.id}" />
			            <html:param name="messageId" value="${msg.id}" />
			            <bean:message key="topics.deleteMsg"/>
			        </html:link>
			        
                    <html:link action="/DisplayModifyTopicMessage" styleClass="button">
                        <html:param name="topicId" value="${topic.id}" />
                        <html:param name="messageId" value="${msg.id}" />
                        <bean:message key="topics.modifyMsg"/>
                    </html:link>
                    
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="topicOwner">
                <ili:getSocialEntityInfos socialEntity="${msg.from}"/>
                <br/>
                <img src="avatar/${msg.from.id}.png" alt="Avatar">
            </td>
            <td class="topicMessage">
                <c:if test="${not empty msg.body}">
                    ${msg.body}
                </c:if>
            </td>
        </tr>
    </table>

</c:forEach>
<c:set var="paginatorInstance" value="${requestScope.topicMessageDisplayPaginator}" scope="request"/>
<c:set var="paginatorAction" value="/DisplayTopic" scope="request"/>
<c:set var="paginatorTile" value="displayTopic" scope="request"/>
<c:import url="/content/pagination/Pagination.jsp"/>

<html:link action="/DisplayCreateTopicMessage" styleClass="button">
    <html:param name="topicId" value="${topic.id}" />
    <bean:message key="topics.answerMsg"/>
</html:link>



