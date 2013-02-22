<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

	<s:url action="/DisplayCommunity">
		<s:param name="communityId"
			value="%{requestScope.topic.hub.community.id}" />
        ${requestScope.topic.hub.community.title}
    </s:url>
	-&gt;
	<s:url action="/DisplayHub">
		<s:param name="hubId" value="%{requestScope.topic.hub.id}" />
        ${requestScope.topic.hub.title}
    </s:url>
	-&gt;
	<s:url action="/DisplayTopic">
		<s:param name="topicId" value="%{requestScope.topic.id}" />
        ${requestScope.topic.title}
    </s:url>

	-&gt;
	<s:text name="topics.title.message" />
</h3>

<c:forEach var="msg"
	items="${requestScope.topicMessageDisplayPaginator.resultList}">
	<table class="topicTable tableStyle">
		<tr class="topicHeader">
			<td><s:property value="msg" /></td>
			<td style="text-align: right;"><c:if
					test="${sessionScope.userId eq msg.from.id}">

					<s:a href="/DeleteTopicMessage" styleClass="btn btn-inverse">
						<s:param name="topicId" value="%{topic.id}" />
						<s:param name="messageId" value="%{msg.id}" />
						<s:text name="topics.button.deleteMessage" />
					</s:a>

					<s:a href="/DisplayModifyTopicMessage" styleClass="btn btn-inverse">
						<s:param name="topicId" value="%{topic.id}" />
						<s:param name="messageId" value="%{msg.id}" />
						<s:text name="topics.button.modifyMessage" />

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

<s:a href="/DisplayCreateTopicMessage" styleClass="btn btn-inverse">
	<s:param name="topicId" value="%{topic.id}" />
	<s:text name="topics.button.answerMessage" />

</s:a>



