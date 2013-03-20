<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<s:text name="consultations.placeholder.search" var="searchMessage" />

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="consultations.title.search" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td><s:form action="SearchConsultation" method="get">
					<div id="SearchConsultation">
						<s:textfield property="searchText" styleId="searchTexte"
							styleClass="search-query" />
						<ili:placeHolder id="searchTexte" value="${searchMessage}" />
						<s:submit cssClass="btn btn-inverse" key="consultations.button.search"/>
					</div>
				</s:form></td>
		</tr>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="consultations.title.listConsultations" />
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
						<th width="25%"><s:text name="tableheader.consultationname" /></th>
						<th width="20%"><s:text name="consultations.createdAtDate" /></th>
						<th><s:text name="tableheader.by" /></th>
						<th><s:text name="members.firstName" /></th>
						<th><s:text name="members.name" /></th>
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
							<td><s:a href="/DisplayAConsultation?id=%{consultation.id }">${consultation.title }</s:a>
							<td><s:property value="creationDate" /></td>
							<td></td>
							<td><ili:getSocialEntityInfosFirstname
									socialEntity="${consultation.creator}" /></td>
							<td><ili:getSocialEntityInfosName
									socialEntity="${consultation.creator}" /></td>
							<td class="tableButton"
								onclick="confirmDelete2(${consultation.id},	'<s:text name="message.confirmation.delete" />');"><c:if
									test="${sessionScope.userId eq consultation.creator.id}">
									<s:form action="/DeleteAConsultation" method="post"
										styleId="${consultation.id}" styleClass="cursorPointer">
										<s:hidden name="id" value="%{consultation.id}" />
										<span class="btn btn-inverse"> <s:text
												name="consultations.button.delete" />
										</span>
									</s:form>
								</c:if></td>
						</ili:interactionRow>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<table class="inLineTable tableStyle">
				<tr>
					<td><s:text name="consultations.noResult" />.</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>