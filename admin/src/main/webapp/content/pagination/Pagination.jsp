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
        <s:a styleClass="paginatorPagePrevious" action="${paginatorAction}" title="${previousLinkTitle}">
            <html:param name="pageId" value="${paginatorInstance.previousPage}"/>
            <html:param name="tileId" value="${paginatorTile}"/>
            <html:param name="${paginatorInstance.requestInputName}" value="${paginatorInstance.requestInput}"/>
            <bean:message key="pagination.previous"/>
        </s:a>
    </c:if>

    <c:if test="${paginatorInstance.numPages > 1}">
        <c:forEach var="page" begin="0" end="${paginatorInstance.numPages-1}">
            <s:a styleClass="paginatorPageId" action="${paginatorAction}" title="page ${page+1}">
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
            </s:a>
        </c:forEach>
    </c:if>

    <c:if test="${paginatorInstance.hasNextPage}">
        <s:a styleClass="paginatorPageNext" action="${paginatorAction}" title="${nextLinkTitle}">
            <html:param name="pageId" value="${paginatorInstance.nextPage}"/>
            <html:param name="tileId" value="${paginatorTile}"/>
            <html:param name="${paginatorInstance.requestInputName}" value="${paginatorInstance.requestInput}"/>
            <bean:message key="pagination.next"/>
        </s:a>
    </c:if>
</div>