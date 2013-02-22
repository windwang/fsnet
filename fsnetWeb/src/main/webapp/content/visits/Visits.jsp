<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>




<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="visits.conts" />
	</legend>


	<c:if test="${empty requestScope.lastVisitors}">
		<table class="inLineTable tableStyle">
			<tr>
				<td><s:text name="visits.voidlist" />.</td>
			</tr>
		</table>
	</c:if>


	<c:if test="${not empty requestScope.lastVisitors}">

		<script type="text/javascript">
			$(document).ready(
					function pagination() {
						var nomTable = "tablelastvisitors";
						var idColonneATrier = 3;
						var sensDeTri = "desc";
						var aoColumns = [ {
							"bSortable" : false
						}, null, null, {
							"sType" : "date-euro"
						} ];
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, false, 5);
					});
		</script>

		<table id="tablelastvisitors"
			class="tablesorter inLineTable tableStyle">
			<thead>
				<tr>
					<th><s:text name="privatemessages.from" /></th>
					<th width="20%"><s:text name="members.firstName" /></th>
					<th width="20%"><s:text name="members.name" /></th>
					<th width="40%"><s:text name="privatemessages.date" /></th>
				</tr>
			</thead>
			<tbody>

				<c:forEach var="visitor" items="${requestScope.lastVisitors}">
					<tr>
						<td class="miniatureContainer"><ili:getMiniature
								socialEntity="${visitor.visitor}" /></td>
						<td><ili:getSocialEntityInfosFirstname
								socialEntity="${visitor.visitor}" /></td>
						<td><ili:getSocialEntityInfosName
								socialEntity="${visitor.visitor}" /></td>
						<td>
						<s:property value="lastVisite" />
<!-- 						<bean:write name="visitor" property="lastVisite" formatKey="date.format" /> -->
								</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="visits.old" />
	</legend>


	<c:if test="${empty requestScope.beforeLastVisitors}">
		<table class="inLineTable tableStyle">
			<tr>
				<td><s:text name="visits.voidlist" />.</td>
			</tr>
		</table>
	</c:if>

	<c:if test="${not empty requestScope.beforeLastVisitors}">

		<script type="text/javascript">
			$(document).ready(
					function pagination() {
						var nomTable = "tablelastvisitorsBeforeLastConnection";
						var idColonneATrier = 3;
						var sensDeTri = "desc";
						var aoColumns = [ {
							"bSortable" : false
						}, null, null, {
							"sType" : "date-euro"
						} ];
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, false, 5);
					});
		</script>

		<table id="tablelastvisitorsBeforeLastConnection"
			class="tablesorter inLineTable tableStyle">
			<thead>
				<tr>
					<th><s:text name="privatemessages.from" /></th>
					<th width="20%"><s:text name="members.firstName" /></th>
					<th width="20%"><s:text name="members.name" /></th>
					<th width="40%"><s:text name="privatemessages.date" /></th>
				</tr>
			</thead>
			<tbody>

				<c:forEach var="visitor" items="${requestScope.beforeLastVisitors}">
					<tr>
						<td class="miniatureContainer"><ili:getMiniature
								socialEntity="${visitor.visitor}" /></td>
						<td><ili:getSocialEntityInfosFirstname
								socialEntity="${visitor.visitor}" /></td>
						<td><ili:getSocialEntityInfosName
								socialEntity="${visitor.visitor}" /></td>
						<td>
						<s:property value="lastVisite" />
<!-- 								<bean:write name="visitor" property="lastVisite" formatKey="date.format" /> -->
								</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</fieldset>