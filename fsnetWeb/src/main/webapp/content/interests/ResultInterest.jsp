<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<s:if test="%{interestSearchPaginator != null}">

	<table class="inLineTable fieldsetTableAppli">
		<tr>
			<td><c:choose>
					<c:when
						test="${not empty requestScope.interestSearchPaginator.resultList}">
						<div class="cloud">
							<c:forEach var="interest"
								items="${requestScope.interestSearchPaginator.resultList}">
								<div>
									<s:a action="/InterestInformations">
										<s:param name="infoInterestId" value="%{interest.id}" />
	                		${interest.name}
	                	</s:a>
								</div>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						<s:text name="interests.search.empty" />
					</c:otherwise>
				</c:choose></td>
		</tr>
	</table>
	<c:set var="paginatorInstance"
		value="${requestScope.interestSearchPaginator}" scope="request" />
	<c:set var="paginatorAction" value="/SearchInterest" scope="request" />
	<c:set var="paginatorTile" value="search" scope="request" />
	<c:import url="/content/pagination/Pagination.jsp" />
</s:if>
