<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<h3><bean:message key="consultations.manage"/></h3>

<c:choose>
	<c:when test="${not empty requestScope.consultationsListPaginator.resultList}">
		<ul>
			<c:forEach var="consultation" items="${requestScope.consultationsListPaginator.resultList}">
			<li>
				<html:link action="/DisplayAConsultation?id=${consultation.id }">${consultation.title }</html:link>
				(<bean:message key="consultation.createdAtDate" /> <bean:write name="consultation" property="creationDate" format="dd/MM/yyyy" />
				<bean:message key="consultation.createdAtHour" /> <bean:write name="consultation" property="creationDate" format="HH:mm" />)
				- <html:link action="/DeleteAConsultation?id=${consultation.id}"><bean:message key="consultation.delete" /></html:link>
			</li>
			</c:forEach>
		</ul>
		<c:set var="paginatorInstance" value="${consultationsListPaginator}" scope="request"/>
		<c:set var="paginatorAction" value="/DisplayConsultations" scope="request"/>
		<c:set var="paginatorTile" value="listConsultations" scope="request"/>
		<c:import url="/content/pagination/Pagination.jsp"/>
	</c:when>
	<c:otherwise>
		<p><bean:message key="consultations.noResult"/>.</p>
	</c:otherwise>
</c:choose>
