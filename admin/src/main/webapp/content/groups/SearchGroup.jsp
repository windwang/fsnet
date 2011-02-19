<%--
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
 <c:when test="${not empty requestScope.membersListPaginator.resultList}">
 </c:when>
 <c:otherwise>
  <bean:message key="groups.noResult"/>
 </c:otherwise>
</c:choose>

