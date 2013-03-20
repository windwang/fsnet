<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<logic:present name="addInterestPaginator" scope="request">
	<fieldset class="fieldsetCadre">
		<legend>
			<bean:message key="interests.title.add" />
		</legend>
		<table class="inLineTable tableStyle">
			<tr>
				<td><c:choose>
						<c:when
							test="${not empty requestScope.addInterestPaginator.resultList}">
							<html:javascript formName="/AddInterest" />
							<c:forEach var="interest"
								items="${requestScope.addInterestPaginator.resultList}">
								<!-- <div class="otag"> !-->
								<div class="label label-success">
									<html:link action="/AddInterest">
										<i class="icon-plus icon-white"></i>
										<html:param name="addedInterestId" value="${interest.id}" />
									</html:link>
									<html:link action="/InterestInformations">
										<html:param name="infoInterestId" value="${interest.id}" />${interest.name}
									</html:link>
								</div>
							</c:forEach>
							<div style="clear: both;"></div>
							<div class="errorMessage">
								<html:errors property="addedInterestId" />
							</div>
						</c:when>
						<c:otherwise>
							<bean:message key="interests.allAvailable" />
						</c:otherwise>
					</c:choose> <c:set var="paginatorInstance"
						value="${requestScope.addInterestPaginator}" scope="request" /> <c:set
						var="paginatorAction" value="/DisplayInterests" scope="request" />
					<c:set var="paginatorTile" value="addInterest" scope="request" />
					<c:import url="/content/pagination/Pagination.jsp" /></td>
			</tr>
		</table>
	</fieldset>
</logic:present>

