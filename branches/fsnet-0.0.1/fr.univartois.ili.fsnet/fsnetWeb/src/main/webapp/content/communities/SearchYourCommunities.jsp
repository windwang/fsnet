<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<h3><bean:message key="communities.searchYourCommunities"/></h3>
<html:form action="SearchYourCommunities">
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
                        <html:link action="/DisplayProfile">
                            <html:param name="id" value="${community.creator.id}"/>
                            ${community.creator.firstName} ${community.creator.name}
                        </html:link>
                    </td>
                    <td class="tableButton">
                        <c:if test="${sessionScope.user.id eq community.creator.id}">
                            <html:link action="/DeleteYourCommunity" styleClass="button" >
                                <html:param name="communityId" value="${community.id}"/>
                                <bean:message key="communities.delete"/>
                            </html:link>
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
