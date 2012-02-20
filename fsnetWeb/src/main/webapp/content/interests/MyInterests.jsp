<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<fieldset class="fieldsetAppli">
  <legend class="legendHome"><bean:message key="interests.6"/></legend>
  <logic:present name="myInterestPaginator" scope="request">
    <table class="inLineTableDashBoardFieldset fieldsetTable"><tr><td>
    <c:choose>
        <c:when test="${not empty requestScope.myInterestPaginator.resultList}">
            <div class="cloud">
                <c:forEach var="interest" items="${requestScope.myInterestPaginator.resultList}">

                    <span class="tag">
                        <html:link action="/RemoveInterest">
                            <html:param name="removedInterestId" value="${interest.id}"/>
                            <img src="images/mini-delete.png" alt="delete"/>
                        </html:link>
                        <html:link action="/InterestInformations">
                            <html:param name="infoInterestId" value="${interest.id}"/>
                            ${interest.name}
                        </html:link>

                    </span>
                </c:forEach>
            </div>
            <div style="clear : both;"></div>
        </c:when>
        <c:otherwise>
            <bean:message key="interests.8"/>
        </c:otherwise>
    </c:choose>
    <c:set var="paginatorInstance" value="${requestScope.myInterestPaginator}" scope="request"/>
	<c:set var="paginatorAction" value="/DisplayInterests" scope="request"/>
	<c:set var="paginatorTile" value="display" scope="request"/>
	<c:import url="/content/pagination/Pagination.jsp"/>
  </td></tr></table>
  </logic:present>
</fieldset>
