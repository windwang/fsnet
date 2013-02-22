<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<c:set var="previousLinkTitle">
	<s:text name="pagination.previous.title" />
</c:set>
<c:set var="nextLinkTitle">
	<s:text name="pagination.next.title" />
</c:set>

<div class="paginator">
	<c:if test="${paginatorInstance.hasPreviousPage}">
		<s:a cssClass="paginatorPagePrevious"
			href="%{paginatorAction}" title="%{previousLinkTitle}">
			<s:param name="pageId" value="%{paginatorInstance.previousPage}" />
			<s:param name="tileId" value="%{paginatorTile}" />
			<s:param name="%{paginatorInstance.requestInputName}"
				value="%{paginatorInstance.requestInput}" />
			<s:text name="pagination.previous" />
		</s:a>
	</c:if>


	<c:if test="${paginatorInstance.numPages > 1}">
		<c:forEach var="page" begin="0" end="${paginatorInstance.numPages-1}">
			<s:a styleClass="paginatorPageId" href="%{paginatorAction}"
				title="page %{page+1}">
				<s:param name="pageId" value="%{page}" />
				<s:param name="tileId" value="%{paginatorTile}" />
				<s:param name="%{paginatorInstance.requestInputName}"
					value="%{paginatorInstance.requestInput}" />
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
		<s:a styleClass="paginatorPageNext" href="%{paginatorAction}"
			title="%{nextLinkTitle}">
			<s:param name="pageId" value="%{paginatorInstance.nextPage}" />
			<s:param name="tileId" value="%{paginatorTile}" />
			<s:param name="%{paginatorInstance.requestInputName}"
				value="%{paginatorInstance.requestInput}" />
			<s:textfield name="pagination.next" />
		</s:a>
	</c:if>


</div>