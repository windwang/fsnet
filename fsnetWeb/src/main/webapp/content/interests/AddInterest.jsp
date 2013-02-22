<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="%{addInterestPaginator != null}">
	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="interests.title.create" />
		</legend>
		<table class="inLineTable tableStyle">
			<tr>
				<td><c:choose>
						<c:when
							test="${not empty requestScope.addInterestPaginator.resultList}">
							<html:javascript formName="/AddInterest" />
							
							<c:forEach var="interest"
								items="${requestScope.addInterestPaginator.resultList}">
								<div class="otag">
									<s:a action="/AddInterest">
										<img src="images/add.png" alt="add" />
										<s:param name="addedInterestId" value="%{interest.id}" />
									</s:a>
									<s:a action="/InterestInformations">
										<s:param name="infoInterestId" value="%{interest.id}" />${interest.name}
									</s:a>
								</div>
							</c:forEach>
							<div style="clear: both;"></div>

						</c:when>
						<c:otherwise>
							<s:text name="interests.allAvailable" />
						</c:otherwise>
					</c:choose> <c:set var="paginatorInstance"
						value="${requestScope.addInterestPaginator}" scope="request" /> <c:set
						var="paginatorAction" value="/DisplayInterests" scope="request" />
					<c:set var="paginatorTile" value="addInterest" scope="request" />
					<c:import url="/content/pagination/Pagination.jsp" /></td>
			</tr>
		</table>
	</fieldset>
</s:if>

