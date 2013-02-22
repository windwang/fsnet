<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:set name="searchMessage">
	<s:text name="hubs.placeholder.searchh" />
</s:set>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="hubs.title.search" />
	</legend>

	<table id="SearchYourHubs"
		class="inLineTable tableStyle">
		<s:form action="/SearchYourHubs" method="GET">
			<tr>
				<td><s:hidden name="communityId"
<<<<<<< HEAD
						value="%{param.communityId}" /> <s:textfield
=======
						value="${param.communityId}" /> <html:text
>>>>>>> 75d3d39903713564d348b5ead824e237db25c0ee
						property="searchYourText" styleId="searchTexte" /> <ili:placeHolder
						id="searchTexte" value="${searchMessage}" /> <s:submit
						styleClass="btn btn-inverse">
						<s:text name="hubs.button.search" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
</fieldset>

<%@ include file="ModifyHub.jsp"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="hubs.title.myHubs" />
	</legend>
	<c:set var="hub" value="${hubResults}" />
	<table class="inLineTable tableStyle">
	
		<s:if test="%{hub.size==0}">
		<tr>
				<td><s:text name="hubs.hubNotFound" />
				<td>
			</tr>
		</s:if>
	
		<c:forEach var="hub" items="${hubResults}">
			<tr>
<<<<<<< HEAD
				<th><s:a href="/DisplayHub"
						title='%{empty hub.interests? "" : hub.interests}'>
						<s:param name="hubId" value="%{hub.id}" />
                    ${hub.title}
                </s:a> (${fn:length(hub.topics)} topics)</th>
				<td><s:text name="hubs.createdOn" />
				<s:property value="creationDate"/> 
				<ili:getSocialEntityInfos
=======
				<th><s:a action="/DisplayHub"
						title='${empty hub.interests? "" : hub.interests}'>
						<html:param name="hubId" value="${hub.id}" />
                    ${hub.title}
                </s:a> (${fn:length(hub.topics)} topics)</th>
				<td><bean:message key="hubs.createdOn" /> <bean:write
						name="hub" property="creationDate" format="dd/MM/yyyy" /> <bean:message
						key="hubs.by" /> <ili:getSocialEntityInfos
>>>>>>> 75d3d39903713564d348b5ead824e237db25c0ee
						socialEntity="${hub.creator}" /></td>
				<td class="tableButton"><c:if
						test="${sessionScope.userId eq hub.creator.id}">
						<a class="btn btn-inverse"
							onclick="confirmDelete('DeleteYourHub.do?hubId='+${hub.id}+'&communityId='+${hub.community.id}, '<bean:message key="message.confirmation.delete" />');">
							<s:text name="hubs.button.delete" />
						</a>
					</c:if></td>
			</tr>

		</c:forEach>
	</table>
</fieldset>

<%@ include file="CreateHub.jsp"%>