<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<bean:define id="searchMessage"><bean:message key="community.search"/></bean:define>

<jsp:include page="/content/communities/YourCommunities.jsp"/>
<jsp:include page="/content/communities/ModifyCommunity.jsp"/>
<h3><bean:message key="communities.searchYourCommunities"/></h3>
<table  class="inLineTable"><tr><td>
<html:form action="SearchYourCommunities" method="GET">
    <div id="SearchCommunity">
        <html:text property="searchCommunityText" styleId="searchTexte"  />
        <ili:placeHolder id="searchTexte" value="${searchMessage}" /> 
        <html:submit styleClass="button"><bean:message key="communities.searchButton"/></html:submit>
    </div>
</html:form>
</td></tr></table>
<ili:interactionFilter user="${ socialEntity }" right="${ rightCreateCommunity }">
	<jsp:include page="/content/communities/CreateCommunity.jsp"/>
</ili:interactionFilter>