<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3>
    <bean:message key="announce"/>s
</h3>

<c:choose>
	<c:when test="${empty requestScope.annoucesListPaginator.resultList}">
		<bean:message key="announce.emptyList"/>
	</c:when>
	<c:otherwise>
	    <table class="inLineTable">
	
	        <c:forEach var="announce" items="${requestScope.annoucesListPaginator.resultList}">
	        	<ili:interactionRow unreadInteractionsId="${requestScope.unreadInteractionsId}" currentInteractionId="${announce.id}">
	                <bean:define id="idAnnounce" name="announce" property="id" />
	                <th colspan="2">
	                  <c:import url="/FavoriteFragment.do">
	                        <c:param name="interactionId" value="${announce.id}"/>
	                    </c:import> 
	                    <html:link action="/DisplayAnnounce.do" paramId="idAnnounce" paramName="idAnnounce">
	                        <bean:write name="announce" property="title" />
	                    </html:link>
	                </th>
	                <td>
	                    <bean:message key="announce.by"/>
	                    <ili:getSocialEntityInfos socialEntity="${announce.creator}"/>
	                </td>
	                <td class="tableButton">
	                    <bean:message key="announce.expiryDate"/>
	                    <bean:write name="announce" property="endDate" format="dd/MM/yyyy"/>
	                </td>
	            </ili:interactionRow>
	        </c:forEach>
	    </table>
	    <c:set var="paginatorInstance" value="${requestScope.annoucesListPaginator}" scope="request"/>
		<c:set var="paginatorAction" value="/Announces" scope="request"/>
		<c:set var="paginatorTile" value="listAnnounces" scope="request"/>
		<c:import url="/content/pagination/Pagination.jsp"/>

	</c:otherwise>
</c:choose>