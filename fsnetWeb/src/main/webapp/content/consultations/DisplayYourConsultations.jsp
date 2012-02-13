<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3>
	<bean:message key="consultations.manage" />
</h3>

<c:choose>
	<c:when test="${not empty requestScope.consultationsList}">

		<script type="text/javascript">
	$(document).ready(function pagination() {
		var nomTable = "yourConsults";
		var idColonneATrier = 1;
		var sensDeTri = "asc";
		var aoColumns = [ {
			"bSortable" : false
		}, null, {
			"bSortable" : false
		}];
		miseEnPageTable(nomTable, idColonneATrier, sensDeTri, aoColumns, false);
	});
</script>
		<table id="yourConsults" class="tablesorter inLineTable">
			<thead>
				<tr>
					<th></th>
					<th><bean:message key="tableheader.name" /></th>
					<th></th>
				</tr>
			</thead>
			<c:forEach var="consultation"
				items="${requestScope.consultationsList}">
				<tr>
					<td><c:import url="/FavoriteFragment.do">
							<c:param name="interactionId" value="${consultation.id}" />
						</c:import> </td><td><html:link action="/DisplayAConsultation?id=${consultation.id }">${consultation.title }</html:link>
						(<bean:message key="consultation.createdAtDate" /> <bean:write
							name="consultation" property="creationDate" format="dd/MM/yyyy" />
						<bean:message key="consultation.createdAtHour" /> <bean:write
							name="consultation" property="creationDate" format="HH:mm" />)</td>
					<td class="tableButton"
						onclick="confirmDelete2(${consultation.id}	)"><html:form
							action="/DeleteAConsultation" method="post"
							styleId="${consultation.id}" styleClass="cursorPointer">
							<html:hidden property="id" value="${consultation.id}" />

							<span class="button"> <bean:message
									key="consultation.delete" />
							</span>
						</html:form></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<table class="inLineTable">
			<tr>
				<td><bean:message key="consultations.noResult" />.</td>
			</tr>
		</table>
	</c:otherwise>
</c:choose>
