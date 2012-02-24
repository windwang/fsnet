<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetAppli">
  <legend class="legendHome"><bean:message key="announce.title.list" />s</legend>

<c:choose>
	<c:when test="${empty requestScope.annoucesList}">
		<table class="inLineTableDashBoardFieldset fieldsetTable">
			<tr>
				<td><bean:message key="announce.emptyList" /></td>
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
						}, null,{
							"bSortable" : false
						}, null, null, {
							"sType" : "date-euro"
						} ];
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, false);

					});
		</script>
		<table id="eventsTable" class="tablesorter inLineTableDashBoardFieldset fieldsetTable">
			<thead>
				<tr>
					<th></th>
					<th><bean:message key="tableheader.announcename" /></th>
					<th><bean:message key="tableheader.by" /></th>
					<th><bean:message key="members.firstName" /></th>
					<th><bean:message key="members.name" /></th>
					<th><bean:message key="tableheader.expirdate" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="announce" items="${requestScope.annoucesList}">
					<ili:interactionRow
						unreadInteractionsId="${requestScope.unreadInteractionsId}"
						currentInteractionId="${announce.id}">
						<bean:define id="idAnnounce" name="announce" property="id" />
						<td><c:import url="/FavoriteFragment.do">
								<c:param name="interactionId" value="${announce.id}" />
							</c:import></td>
						<td><html:link action="/DisplayAnnounce.do"
								paramId="idAnnounce" paramName="idAnnounce">
								<bean:write name="announce" property="title" />
							</html:link></td>
						<td></td>
						<td><ili:getSocialEntityInfosFirstname
								socialEntity="${announce.creator}" /></td>
						<td><ili:getSocialEntityInfosName
								socialEntity="${announce.creator}" /></td>
						<td><bean:write name="announce" property="endDate"
								format="dd/MM/yyyy HH:mm" /></td>
					</ili:interactionRow>
				</c:forEach>
			</tbody>
		</table>

	</c:otherwise>
  </c:choose>
</fieldset>
