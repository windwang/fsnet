<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3><bean:message key="communities.listCommunities"/></h3>

<c:choose>
    <c:when test="${! empty requestScope.communitiesSearchPaginator.resultList}">
        <table  class="inLineTable">
            <c:forEach var="community" items="${requestScope.communitiesSearchPaginator.resultList}">
                <tr class="content">
                    <td>
                        <c:import url="/FavoriteFragment.do">
                            <c:param name="interactionId" value="${community.id}"/>
                        </c:import>
                        <html:link action="/DisplayCommunity" title="${empty community.interests?\"\":community.interests}">
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
                            <html:link action="/Communities.do" styleClass="button" onclick="confirmDelete('DeleteCommunity.do?communityId='+${community.id})">   
                                <bean:message key="communities.delete"/>
                            </html:link>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:set var="paginatorInstance" value="${requestScope.communitiesSearchPaginator}" scope="request"/>
		<c:set var="paginatorAction" value="/SearchCommunity" scope="request"/>
		<c:set var="paginatorTile" value="searchCommunity" scope="request"/>
		<c:import url="/content/pagination/Pagination.jsp"/>
    </c:when>
    <c:otherwise>
        <bean:message key="communities.noResult"/>
    </c:otherwise>
</c:choose>

<h3><bean:message key="communities.search"/></h3>
<html:form action="SearchCommunity" method="GET">
    <div id="SearchCommunity">
        <html:text property="searchText" />
        <html:submit styleClass="button"><bean:message key="communities.searchButton"/></html:submit>
    </div>
</html:form>
