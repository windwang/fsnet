<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<c:choose>
    <c:when test="${not empty requestScope.interestResult}">
        <div class="cloud">
            <c:forEach var="interest" items="${requestScope.interestResult}">
                <div>
                	<html:link action="/InterestInformations">
                		<html:param name="infoInterestId" value="${interest.id}"/>
                		${interest.name}
                	</html:link>
                </div>
            </c:forEach>
        </div>
        <logic:present name="currentSearch" scope="request">
	        <logic:present name="currentPage" scope="request">
				<c:if test="${requestScope.currentPage > 0}">
					<html:link styleClass="button" action="/SearchInterest">
						<html:param name="nextPage" value="${requestScope.currentPage-1}"/>
					 	<html:param name="search" value="${requestScope.currentSearch}"/>
						<bean:message key="interests.12"/>
					</html:link>
				</c:if>
				<logic:present name="hasnext" scope="request">
					<html:link styleClass="button" action="/SearchInterest">
						<html:param name="nextPage" value="${requestScope.currentPage+1}"/>
					 	<html:param name="search" value="${requestScope.currentSearch}"/>
						<bean:message key="interests.13"/>
					</html:link>
				</logic:present>
			</logic:present>
		</logic:present>
    </c:when>
    <c:otherwise>
        <bean:message key="interests.10"/>
    </c:otherwise>
</c:choose>
