<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set name="searchMessage">
	<s:text name="communities.placeholder.search" />
</s:set>

<%@ include file="YourCommunities.jsp" %>
<%@ include file="ModifyCommunity.jsp" %>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="communities.title.searchYourCommunities" />
	</legend>
	<table id="SearchCommunity"
		class="inLineTable tableStyle">
		<s:form action="SearchYourCommunities" method="GET">
			<tr>
				<td>
					<s:textfield property="searchYourText" styleId="searchTexte" styleClass="search-query" />
					<ili:placeHolder id="searchTexte" value="${searchMessage}" /> 
					<s:submit
						styleClass="btn btn-inverse">
						<s:text name="communities.button.search" />
					</s:submit>
				</td>
			</tr>
		</s:form>
	</table>
</fieldset>

<ili:interactionFilter user="${ socialEntity }"
	right="${ rightCreateCommunity }">
	<%@ include file="CreateCommunity.jsp" %>
</ili:interactionFilter>