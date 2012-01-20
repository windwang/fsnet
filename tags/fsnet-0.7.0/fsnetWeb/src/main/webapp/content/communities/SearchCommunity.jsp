<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<h3><bean:message key="communities.search"/></h3>
<html:form action="SearchCommunity" method="GET">
    <div id="SearchCommunity">
        <html:text property="searchText" />
        <html:submit styleClass="button"><bean:message key="communities.searchButton"/></html:submit>
    </div>
</html:form>


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
                        <html:link action="/DisplayCommunity" title='${empty community.interests ? "" : community.interests}'>
                            <html:param name="communityId" value="${community.id}"/>
                            ${community.title}
                        </html:link> 
                         <c:choose>
                         	<c:when test="${fn:length(community.hubs) eq 0}">
                         		(<bean:message key="communities.notany.hubs"/> hub)
                         	</c:when>
                         	<c:when test="${fn:length(community.hubs) eq 1}">
                         		(1 hub)
                         	</c:when>
                         	<c:when test="${fn:length(community.hubs) gt 1}">
                         		(${fn:length(community.hubs)} hubs)
                         	</c:when>
                         </c:choose>
                    </td>
                    <td>
                        <bean:message key="communities.by"/>
                        <ili:getSocialEntityInfos socialEntity="${community.creator}"/>
                    </td>
                    <td class="tableButton" onclick="confirmDelete2('deleteid${community.id}');">
                    <c:if test="${sessionScope.userId eq community.creator.id}">
						<form action="DeleteCommunity.do" id="deleteid${community.id}" method="post"><input
							type="hidden" name="communityId" value="${community.id}" />
						</form>
						<span class="button">
						    <bean:message
							    key="communities.delete"/>
						</span>
					</c:if></td>
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


