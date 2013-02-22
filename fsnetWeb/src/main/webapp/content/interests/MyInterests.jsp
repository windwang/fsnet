<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="interests.title.my" />
	</legend>
	
<s:if test="%{myInterestPaginator != null}">
		<table class="inLineTable tableStyle">
			<tr>
				<td><c:choose>
						<c:when
							test="${not empty requestScope.myInterestPaginator.resultList}">
							<div class="cloud">
								<c:forEach var="interest"
									items="${requestScope.myInterestPaginator.resultList}">

									<span class="tag"> <s:a action="/RemoveInterest">
											<s:param name="removedInterestId" value="%{interest.id}" />
											<img src="images/mini-delete.png" alt="delete" />
										</s:a> <s:a action="/InterestInformations">
											<s:param name="infoInterestId" value="%{interest.id}" />
                            ${interest.name}
                        </s:a>

									</span>
								</c:forEach>
							</div>
							<div style="clear: both;"></div>
						</c:when>
						<c:otherwise>
							<s:text name="interests.search.empty" />
						</c:otherwise>
					</c:choose> <c:set var="paginatorInstance"
						value="${requestScope.myInterestPaginator}" scope="request" /> <c:set
						var="paginatorAction" value="/DisplayInterests" scope="request" />
					<c:set var="paginatorTile" value="display" scope="request" /> <c:import
						url="/content/pagination/Pagination.jsp" /></td>
			</tr>
		</table>
	</s:if>
</fieldset>
