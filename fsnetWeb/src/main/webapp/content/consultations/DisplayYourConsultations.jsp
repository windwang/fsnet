<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="consultations.title.my" />
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
			<s:form action="/DeleteMultiConsultations">
				<table id="yourConsults" class="tablesorter inLineTable tableStyle">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll1" /></th>
							<th><s:text name="tableheader.consultationname" /></th>
							<th><s:text name="consultations.createdAtDate" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="3"><s:submit styleClass="btn btn-inverse">
									<s:text name="privatemessages.delete" />
								</s:submit></td>
						</tr>
					</tfoot>
					<c:forEach var="consultation"
						items="${requestScope.consultationsList}">
						<tr>
							<td><s:checkbox property="selectedConsultations"
									value="%{consultation.id}" /></td>

							<td><s:a
									href="/DisplayAConsultation?id=%{consultation.id }">%{consultation.title }</s:a>
							</td>
							<td><s:property value="creationDate" /></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</s:form>
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
