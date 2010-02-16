<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<logic:present name="addInterestPaginator" scope="request">
	<h3><bean:message key="interests.0"/></h3>
	<c:choose>
	    <c:when test="${not empty requestScope.addInterestPaginator.resultList}">
	        <html:javascript formName="/AddInterest"/>
	        <div class="cloud">
	            <c:forEach var="interest" items="${requestScope.addInterestPaginator.resultList}">
	                <span class="otag">
	                    <html:link action="/AddInterest">
	                        <img src="images/add.png"/>
	                        <html:param name="addedInterestId" value="${interest.id}"/>
	                    </html:link>
	                    <html:link action="/InterestInformations">
	                        <html:param name="infoInterestId" value="${interest.id}"/>
	                        ${interest.name}
	                    </html:link>
	                </span>
	            </c:forEach>
	        </div>
	        <html:errors property="addedInterestId"/>
	    </c:when>
	    <c:otherwise>
	        <bean:message key="interests.2"/>
	    </c:otherwise>
	</c:choose>
	<c:if test="${requestScope.addInterestPaginator.hasPreviousPage}">
        <html:link styleClass="button" action="/DisplayInterests">
            <html:param name="pageId" value="${requestScope.addInterestPaginator.previousPage}"/>
            <html:param name="tileId" value="addInterest"/>
            <bean:message key="interests.12"/>
        </html:link>
    </c:if>
    <c:if test="${requestScope.addInterestPaginator.numPages > 1}">
        <c:forEach var="page" begin="0" end="${requestScope.addInterestPaginator.numPages-1}">
            <html:link styleClass="button" action="/DisplayInterests">
                <html:param name="pageId" value="${page}"/>
                <html:param name="tileId" value="addInterest"/>
                <c:choose>
                    <c:when test="${page == requestScope.addInterestPaginator.requestedPage}">
                        <u>${page}</u>
                    </c:when>
                    <c:otherwise>
                        ${page}
                    </c:otherwise>
                </c:choose>
            </html:link>
        </c:forEach>
    </c:if>
    <c:if test="${requestScope.addInterestPaginator.hasNextPage}">
        <html:link styleClass="button" action="/DisplayInterests">
            <html:param name="pageId" value="${requestScope.addInterestPaginator.nextPage}"/>
           <html:param name="tileId" value="addInterest"/>
            <bean:message key="interests.13"/>
        </html:link>
    </c:if>
</logic:present>
