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
			<s:form action="/DeleteMultiConsultations" theme="simple">
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
							<td colspan="3"><s:submit cssClass="btn btn-inverse" key="privatemessages.delete"/></td>
						</tr>
					</tfoot>
					<c:forEach var="consultation"
						items="${requestScope.consultationsList}">
						<tr>
							<td><s:checkbox name="selectedConsultations"
									fieldValue="%{consultation.id}" /></td>
							<td><s:url var="consul" action="DisplayAConsultation">
								<s:param name="id" value="%{#attr.consultation.id}"/>
							</s:url>

							<a href="<s:property value='#consul'/>">${consultation.title}</a>
							</td>
							<td><s:property value="%{#attr.consultation.creationDate}" /></td>
						
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
