<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3><bean:message key="group.listConsultations"/></h3>
 <c:choose>
    <c:when test="${! empty requestScope.groupsListPaginator.resultList}">
        <table  class="inLineTable">
            <c:forEach var="group" items="${requestScope.groupsListPaginator.resultList}">
               	<tr>
					<td>		                 
						<html:link action="">${group.name }<html:param name="id" value="${group.id}"></html:param></html:link>
	                </td> 
	                <td>
	                    <bean:message key="groups.by"/>
	                   	<ili:getSocialEntityInfos socialEntity="${group.masterGroup}"/>
	                 </td>
				</tr>
            </c:forEach>
        </table>
        						
						
        <!-- To be continued
        
        <c:set var="paginatorInstance" value="${requestScope.groupListPaginator.resultList}" scope="request"/>
        <c:set var="paginatorAction" value="/DisplayConsultations" scope="request"/>
		<c:set var="paginatorTile" value="searchConsultation" scope="request"/>
		<c:import url="/content/pagination/Pagination.jsp"/>-->
    </c:when>
    <c:otherwise>
        <p><bean:message key="group.noResult"/>.</p>
    </c:otherwise>
</c:choose>