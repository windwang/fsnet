<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3><bean:message key="communities.searchYourCommunities"/></h3>
<html:form action="SearchYourCommunities" method="GET">
    <div id="SearchCommunity">
        <html:text property="searchText" />
        <html:submit styleClass="button"><bean:message key="communities.searchButton"/></html:submit>
    </div>
</html:form>

<h3><bean:message key="communities.listYourCommunities"/></h3>

<c:choose>
    <c:when test="${! empty communitiesResult}">
        <table  class="inLineTable">
            <c:forEach var="community" items="${communitiesResult}">
                <tr class="content">
                    <td>
                        <c:import url="/FavoriteFragment.do">
                            <c:param name="interactionId" value="${community.id}"/>
                        </c:import>
                        <html:link action="/DisplayCommunity">
                            <html:param name="communityId" value="${community.id}"/>
                            ${community.title}
                        </html:link>
                    </td>
                    <td>
                        <bean:message key="communities.by"/>
                      	<ili:getSocialEntityInfos socialEntity="${community.creator}"/>
                    </td>
                    <td class="tableButton">
                        <c:if test="${sessionScope.userId eq community.creator.id}">
                            <a class="button" onclick="confirmDelete('DeleteYourCommunity.do?communityId='+${community.id})">
                                <bean:message key="communities.delete"/>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <bean:message key="communities.noResult"/>
    </c:otherwise>
</c:choose>
