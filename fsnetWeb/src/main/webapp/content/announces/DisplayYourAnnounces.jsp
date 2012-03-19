<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="announce.leftMenu.my" />
	</legend>

	<c:choose>
		<c:when test="${not empty requestScope.myAnnouncesList}">

			<script type="text/javascript">
				$(document).ready(
						function() {
							function pagination() {
								var nomTable = "yourAnnounces";
								var idColonneATrier = 2;
								var sensDeTri = "desc";
								var aoColumns = [ {
									"bSortable" : false
								}, null, {
									"sType" : "date-euro"
								} ];
								miseEnPageTable(nomTable, idColonneATrier,
										sensDeTri, aoColumns, false, 5);
							}
							pagination();

							function allSelect() {
								$('input[name=selectedAnnounces]').attr(
										'checked', true);
							}

							function allNoSelect() {
								$('input[name=selectedAnnounces]').attr(
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
			<html:form action="/DeleteMultiAnnounces">
				<table id="yourAnnounces"
					class="tablesorter inLineTable fieldsetTableAppli">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll1" /></th>
							<th><bean:message key="tableheader.announcename" /></th>
							<th><bean:message key="tableheader.expirdate" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="4"><html:submit
									styleClass="button">
									<bean:message key="privatemessages.delete" />
								</html:submit></td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="announce" items="${requestScope.myAnnouncesList}">
							<tr>
								<bean:define id="idAnnounce" name="announce" property="id" />
								<td><html:multibox property="selectedAnnounces"
										value="${announce.id}" /></td>
								<td><html:link action="/DisplayAnnounce.do"
										paramId="idAnnounce" paramName="idAnnounce">
										<bean:write name="announce" property="title" />
									</html:link></td>
								<td><bean:write name="announce" property="endDate"
										format="dd/MM/yyyy HH:mm" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</html:form>
		</c:when>
		<c:otherwise>
			<table class="inLineTable fieldsetTableAppli">
				<tr>
					<td><bean:message key="research.announce.emptyList" />.</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>
