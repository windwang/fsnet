<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:set name="searchMessage">
	<s:text name="topics.placeholder.search" />
</s:set>

<fieldset class="fieldsetCadre">
	<legend>
		<c:import url="/FavoriteFragment.do">
			<c:param name="interactionId" value="${hubResult.id}" />
		</c:import>
		<s:property value="title" />
	</legend>

	<c:set var="theInteraction" value="${hubResult}" scope="request" />
	<jsp:include page="/content/interactions/LargeInteractionInfo.jsp" />
</fieldset>
<div class="clear"></div>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="hubs.title.searchTopic" />
	</legend>
	<table class="inLineTable tableStyle">
		<s:form action="/SearchTopic" method="get">
			<tr>
				<td><s:textfield property="topicSujetSearch" styleId="topicSujet" />
					<ili:placeHolder id="topicSujet" value="${searchMessage}" /></td>
<<<<<<< HEAD
				<td><s:hidden name="hubId" value="%{hubResult.id}" /></td>
				<td><s:submit styleClass="btn btn-inverse">
						<s:text name="hubs.button.searchTopic" />
					</s:submit></td>
=======
				<td><s:hidden name="hubId" value="${hubResult.id}" /></td>
				<td><html:submit styleClass="btn btn-inverse">
						<bean:message key="hubs.button.searchTopic" />
					</html:submit></td>
>>>>>>> 75d3d39903713564d348b5ead824e237db25c0ee
			</tr>
		</s:form>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
<<<<<<< HEAD
		<s:a href="/DisplayCommunity">
			<s:param name="communityId" value="%{hubResult.community.id}" />
        ${hubResult.community.title}
    </s:a>
		-&gt;
		<s:a href="/DisplayHub">
			<s:param name="hubId" value="%{hubResult.id}" />
=======
		<s:a action="/DisplayCommunity">
			<html:param name="communityId" value="${hubResult.community.id}" />
        ${hubResult.community.title}
    </s:a>
		-&gt;
		<s:a action="/DisplayHub">
			<html:param name="hubId" value="${hubResult.id}" />
>>>>>>> 75d3d39903713564d348b5ead824e237db25c0ee
        ${hubResult.title}
    </s:a>
		-&gt;
		<s:text name="hubs.title.topics" />
	</legend>
	<table class="inLineTable tableStyle">
	
		<s:if test="%{topicsLastMessage.size == 0}">
			<tr>
				<td><s:text name="hubs.noTopics" /></td>
			</tr>
		</s:if>
		
		<c:forEach var="couple" items="${topicsLastMessage}">
			<c:set var="theTopic" value="${couple.key}" />
			<tr>
				<td><c:import url="/FavoriteFragment.do">
						<c:param name="interactionId" value="${theTopic.id}" />
					</c:import></td>
<<<<<<< HEAD
				<td><s:a href="/DisplayTopic"
						title='%{empty theTopic.interests? "" : theTopic.interests}'>
						<s:param name="topicId" value="%{theTopic.id}" />
                    ${theTopic.title}
                </s:a> (${fn:length(theTopic.messages)} messages) <br /> <s:text
						name="hubs.createdOn" />
						<s:property value="creationDate" />
				<s:text name="hubs.by" /> <ili:getSocialEntityInfos
=======
				<td><s:a action="/DisplayTopic"
						title='${empty theTopic.interests? "" : theTopic.interests}'>
						<html:param name="topicId" value="${theTopic.id}" />
                    ${theTopic.title}
                </s:a> (${fn:length(theTopic.messages)} messages) <br /> <bean:message
						key="hubs.createdOn" /> <bean:write name="hubResult"
						property="creationDate" format="dd/MM/yyyy" /> <bean:message
						key="hubs.by" /> <ili:getSocialEntityInfos
>>>>>>> 75d3d39903713564d348b5ead824e237db25c0ee
						socialEntity="${couple.key.creator}" /></td>

				<td style="background-color: #C7E5F8;">
				
				<s:if test="%{couple.size != 0}">
					<c:set var="lastMessage" value="${couple.value}" />
						<s:property value="creationDate"/>
						<br />
						<s:text name="hubs.by" />
						<ili:getSocialEntityInfos socialEntity="${lastMessage.from}" />
				</s:if>
				
				<s:if test="%{couple.size == 0}">
					<s:text name="hubs.noMessage" />
				</s:if>
				
				</td>
				<c:if test="${sessionScope.userId eq couple.key.creator.id}">
					<td class="tableButton"><a class="btn btn-inverse"
						onclick="confirmDelete('DeleteTopic.do?topicId='+${couple.key.id}+'&hubId='+${hubResult.id}, '<bean:message key="message.confirmation.delete" />');">
							<s:text name="hubs.button.deleteTopic" />
					</a></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</fieldset>
