<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<c:if test="${paginatorInstance.hasPreviousPage}">
	<html:link styleClass="button" action="${paginatorAction}">
		<html:param name="pageId" value="${paginatorInstance.previousPage}"/>
		<html:param name="tileId" value="${paginatorTile}"/>
	 	<html:param name="requestInput" value="${paginatorInstance.requestInput}"/>
		<bean:message key="interests.12"/>
	</html:link>
</c:if>
<c:if test="${paginatorInstance.numPages > 1}">
	<c:forEach var="page" begin="0" end="${paginatorInstance.numPages-1}">
		<html:link styleClass="button" action="${paginatorAction}">
			<html:param name="pageId" value="${page}"/>
		 	<html:param name="tileId" value="${paginatorTile}"/>
		 	<html:param name="requestInput" value="${paginatorInstance.requestInput}"/>
			<c:choose>
				<c:when test="${page == paginatorInstance.requestedPage}">
					<u>${page}</u>
				</c:when>
				<c:otherwise>
					${page}
				</c:otherwise>
			</c:choose>
		</html:link>
	</c:forEach>
</c:if>
<c:if test="${paginatorInstance.hasNextPage}">
	<html:link styleClass="button" action="${paginatorAction}">
		<html:param name="pageId" value="${paginatorInstance.nextPage}"/>
	 	<html:param name="tileId" value="${paginatorTile}"/>
	 	<html:param name="requestInput" value="${paginatorInstance.requestInput}"/>
		<bean:message key="interests.13"/>
	</html:link>
</c:if>