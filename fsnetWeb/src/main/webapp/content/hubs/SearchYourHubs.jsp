<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<bean:define id="searchMessage">
	<bean:message key="hubs.placeholder.search" />
</bean:define>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="hubs.title.search" />
	</legend>

	<table id="SearchYourHubs"
		class="inLineTable fieldsetTableAppli">
		<html:form action="/SearchYourHubs" method="GET">
			<tr>
				<td><html:hidden property="communityId"
						value="${param.communityId}" /> <html:text
						property="searchYourText" styleId="searchTexte" /> <ili:placeHolder
						id="searchTexte" value="${searchMessage}" /> <html:submit
						styleClass="button">
						<bean:message key="hubs.button.search" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>

<%@ include file="ModifyHub.jsp"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="hubs.title.myHubs" />
	</legend>
	<c:set var="hub" value="${hubResults}" />
	<table class="inLineTable fieldsetTableAppli">
		<logic:empty name="hub">
			<tr>
				<td><bean:message key="hubs.hubNotFound" />
				<td>
			</tr>
		</logic:empty>
		<c:forEach var="hub" items="${hubResults}">
			<tr>
				<th><html:link action="/DisplayHub"
						title='${empty hub.interests? "" : hub.interests}'>
						<html:param name="hubId" value="${hub.id}" />
                    ${hub.title}
                </html:link> (${fn:length(hub.topics)} topics)</th>
				<td><bean:message key="hubs.createdOn" /> <bean:write
						name="hub" property="creationDate" format="dd/MM/yyyy" /> <bean:message
						key="hubs.by" /> <ili:getSocialEntityInfos
						socialEntity="${hub.creator}" /></td>
				<td class="tableButton"><c:if
						test="${sessionScope.userId eq hub.creator.id}">
						<a class="button"
							onclick="confirmDelete('DeleteYourHub.do?hubId='+${hub.id}+'&communityId='+${hub.community.id}, '<bean:message key="message.confirmation.delete" />');">
							<bean:message key="hubs.button.delete" />
						</a>
					</c:if></td>
			</tr>

		</c:forEach>
	</table>
</fieldset>

<%@ include file="CreateHub.jsp"%>