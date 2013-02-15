<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<bean:define id="searchMessage">
	<bean:message key="communities.placeholder.search" />
</bean:define>

<%@ include file="YourCommunities.jsp" %>
<%@ include file="ModifyCommunity.jsp" %>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="communities.title.searchYourCommunities" />
	</legend>
	<table id="SearchCommunity"
		class="inLineTable tableStyle">
		<html:form action="SearchYourCommunities" method="GET">
			<tr>
				<td><html:text property="searchYourText" styleId="searchTexte" styleClass="search-query" />
					<ili:placeHolder id="searchTexte" value="${searchMessage}" /> <html:submit
						styleClass="btn btn-inverse">
						<bean:message key="communities.button.search" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>

<ili:interactionFilter user="${ socialEntity }"
	right="${ rightCreateCommunity }">
	<%@ include file="CreateCommunity.jsp" %>
</ili:interactionFilter>