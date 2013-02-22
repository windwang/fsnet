<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
		<c:import url="/FavoriteFragment.do">
			<c:param name="interactionId" value="${requestScope.topic.id}" />
		</c:import>
		${requestScope.topic.title}
	</legend>

	<c:set var="theInteraction" value="${topic}" scope="request" />
	<jsp:include page="/content/interactions/LargeInteractionInfo.jsp" />
</fieldset>
<div class="clear"></div>

<h3>

	<s:a action="/DisplayCommunity">
		<html:param name="communityId"
			value="${requestScope.topic.hub.community.id}" />
        ${requestScope.topic.hub.community.title}
    </s:a>
	-&gt;
	<s:a action="/DisplayHub">
		<html:param name="hubId" value="${requestScope.topic.hub.id}" />
        ${requestScope.topic.hub.title}
    </s:a>
	-&gt;
	<s:a action="/DisplayTopic">
		<html:param name="topicId" value="${requestScope.topic.id}" />
        ${requestScope.topic.title}
    </s:a>
	-&gt;
	<bean:message key="topics.title.message" />
</h3>

<c:forEach var="msg"
	items="${requestScope.topicMessageDisplayPaginator.resultList}">
	<table class="topicTable tableStyle">
		<tr class="topicHeader">
			<td><bean:write name="msg" property="creationDate"
					format="dd/MM/yyyy HH:mm" /></td>
			<td style="text-align: right;"><c:if
					test="${sessionScope.userId eq msg.from.id}">

					<s:a action="/DeleteTopicMessage" styleClass="btn btn-inverse">
						<html:param name="topicId" value="${topic.id}" />
						<html:param name="messageId" value="${msg.id}" />
						<bean:message key="topics.button.deleteMessage" />
					</s:a>

					<s:a action="/DisplayModifyTopicMessage" styleClass="btn btn-inverse">
						<html:param name="topicId" value="${topic.id}" />
						<html:param name="messageId" value="${msg.id}" />
						<bean:message key="topics.button.modifyMessage" />
					</s:a>

				</c:if></td>
		</tr>
		<tr>
			<td class="topicOwner"><ili:getSocialEntityInfos
					socialEntity="${msg.from}" /> <br /> <img
				src="avatar/${msg.from.id}.png" alt="Avatar"></td>
			<td class="topicMessage"><c:if test="${not empty msg.body}">
                    ${msg.body}
                </c:if></td>
		</tr>
	</table>

</c:forEach>
<c:set var="paginatorInstance"
	value="${requestScope.topicMessageDisplayPaginator}" scope="request" />
<c:set var="paginatorAction" value="/DisplayTopic" scope="request" />
<c:set var="paginatorTile" value="displayTopic" scope="request" />
<c:import url="/content/pagination/Pagination.jsp" />

<s:a action="/DisplayCreateTopicMessage" styleClass="btn btn-inverse">
	<html:param name="topicId" value="${topic.id}" />
	<bean:message key="topics.button.answerMessage" />
</s:a>



