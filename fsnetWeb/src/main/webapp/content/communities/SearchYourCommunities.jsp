<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<jsp:include page="/content/communities/YourCommunities.jsp"/>
<h3><bean:message key="communities.searchYourCommunities"/></h3>
<html:form action="SearchYourCommunities" method="GET">
    <div id="SearchCommunity">
        <html:text property="searchCommunityText" />
        <html:submit styleClass="button"><bean:message key="communities.searchButton"/></html:submit>
    </div>
</html:form>
<jsp:include page="/content/communities/CreateCommunity.jsp"/>