<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<h3><bean:message key="communities.listYourCommunities"/></h3>

<c:choose>
    <c:when test="${not empty requestScope.myCommunitiesPaginator.resultList}">
        <table  class="inLineTable">
            <c:forEach var="community" items="${requestScope.myCommunitiesPaginator.resultList}">
                <tr class="content">
                    <td>
                        <c:import url="/FavoriteFragment.do">
                            <c:param name="interactionId" value="${community.id}"/>
                        </c:import>
                        <html:link action="/DisplayCommunity">
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
                    <td class="tableButton">
                        <c:if test="${sessionScope.userId eq community.creator.id}">
                            <html:link action="/DisplayCommunities.do" styleClass="button" onclick="confirmDelete('DeleteCommunity.do?communityId='+${community.id})">
                                <bean:message key="communities.delete"/>
                            </html:link>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:set var="paginatorInstance" value="${requestScope.myCommunitiesPaginator}" scope="request"/>
		<c:set var="paginatorAction" value="/Communities" scope="request"/>
		<c:set var="paginatorTile" value="myCommunities" scope="request"/>
		<c:import url="/content/pagination/Pagination.jsp"/>
    </c:when>
    <c:otherwise>
        <bean:message key="communities.noResult"/>
    </c:otherwise>
</c:choose>
