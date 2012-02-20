<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetAppli">
  <legend class="legendHome"><bean:message key="consultations.manage" /></legend>

  <c:choose>
	<c:when test="${not empty requestScope.consultationsList}">

		<script type="text/javascript">
	$(document).ready(function pagination() {
		var nomTable = "yourConsults";
		var idColonneATrier = 2;
		var sensDeTri = "desc";
		var aoColumns = [ {
			"bSortable" : false
		}, null,{
			"sType" : "date-euro"
		} , {
			"bSortable" : false
		}];
		miseEnPageTable(nomTable, idColonneATrier, sensDeTri, aoColumns, false);
	});
  </script>
		<table id="yourConsults" class="tablesorter inLineTableDashBoardFieldset fieldsetTable">
			<thead>
				<tr>
					<th width="5%"></th>
					<th width="25%"><bean:message key="tableheader.consultationname" /></th>
					<th width="20%"><bean:message key="consultation.createdAtDate" /></th>
					<th></th>
				</tr>
			</thead>
			<c:forEach var="consultation"
				items="${requestScope.consultationsList}">
				<tr>
					<td><c:import url="/FavoriteFragment.do">
							<c:param name="interactionId" value="${consultation.id}" />
						</c:import></td>
					<td><html:link
							action="/DisplayAConsultation?id=${consultation.id }">${consultation.title }</html:link>
					</td>
					<td><bean:write name="consultation" property="creationDate"
							formatKey="date.format" /></td>
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
		<table class="inLineTableDashBoardFieldset fieldsetTable">
			<tr>
				<td><bean:message key="consultations.noResult" />.</td>
			</tr>
		</table>
	</c:otherwise>
  </c:choose>
</fieldset>
