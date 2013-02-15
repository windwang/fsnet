<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<bean:define id="searchMessage">
	<bean:message key="consultations.placeholder.search" />
</bean:define>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="consultations.title.search" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td><html:form action="SearchConsultation" method="get">
					<div id="SearchConsultation">
						<html:text property="searchText" styleId="searchTexte" styleClass="search-query"/>
						<ili:placeHolder id="searchTexte" value="${searchMessage}" />
						<html:submit styleClass="btn btn-inverse">
							<bean:message key="consultations.button.search" />
						</html:submit>
					</div>
				</html:form></td>
		</tr>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="consultations.title.listConsultations" />
	</legend>
	<c:choose>
		<c:when test="${! empty requestScope.consultationsSearchList}">

			<script type="text/javascript">
	$(document).ready(function pagination() {
		var nomTable = "searchConsults";
		var idColonneATrier = 2;
		var sensDeTri = "desc";
		var aoColumns = [ {
			"bSortable" : false
		}, null, {
			"sType" : "date-euro"
		} ,{
			"bSortable" : false
		}, null, null, {
			"bSortable" : false
		}];
		miseEnPageTable(nomTable, idColonneATrier, sensDeTri, aoColumns, false, 10);
	});
</script>
			<table id="searchConsults" class="tablesorter inLineTable tableStyle">
				<thead>
					<tr>
						<th width="5%"></th>
						<th width="25%"><bean:message
								key="tableheader.consultationname" /></th>
						<th width="20%"><bean:message
								key="consultations.createdAtDate" /></th>
						<th><bean:message key="tableheader.by" /></th>
						<th><bean:message key="members.firstName" /></th>
						<th><bean:message key="members.name" /></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="consultation"
						items="${requestScope.consultationsSearchList}">
						<ili:interactionRow
							unreadInteractionsId="${requestScope.unreadInteractionsId}"
							currentInteractionId="${consultation.id}">
							<td><c:import url="/FavoriteFragment.do">
									<c:param name="interactionId" value="${consultation.id}" />
								</c:import></td>
							<td><html:link
									action="/DisplayAConsultation?id=${consultation.id }">${consultation.title }</html:link>
							<td><bean:write name="consultation" property="creationDate"
									formatKey="date.format" /></td>
							<td></td>
							<td><ili:getSocialEntityInfosFirstname
									socialEntity="${consultation.creator}" /></td>
							<td><ili:getSocialEntityInfosName
									socialEntity="${consultation.creator}" /></td>
							<td class="tableButton"
								onclick="confirmDelete2(${consultation.id},	'<bean:message key="message.confirmation.delete" />');"><c:if
									test="${sessionScope.userId eq consultation.creator.id}">
									<html:form action="/DeleteAConsultation" method="post"
										styleId="${consultation.id}" styleClass="cursorPointer">
										<html:hidden property="id" value="${consultation.id}" />
										<span class="btn btn-inverse"> <bean:message
												key="consultations.button.delete" />
										</span>
									</html:form>
								</c:if></td>
						</ili:interactionRow>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<table class="inLineTable tableStyle">
				<tr>
					<td><bean:message key="consultations.noResult" />.</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>