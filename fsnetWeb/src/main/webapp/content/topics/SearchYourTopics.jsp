<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<bean:define id="searchMessage">
	<bean:message key="topics.placeholder.search" />
</bean:define>


<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="hubs.title.search" />
	</legend>
	<table class="inLineTable tableStyle">

		<tr>
			<td><html:form action="/SearchYourTopics" method="get">
					<div id="SearchYourTopics">
						<html:text property="searchText" styleId="searchTexte"
							styleClass="search-query" />
						<ili:placeHolder id="searchTexte" value="${searchMessage}" />
						<s:hidden name="hubId" value="${hubResult.id}" />
						<html:submit styleClass="btn btn-inverse">
							<bean:message key="topics.button.search" />
						</html:submit>
					</div>
				</html:form></td>
		</tr>
	</table>
</fieldset>


<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="hubs.title.hub" />
		${hubResult.title} -
		<bean:message key="topics.title.myTopics" />
	</legend>
	<table class="inLineTable tableStyle">
		<logic:empty name="topicsLastMessage">
			<bean:message key="topics.noTopics" />
		</logic:empty>
		<c:forEach var="couple" items="${topicsLastMessage}">
			<tr>
				<td>
					<!-- TODO gerer les favoris --> <img src="images/non-favorite.png"
					alt="Favorite" onclick="this.src='images/favorite.png';"
					onmouseover="this.style.cursor='pointer'" /> <img
					src="images/message.png" alt="Message" />
				</td>
				<td><s:a action="/DisplayTopic"
						title='${empty couple.key.interests? "" : couple.key.interests}'>
						<html:param name="topicId" value="${couple.key.id}" />
                    ${couple.key.title}
                </s:a> <br /> <bean:message key="topics.createdOn" /> <bean:write
						name="hubResult" property="creationDate" format="dd/MM/yyyy" /> <bean:message
						key="topics.by" /> <ili:getSocialEntityInfos
						socialEntity="${couple.key.creator}" /></td>

				<td style="background-color: #C7E5F8;"><logic:notEmpty
						name="couple" property="value">
						<c:set var="lastMessage" value="${couple.value}" />
						<bean:write name="lastMessage" property="creationDate"
							format="dd/MM/yyyy" />
						<br />
						<bean:message key="topics.by" /> ${lastMessage.from.firstName} ${lastMessage.from.name}
                </logic:notEmpty> <logic:empty name="couple" property="value">
						<bean:message key="topics.noMessage" />
					</logic:empty></td>
				<c:if test="${sessionScope.userId eq couple.key.creator.id}">
					<td class="tableButton"><a class="btn btn-inverse"
						onclick="confirmDelete('DeleteYourTopic.do?topicId='+${couple.key.id}+'&hubId='+${hubResult.id}, '<bean:message key="message.confirmation.delete" />');">
							<bean:message key="topics.button.delete" />
					</a></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</fieldset>