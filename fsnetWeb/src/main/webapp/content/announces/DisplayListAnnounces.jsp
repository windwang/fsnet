<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="announce.title.list" />
	</legend>

	<c:choose>
		<c:when test="${empty requestScope.annoucesList}">
			<table class="inLineTable tableStyle">
				<tr>
					<td><s:text name="announce.emptyList" /></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
				$(document).ready(
						function pagination() {
							var nomTable = "eventsTable";
							var idColonneATrier = 5;
							var sensDeTri = "desc";
							var aoColumns = [ {
								"bSortable" : false
							}, null, {
								"bSortable" : false
							}, null, null, {
								"sType" : "date-euro"
							} ];
							miseEnPageTable(nomTable, idColonneATrier,
									sensDeTri, aoColumns, false, 10);

						});
			</script>
			<table id="eventsTable"
				class="tablesorter inLineTable tableStyle">
				<thead>
					<tr>
						<th></th>
						<th><s:text name="tableheader.announcename" /></th>
						<th><s:text name="tableheader.by" /></th>
						<th><s:text name="members.firstName" /></th>
						<th><s:text name="members.name" /></th>
						<th><s:text name="tableheader.expirdate" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="announce" items="${requestScope.annoucesList}">
						<ili:interactionRow
							unreadInteractionsId="${requestScope.unreadInteractionsId}"
							currentInteractionId="${announce.id}">
							<s:set id="idAnnounce" name="announce" var="id" />
							<td><c:import url="/FavoriteFragment.do">
									<c:param name="interactionId" value="${announce.id}" />
								</c:import></td>
							<td><s:a action="/DisplayAnnounce.do"
									paramId="idAnnounce" paramName="idAnnounce">
									<s:property default="title" value="announce" />
								</s:a></td>
							<td></td>
							<td><ili:getSocialEntityInfosFirstname
									socialEntity="${announce.creator}" /></td>
							<td><ili:getSocialEntityInfosName
									socialEntity="${announce.creator}" /></td>
							<td><s:property value="announce" default="endDate"
									format="dd/MM/yyyy HH:mm" /></td>
						</ili:interactionRow>
					</c:forEach>
				</tbody>
			</table>

		</c:otherwise>
	</c:choose>
</fieldset>
