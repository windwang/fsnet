<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<logic:present name="interestSearchPaginator" scope="request">
	<c:choose>
	    <c:when test="${not empty requestScope.interestSearchPaginator.resultList}">
	        <div class="cloud">
	            <c:forEach var="interest" items="${requestScope.interestSearchPaginator.resultList}">
	                <div>
	                	<html:link action="/InterestInformations">
	                		<html:param name="infoInterestId" value="${interest.id}"/>
	                		${interest.name}
	                	</html:link>
	                </div>
	            </c:forEach>
	        </div>
	    </c:when>
	    <c:otherwise>
	        <bean:message key="interests.10"/>
	    </c:otherwise>
	</c:choose>
	<c:if test="${requestScope.interestSearchPaginator.hasPreviousPage}">
		<html:link styleClass="button" action="/SearchInterest">
			<html:param name="pageId" value="${requestScope.interestSearchPaginator.previousPage}"/>
			<html:param name="tileId" value="search"/>
		 	<html:param name="requestInput" value="${requestScope.interestSearchPaginator.requestInput}"/>
			<bean:message key="interests.12"/>
		</html:link>
	</c:if>
	<c:if test="${requestScope.interestSearchPaginator.numPages > 1}">
		<c:forEach var="page" begin="0" end="${requestScope.interestSearchPaginator.numPages-1}">
			<html:link styleClass="button" action="/SearchInterest">
				<html:param name="pageId" value="${page}"/>
			 	<html:param name="tileId" value="search"/>
			 	<html:param name="requestInput" value="${requestScope.interestSearchPaginator.requestInput}"/>
				<c:choose>
					<c:when test="${page == requestScope.interestSearchPaginator.requestedPage}">
						<u>${page}</u>
					</c:when>
					<c:otherwise>
						${page}
					</c:otherwise>
				</c:choose>
			</html:link>
		</c:forEach>
	</c:if>
	<c:if test="${requestScope.interestSearchPaginator.hasNextPage}">
		<html:link styleClass="button" action="/SearchInterest">
			<html:param name="pageId" value="${requestScope.interestSearchPaginator.nextPage}"/>
		 	<html:param name="tileId" value="search"/>
		 	<html:param name="requestInput" value="${requestScope.interestSearchPaginator.requestInput}"/>
			<bean:message key="interests.13"/>
		</html:link>
	</c:if>
</logic:present>

