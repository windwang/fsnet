<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="announce.title.all" />
	</legend>

	<c:choose>
		<c:when test="${empty requestScope.annoucesList}">
			<table class="inLineTable fieldsetTableAdmin">
				<tr>
					<td><bean:message key="announce.noResult" /></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<table id="announceTable"
				class="tablesorter inLineTable fieldsetTableAdmin">
				<thead>
					<tr>
						<th><bean:message key="tableheader.announcename" /></th>
						<th><bean:message key="tableheader.by" /></th>
						<th><bean:message key="tableheader.firstname" /></th>
						<th><bean:message key="tableheader.name" /></th>
						<th><bean:message key="tableheader.expirdate" /></th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="announce" items="${requestScope.annoucesList}">
						<tr>
							<bean:define id="idAnnounce" name="announce" property="id" />
							<td><html:link action="/DisplayAnnounce.do"
									paramId="idAnnounce" paramName="idAnnounce">
									<bean:write name="announce" property="title" />
								</html:link></td>
							<td></td>
							<td><html:link action="/DisplayMember">
									<html:param name="idMember" value="${announce.creator.id}" />
	                    	${announce.creator.firstName} 
	                		</html:link></td>
							<td><html:link action="/DisplayMember">
									<html:param name="idMember" value="${announce.creator.id}" />
	                    	 ${announce.creator.name}
	                		</html:link></td>
							<td><bean:write name="announce" property="endDate"
									format="dd/MM/yyyy" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>

<script type="text/javascript">
	$(document).ready(
			function pagination() {
				var nomTable = "announceTable";
				var idColonneATrier = 4;
				var sensDeTri = "desc";
				var aoColumns = [ null, {
					"bSortable" : false
				}, null, null, {
					"sType" : "date-euro"
				} ];
				miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
						aoColumns, false);
			});
</script>
