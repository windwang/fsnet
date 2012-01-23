<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3><bean:message key="consultations.manage"/></h3>

<c:choose>
	<c:when test="${not empty requestScope.consultationsListPaginator.resultList}">
		<table  class="inLineTable">
			<c:forEach var="consultation" items="${requestScope.consultationsListPaginator.resultList}">
			<tr>
				<td>
					<c:import url="/FavoriteFragment.do">
	                     <c:param name="interactionId" value="${consultation.id}"/>
	                 </c:import>
	                 
					<html:link action="/DisplayAConsultation?id=${consultation.id }">${consultation.title }</html:link>
					(<bean:message key="consultation.createdAtDate" /> <bean:write name="consultation" property="creationDate" format="dd/MM/yyyy" />
					<bean:message key="consultation.createdAtHour" /> <bean:write name="consultation" property="creationDate" format="HH:mm" />)
                </td>
                <td>
                     <bean:message key="consultation.by"/>
                   	<ili:getSocialEntityInfos socialEntity="${consultation.creator}"/>
                 </td>
				 <td class="tableButton" onclick="confirmDelete2(${consultation.id}	)">
				 	<html:form action="/DeleteAConsultation" method="post" styleId="${consultation.id}" styleClass="cursorPointer">
				 		<html:hidden property="id" value="${consultation.id}" />
					 	
				  		<span class="button">
						    <bean:message key="consultation.delete" />
						</span>
					</html:form>
				 </td>
			</tr>
			</c:forEach>
		</table>
		<c:set var="paginatorInstance" value="${consultationsListPaginator}" scope="request"/>
		<c:set var="paginatorAction" value="/DisplayConsultations" scope="request"/>
		<c:set var="paginatorTile" value="listConsultations" scope="request"/>
		<c:import url="/content/pagination/Pagination.jsp"/>
	</c:when>
	<c:otherwise>
		<p><bean:message key="consultations.noResult"/>.</p>
	</c:otherwise>
</c:choose>
