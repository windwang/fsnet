<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<bean:define id="searchMessage"><bean:message key="consultation.search"/></bean:define>

<h3><bean:message key="consultation.searchConsultation"/></h3>
<table  class="inLineTable"><tr><td>
<html:form action="SearchConsultation" method="GET">
    <div id="SearchCommunity">
        <html:text styleId="searchTexte" property="searchText" />
        <ili:placeHolder id="searchTexte" value="${searchMessage}" />	
        <html:submit styleClass="button"><bean:message key="consultation.searchButton"/></html:submit>
    </div>
</html:form>
</td></tr></table>

<h3><bean:message key="consultation.listConsultations"/></h3>
<c:choose>
    <c:when test="${! empty requestScope.consultationsSearchListPaginator.resultList}">
        <table  class="inLineTable">
            <c:forEach var="consultation" items="${requestScope.consultationsSearchListPaginator.resultList}">
               	<ili:interactionRow unreadInteractionsId="${requestScope.unreadInteractionsId}" currentInteractionId="${consultation.id}">
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
					 	<c:if test="${sessionScope.userId eq consultation.creator.id}">
						 	<html:form action="/DeleteAConsultation" method="post" styleId="${consultation.id}" styleClass="cursorPointer">
						 		<html:hidden property="id" value="${consultation.id}" />
							 	
						  		<span class="button">
								    <bean:message key="consultation.delete" />
								</span>
							</html:form>
						</c:if>
					 </td>
				</ili:interactionRow>
            </c:forEach>
        </table>
        						
						
        <c:set var="paginatorInstance" value="${requestScope.consultationsSearchListPaginator}" scope="request"/>
        <c:set var="paginatorAction" value="/DisplayConsultations" scope="request"/>
		<c:set var="paginatorTile" value="searchConsultation" scope="request"/>
		<c:import url="/content/pagination/Pagination.jsp"/>
    </c:when>
    <c:otherwise>
      <table  class="inLineTable"><tr><td>
        <bean:message key="consultations.noResult"/>.
      </td></tr></table>
    </c:otherwise>
</c:choose>