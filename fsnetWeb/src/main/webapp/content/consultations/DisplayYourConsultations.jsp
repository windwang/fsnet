<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="consultations.title.my" />
	</legend>

	<c:choose>
		<c:when test="${not empty requestScope.consultationsList}">

			<script type="text/javascript">
				$(document).ready(
						function() {
							function pagination() {
								var nomTable = "yourConsults";
								var idColonneATrier = 2;
								var sensDeTri = "desc";
								var aoColumns = [ {
									"bSortable" : false
								}, null, {
									"sType" : "date-euro"
								} ];
								miseEnPageTable(nomTable, idColonneATrier,
										sensDeTri, aoColumns, false, 5, true);
							}
							pagination();

							function allSelect() {
								$('input[name=selectedConsultations]').attr(
										'checked', true);
							}

							function allNoSelect() {
								$('input[name=selectedConsultations]').attr(
										'checked', false);
							}

							$(".checkThemAll1").click(function() {
								if (this.checked) {
									allSelect();
								} else {
									allNoSelect();
								}
							});
						});
			</script>
			<html:form action="/DeleteMultiConsultations">
				<table id="yourConsults"
					class="tablesorter inLineTable tableStyle">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll1" /></th>
							<th><bean:message key="tableheader.consultationname" /></th>
							<th><bean:message key="consultations.createdAtDate" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="3"><html:submit styleClass="btn btn-inverse">
									<bean:message key="privatemessages.delete" />
								</html:submit></td>
						</tr>
					</tfoot>
					<c:forEach var="consultation"
						items="${requestScope.consultationsList}">
						<tr>
							<td><html:multibox property="selectedConsultations"
									value="${consultation.id}" /></td>

							<td><s:a
									action="/DisplayAConsultation?id=${consultation.id }">${consultation.title }</s:a>
							</td>
							<td><bean:write name="consultation" property="creationDate"
									formatKey="date.format" /></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</html:form>
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
