<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<h3><bean:message key="interests.6"/></h3>
<logic:present name="myInterestPaginator" scope="request">
    <c:choose>
        <c:when test="${not empty requestScope.myInterestPaginator.resultList}">
            <div class="cloud">
                <c:forEach var="interest" items="${requestScope.myInterestPaginator.resultList}">

                <span class="tag">
                        <html:link action="/RemoveInterest">
                            <html:param name="removedInterestId" value="${interest.id}"/>
                            <img src="images/mini-delete.png" />
                        </html:link>
                        <html:link action="/InterestInformations">
                            <html:param name="infoInterestId" value="${interest.id}"/>
                            ${interest.name}
                        </html:link>

                </span>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <bean:message key="interests.8"/>
        </c:otherwise>
    </c:choose>
    <c:if test="${requestScope.myInterestPaginator.hasPreviousPage}">
        <html:link styleClass="button" action="/DisplayInterests">
            <html:param name="pageId" value="${requestScope.myInterestPaginator.previousPage}"/>
            <html:param name="tileId" value="display"/>
            <bean:message key="interests.12"/>
        </html:link>
    </c:if>
    <c:if test="${requestScope.myInterestPaginator.numPages > 1}">
        <c:forEach var="page" begin="0" end="${requestScope.myInterestPaginator.numPages-1}">
            <html:link styleClass="button" action="/DisplayInterests">
                <html:param name="pageId" value="${page}"/>
                <html:param name="tileId" value="display"/>
                <c:choose>
                    <c:when test="${page == requestScope.myInterestPaginator.requestedPage}">
                        <u>${page}</u>
                    </c:when>
                    <c:otherwise>
                        ${page}
                    </c:otherwise>
                </c:choose>
            </html:link>
        </c:forEach>
    </c:if>
    <c:if test="${requestScope.myInterestPaginator.hasNextPage}">
        <html:link styleClass="button" action="/DisplayInterests">
            <html:param name="pageId" value="${requestScope.myInterestPaginator.nextPage}"/>
           <html:param name="tileId" value="display"/>
            <bean:message key="interests.13"/>
        </html:link>
    </c:if>
</logic:present>
