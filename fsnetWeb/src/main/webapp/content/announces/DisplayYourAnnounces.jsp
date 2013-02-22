<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="announce.leftMenu.my" />
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
										sensDeTri, aoColumns, false, 5, true);
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
			<s:form action="/DeleteMultiAnnounces">
				<table id="yourAnnounces"
					class="tablesorter inLineTable tableStyle">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll1" /></th>
							<th><s:text name="tableheader.announcename" /></th>
							<th><s:text name="tableheader.expirdate" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="4"><s:submit
									styleClass="btn btn-inverse">
									<s:text name="privatemessages.delete" />
								</s:submit></td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="announce" items="${requestScope.myAnnouncesList}">
							<tr>
<<<<<<< HEAD
								<s:set id="idAnnounce" name="announce" var="id" />
								<td><s:checkbox property="selectedAnnounces"
										value="%{announce.id}" /></td>
								<td><s:a action="/DisplayAnnounce.do"
										paramId="idAnnounce" paramName="idAnnounce">
										<s:property value="announce" default="title" />
									</s:a></td>
								<td><s:property value="announce" default="endDate"
=======
								<bean:define id="idAnnounce" name="announce" property="id" />
								<td><html:multibox property="selectedAnnounces"
										value="${announce.id}" /></td>
								<td><s:a action="/DisplayAnnounce.do"
										paramId="idAnnounce" paramName="idAnnounce">
										<bean:write name="announce" property="title" />
									</s:a></td>
								<td><bean:write name="announce" property="endDate"
>>>>>>> b51606823970ae78ca4476d53fe647d5ace62683
										format="dd/MM/yyyy HH:mm" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</s:form>
		</c:when>
		<c:otherwise>
			<table class="inLineTable tableStyle">
				<tr>
					<td><s:text name="research.announce.emptyList" />.</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>
