<%--
 Author : SAID Mohamed
 Author : BOURAGBA Mohamed
 --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<h3><bean:message key="groups.search"/></h3>
<html:form action="SearchGroup">
    <div id="SearchGroup">
        <html:text property="searchText" />
        <html:submit styleClass="button"><bean:message key="groups.searchButton"/></html:submit>
    </div>
</html:form>

<h3><bean:message key="groups.listGroups"/></h3>

<c:choose>
<c:when test="${not empty requestScope.groupsListPaginator.resultList}">
	<table  class="inLineTable">
        <c:forEach var="group" items="${requestScope.groupsListPaginator.resultList}">
            <tr class="content">
                <td>
                	<html:link action="/DisplayGroup">${group.name} 
                		<html:param name="idGroup" value="${group.id}"/>
                	</html:link>
                </td>
                  <td class="tableButton">
                  	<html:link action="/SwitchStateGroup" styleClass="button">
                  		<html:param name="groupSelected" value="${group.id}"/>
                  	    <c:choose>
                  			<c:when test="${group.isEnabled}">
                  				<bean:message key="members.searchDisable"/>
	                   		</c:when>
    	               		<c:otherwise>
                				<bean:message key="members.searchEnable"/>
	                    	</c:otherwise>
    	                </c:choose>
                  	 </html:link>            	
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:set var="paginatorInstance" value="${requestScope.groupsListPaginator}" scope="request"/>
	<c:set var="paginatorAction" value="/GroupList" scope="request"/>
	<c:set var="paginatorTile" value="groupsList" scope="request"/>
	<c:import url="/content/pagination/Pagination.jsp"/>
</c:when>
<c:otherwise>
	<bean:message key="members.noResult"/>
</c:otherwise>
</c:choose>

