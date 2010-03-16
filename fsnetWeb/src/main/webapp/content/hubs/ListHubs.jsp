<%-- 
    Document   : ListHubs
    Author     : Audrey Ruellan and Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<h3><bean:message key="hubs.hubs"/></h3>
<c:if test="${empty requestScope.listHubPaginator.resultList}">
    <bean:message key="hubs.hubNotFound"/>
</c:if>
<table class="inLineTable">
    <c:forEach var="hub" items="${requestScope.listHubPaginator.resultList}">
        <tr>
            <th>
                <c:import url="/FavoriteFragment.do">
                    <c:param name="interactionId" value="${hub.id}"/>
                </c:import>
                <html:link action="/DisplayHub">
                    <html:param name="hubId" value="${hub.id}"/>
                    ${hub.title}
                </html:link>
            </th>
            <td>
                <bean:message key="hubs.createdOn"/>
                <bean:write name="hub" property="creationDate" format="dd/MM/yyyy"/>
                <bean:message key="hubs.by"/>
                <ili:getSocialEntityInfos socialEntity="${hub.creator}"/>
            </td>
            <td class="tableButton">
                <c:if test="${sessionScope.userId eq hub.creator.id}">
                    <a class="button" onclick="confirmDelete('DeleteHub.do?hubId='+${hub.id}+'&communityId='+${hub.community.id})">
                        <bean:message key="hubs.delete"/>
                    </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
<c:set var="paginatorInstance" value="${requestScope.listHubPaginator}" scope="request"/>
<c:set var="paginatorAction" value="/DisplayCommunity" scope="request"/>
<c:set var="paginatorTile" value="hubList" scope="request"/>
<c:import url="/content/pagination/Pagination.jsp"/>