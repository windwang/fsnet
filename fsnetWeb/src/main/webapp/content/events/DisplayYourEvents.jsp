<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="events.leftMenu.title" />
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

			<html:form action="/DeleteMultiEvents">
				<table id="yourEvents"
					class="tablesorter inLineTable  tableStyle">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll1" /></th>
							<th><bean:message key="tableheader.eventname" /></th>
							<th><bean:message key="tableheader.willoccur" /></th>
							<th><bean:message key="tableheader.expirdate" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="4"><html:submit styleClass="button btn btn-inverse">
									<bean:message key="privatemessages.delete" />
								</html:submit></td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="event" items="${requestScope.myEventsList}">
							<tr>
								<bean:define id="idEvent" name="event" property="id" />
								<td><html:multibox property="selectedEvents"
										value="${event.id}" /></td>
								<td><html:link action="/DisplayEvent">
		                    ${event.title}
		                    <html:param name="eventId" value="${event.id}" />
									</html:link> <span style="color: gray"> : <ili:substring
											beginIndex="0" endIndex="30">
											<ili:noxml>${event.content}</ili:noxml>
										</ili:substring>
								</span></td>
								<td class="left"><bean:write name="event"
										property="startDate" format="dd/MM/yyyy HH:mm" /></td>
								<td class="left"><bean:write name="event"
										property="endDate" format="dd/MM/yyyy HH:mm" /></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</html:form>
		</c:when>
		<c:otherwise>
			<
 class="inLineTable  tableStyle">
				<tr>
					<td><bean:message key="research.event.emptyList" />.</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>
