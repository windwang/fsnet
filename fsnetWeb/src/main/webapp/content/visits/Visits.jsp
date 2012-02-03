<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>



<h3><bean:message key="visits.conts"/></h3>

<c:if
    test="${empty requestScope.lastVisitors.resultList}">
    <table class="inLineTable"><tr><td>
      <bean:message key="visits.voidlist" />.
    </td></tr></table>
</c:if>


<c:if test="${not empty requestScope.lastVisitors.resultList}">
    <table class="inLineTable">

        <c:forEach var="visitor" items="${requestScope.lastVisitors.resultList}">
               <tr>
                <td class="miniatureContainer">
                    <ili:getMiniature socialEntity="${visitor.visitor}"/>
                </td>
                <td>
                    <ili:getSocialEntityInfos socialEntity="${visitor.visitor}"/>
                </td>
                <td>
                    <bean:write name="visitor" property="lastVisite" formatKey="date.format" />
                </td>
            </tr>
        </c:forEach>

    </table>
    <c:set var="paginatorInstance" value="${requestScope.lastVisitors}" scope="request"/>
	<c:set var="paginatorAction" value="/Visits" scope="request"/>
	<c:set var="paginatorTile" value="lastVisitors" scope="request"/>
	<c:import url="/content/pagination/Pagination.jsp"/>
</c:if>
 
