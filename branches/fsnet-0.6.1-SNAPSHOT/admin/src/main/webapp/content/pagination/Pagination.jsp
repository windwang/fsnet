<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<c:set var="previousLinkTitle">
	<bean:message key="pagination.previous.title"/>
</c:set>
<c:set var="nextLinkTitle">
	<bean:message key="pagination.next.title"/>
</c:set>

<div class="paginator">
    <c:if test="${paginatorInstance.hasPreviousPage}">
        <html:link styleClass="paginatorPagePrevious" action="${paginatorAction}" title="${previousLinkTitle}">
            <html:param name="pageId" value="${paginatorInstance.previousPage}"/>
            <html:param name="tileId" value="${paginatorTile}"/>
            <html:param name="${paginatorInstance.requestInputName}" value="${paginatorInstance.requestInput}"/>
            <bean:message key="pagination.previous"/>
        </html:link>
    </c:if>


    <c:if test="${paginatorInstance.numPages > 1}">
        <c:forEach var="page" begin="0" end="${paginatorInstance.numPages-1}">
            <html:link styleClass="paginatorPageId" action="${paginatorAction}" title="page ${page}">
                <html:param name="pageId" value="${page}"/>
                <html:param name="tileId" value="${paginatorTile}"/>
                <html:param name="${paginatorInstance.requestInputName}" value="${paginatorInstance.requestInput}"/>
                <c:choose>
                    <c:when test="${page == paginatorInstance.requestedPage}">
                        <u>${page+1}</u>
                    </c:when>
                    <c:otherwise>
                        ${page+1}
                    </c:otherwise>
                </c:choose>
            </html:link>
        </c:forEach>
    </c:if>

    <c:if test="${paginatorInstance.hasNextPage}">
        <html:link styleClass="paginatorPageNext" action="${paginatorAction}" title="${nextLinkTitle}">
            <html:param name="pageId" value="${paginatorInstance.nextPage}"/>
            <html:param name="tileId" value="${paginatorTile}"/>
            <html:param name="${paginatorInstance.requestInputName}" value="${paginatorInstance.requestInput}"/>
            <bean:message key="pagination.next"/>
        </html:link>
    </c:if>


</div>