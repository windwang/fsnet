<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<fieldset class="fieldsetCadre">

	<legend>
		<s:text name="privatemessages.Messagessent" />
	</legend>
	<c:choose>
		<c:when test="${empty requestScope.outBoxMessages}">
			<table class="inLineTable tableStyle">
				<tr>
					<td><s:text name="privatemessages.nomessages" /></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<div class="space"></div>
			<script type="text/javascript">
				$(document).ready(
						function() {

							function pagination() {
								var nomTable = "tableoutbox";
								var idColonneATrier = 5;
								var sensDeTri = "desc";
								var aoColumns = [ {
									"bSortable" : false
								}, {
									"bSortable" : false
								}, null, null, null, {
									"sType" : "date-euro"
								} ];
								miseEnPageTable(nomTable, idColonneATrier,
										sensDeTri, aoColumns, true);
							}

							pagination();

							function allSelect() {
								$('input[name=selectedMessages]').attr(
										'checked', true);
							}

							function allNoSelect() {
								$('input[name=selectedMessages]').attr(
										'checked', false);
							}

							$(".checkThemAll").click(function() {
								if (this.checked) {
									allSelect();
								} else {
									allNoSelect();
								}
							});
						});
			</script>

			<s:form action="/DeleteMultiSentMessages?fromPage=out">
				<table id="tableoutbox"
					class="tablesorter inLineTable tableStyle">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll" /></th>
							<th><s:text name="tableheader.to" />
							<th><s:text name="members.firstName" /></th>
							<th><s:text name="members.name" /></th>
							<th><s:text name="tableheader.subject" /></th>
							<th><s:text name="tableheader.date" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="6"><s:submit styleClass="btn btn-inverse">
									<s:text name="privatemessages.delete" />
								</s:submit></td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach items="${requestScope.outBoxMessages}" var="message">
							<tr>
								<td><s:multibox property="selectedMessages"
										value="${message.id}" /></td>
								<td><ili:getMiniature socialEntity="${message.to}" /></td>
								<td style="width: 15%"><ili:getSocialEntityInfosFirstname
										socialEntity="${message.to}" /></td>
								<td style="width: 15%"><ili:getSocialEntityInfosName
										socialEntity="${message.to}" /></td>
								<td style="width: 40%"><s:a
										action="/DisplaySentMessage">
										<s:param name="messageId" value="%{message.id}" />
										<span>${fn:substring(message.subject, 0,20)} : </span>
										<span style="color: gray"> <ili:substring
												beginIndex="0" endIndex="20">
												<ili:noxml>${message.body}</ili:noxml>
											</ili:substring>
										</span>
									</s:a></td>
								<td><bean:write name="message" property="creationDate"
										formatKey="date.format" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</s:form>
		</c:otherwise>
	</c:choose>
</fieldset>
