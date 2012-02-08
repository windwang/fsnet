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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<h3>
    <c:if test="${not empty requestScope.Community}">
        <html:link action="/DisplayCommunity">
            <html:param name="communityId" value="${requestScope.Community.id}"/>
            ${requestScope.Community.title}
        </html:link>
    </c:if>
	-&gt;
    <bean:message key="hubs.hubs"/>
</h3>
<table class="inLineTable">
<c:if test="${empty requestScope.listHubPaginator.resultList}">
    <tr><td><bean:message key="hubs.hubNotFound"/></td></tr>
</c:if>
    <c:forEach var="hub" items="${requestScope.listHubPaginator.resultList}">
        <tr>
            <th>
                <c:import url="/FavoriteFragment.do">
                    <c:param name="interactionId" value="${hub.id}"/>
                </c:import>
                <html:link action="/DisplayHub" title='${empty hub.interests? "" : hub.interests}'>
                    <html:param name="hubId" value="${hub.id}"/>
                    ${hub.title}
                </html:link>

                (${fn:length(hub.topics)} topics)
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