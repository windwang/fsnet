<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<s:set name="searchMessage">
	<s:text name="topics.placeholder.search" />
</s:set>


<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="hubs.title.search" />
	</legend>
	<table class="inLineTable tableStyle">

		<tr>
			<td><s:form action="/SearchYourTopics" method="get">
					<div id="SearchYourTopics">
						<s:textfield property="searchText" styleId="searchTexte"
							cssClass="search-query" />
						<ili:placeHolder id="searchTexte" value="%{searchMessage}" />
						<s:hidden property="hubId" value="%{hubResult.id}" />
						<s:submit styleClass="btn btn-inverse">
							<s:text name="topics.button.search" />
						</s:submit>
					</div>
				</s:form></td>
		</tr>
	</table>
</fieldset>


<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="hubs.title.hub" />
		${hubResult.title} -
		<s:text name="topics.title.myTopics" />
	</legend>
	<table class="inLineTable tableStyle">
		<s:if test="topicsLastMessage == null">
			<s:text name="topics.noTopics" />
		</s:if>
		<c:forEach var="couple" items="${topicsLastMessage}">
			<tr>
				<td>
					<!-- TODO gerer les favoris --> <img src="images/non-favorite.png"
					alt="Favorite" onclick="this.src='images/favorite.png';"
					onmouseover="this.style.cursor='pointer'" /> <img
					src="images/message.png" alt="Message" />
				</td>
				<td><s:a href="/DisplayTopic"
						title='%{empty couple.key.interests? "" : couple.key.interests}'>
						<s:param name="topicId" value="%{couple.key.id}" />
                    ${couple.key.title}
                </s:a> <br /> <s:text name="topics.createdOn" /> <s:property
						value="hubResult" /> <s:text name="topics.by" /> <ili:getSocialEntityInfos
						socialEntity="${couple.key.creator}" /></td>

				<td style="background-color: #C7E5F8;"><s:if
						test="couple != null">
						<c:set var="lastMessage" value="${couple.value}" />
						<s:property value="lastMessage" />
						<br />
						<s:text name="topics.by" /> 
						${lastMessage.from.firstName} ${lastMessage.from.name}
				</s:if> <s:if test="couple == null">
						<s:text name="topics.noMessage" />
					</s:if></td>
				<c:if test="${sessionScope.userId eq couple.key.creator.id}">
					<td class="tableButton"><a class="btn btn-inverse"
						onclick="confirmDelete('DeleteYourTopic.do?topicId='+${couple.key.id}+'&hubId='+${hubResult.id}, '<bean:message key="message.confirmation.delete" />');">
							<s:text name="topics.button.delete" />
					</a></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</fieldset>