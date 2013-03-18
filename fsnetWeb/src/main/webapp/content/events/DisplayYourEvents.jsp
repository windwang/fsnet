<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="events.leftMenu.title" />
	</legend>

	<c:choose>
		<c:when test="${not empty requestScope.myEventsList}">

			<script type="text/javascript">
				$(document).ready(
						function() {
							function pagination() {
								var nomTable = "yourEvents";
								var idColonneATrier = 2;
								var sensDeTri = "desc";
								var aoColumns = [ {
									"bSortable" : false
								}, null, {
									"sType" : "date-euro"
								}, {
									"sType" : "date-euro"
								} ];
								miseEnPageTable(nomTable, idColonneATrier,
										sensDeTri, aoColumns, false, 5, true);
							}
							pagination();

							function allSelect() {
								$('input[name=selectedEvents]').attr('checked',
										true);
							}

							function allNoSelect() {
								$('input[name=selectedEvents]').attr('checked',
										false);
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

			<s:form action="/DeleteMultiEvents">
				<table id="yourEvents" class="tablesorter inLineTable  tableStyle">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll1" /></th>
							<th><s:text name="tableheader.eventname" /></th>
							<th><s:text name="tableheader.willoccur" /></th>
							<th><s:text name="tableheader.expirdate" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="4"><s:submit styleClass="btn btn-inverse">
									<s:text name="privatemessages.delete" />
								</s:submit></td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="event" items="${requestScope.myEventsList}">
							<tr>
								
								<td><input type="checkbox" name="selectedEvents"
										value="${event.id}" /></td>
								<td><s:a href="/DisplayEvent">
		                    ${event.title}
		                    <s:param name="eventId" value="%{event.id}" />
									</s:a> <span style="color: gray"> : <ili:substring
											beginIndex="0" endIndex="30">
											<ili:noxml>${event.content}</ili:noxml>
										</ili:substring>
								</span></td>
								<td class="left"><s:text name="startDate"/></td>
								<td class="left"><s:text name="endDate"/></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</s:form>
		</c:when>
		<c:otherwise>
			<table class="inLineTable  tableStyle">
				<tr>
					<td><s:text name="research.event.emptyList" />.</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>
