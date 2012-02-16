<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>




<h3>
	<bean:message key="visits.conts" />
</h3>

<c:if test="${empty requestScope.lastVisitors}">
	<table class="inLineTable">
		<tr>
			<td><bean:message key="visits.voidlist" />.</td>
		</tr>
	</table>
</c:if>


<c:if test="${not empty requestScope.lastVisitors}">

	<script type="text/javascript">
		$(document).ready(
				function pagination() {
					var nomTable = "tablelastvisitors";
					var idColonneATrier = 2;
					var sensDeTri = "desc";
					var aoColumns = [ {
						"bSortable" : false
					}, null, {
						"sType" : "date-euro"
					} ];
					miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
							aoColumns, false);
				});
	</script>

	<table id="tablelastvisitors" class="tablesorter inLineTable">
		<thead>
			<tr>
				<th></th>
				<th width="40%"><bean:message key="privatemessages.from" /></th>
				<th width="40%"><bean:message key="privatemessages.date" /></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="visitor" items="${requestScope.lastVisitors}">
				<tr>
					<td class="miniatureContainer"><ili:getMiniature
							socialEntity="${visitor.visitor}" /></td>
					<td><ili:getSocialEntityInfos
							socialEntity="${visitor.visitor}" /></td>
					<td><bean:write name="visitor" property="lastVisite"
							formatKey="date.format" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
</c:if>

	<h3>
		<bean:message key="visits.old" />
	</h3>

<c:if test="${empty requestScope.beforeLastVisitors}">
	<table class="inLineTable">
		<tr>
			<td><bean:message key="visits.voidlist" />.</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty requestScope.beforeLastVisitors}">

	<script type="text/javascript">
		$(document).ready(
				function pagination() {
					var nomTable = "tablelastvisitorsBeforeLastConnection";
					var idColonneATrier = 2;
					var sensDeTri = "desc";
					var aoColumns = [ {
						"bSortable" : false
					}, null, {
						"sType" : "date-euro"
					} ];
					miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
							aoColumns, false);
				});
	</script>

	<table id="tablelastvisitorsBeforeLastConnection" class="tablesorter inLineTable">
		<thead>
			<tr>
				<th></th>
				<th width="40%"><bean:message key="privatemessages.from" /></th>
				<th width="40%"><bean:message key="privatemessages.date" /></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="visitor" items="${requestScope.beforeLastVisitors}">
				<tr>
					<td class="miniatureContainer"><ili:getMiniature
							socialEntity="${visitor.visitor}" /></td>
					<td><ili:getSocialEntityInfos
							socialEntity="${visitor.visitor}" /></td>
					<td><bean:write name="visitor" property="lastVisite"
							formatKey="date.format" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
</c:if>
