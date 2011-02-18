<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<h3><bean:message key="communities.search"/></h3>
<html:form action="SearchCommunity">
    <div id="SearchCommunity">
        <html:text property="searchText" />
        <html:submit styleClass="button"><bean:message key="communities.searchButton"/></html:submit>
    </div>
</html:form>

<h3><bean:message key="communities.listCommunities"/></h3>

<c:choose>
<c:when test="${not empty requestScope.communitiesListPaginator.resultList}">
	<table  class="inLineTable">
        <c:forEach var="community" items="${requestScope.communitiesListPaginator.resultList}">
            <tr class="content">
                <td>${community.title}      
                </td>
                <td>
	                <bean:message key="communities.by"/>
	                <html:link action="/DisplayMember">
	                    <html:param name="idMember" value="${community.creator.id}"/>
	                    ${community.creator.firstName} ${community.creator.name}
	                </html:link>
            	</td>
                <td class="tableButton">
                    <a class="button" onclick="confirmDelete('DeleteCommunity.do?communityId='+${community.id})">
                        <bean:message key="communities.delete"/>
                    </a>
            	</td>
            </tr>
        </c:forEach>
    </table>
    <c:set var="paginatorInstance" value="${requestScope.communitiesListPaginator}" scope="request"/>
	<c:set var="paginatorAction" value="/Communities" scope="request"/>
	<c:set var="paginatorTile" value="communities" scope="request"/>
	<c:import url="/content/pagination/Pagination.jsp"/>
</c:when>
<c:otherwise>
	<bean:message key="communities.noResult"/>
</c:otherwise>
</c:choose>