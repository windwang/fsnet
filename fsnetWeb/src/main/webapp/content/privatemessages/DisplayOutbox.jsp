<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">

	<legend>
		<bean:message key="privatemessages.Messagessent" />
	</legend>
	<c:choose>
		<c:when test="${empty requestScope.outBoxMessages}">
			<table class="inLineTable tableStyle">
				<tr>
					<td><bean:message key="privatemessages.nomessages" /></td>
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

			<html:form action="/DeleteMultiSentMessages?fromPage=out">
				<table id="tableoutbox"
					class="tablesorter inLineTable tableStyle">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll" /></th>
							<th><bean:message key="tableheader.to" />
							<th><bean:message key="members.firstName" /></th>
							<th><bean:message key="members.name" /></th>
							<th><bean:message key="tableheader.subject" /></th>
							<th><bean:message key="tableheader.date" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="6"><html:submit styleClass="btn btn-inverse">
									<bean:message key="privatemessages.delete" />
								</html:submit></td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach items="${requestScope.outBoxMessages}" var="message">
							<tr>
								<td><html:multibox property="selectedMessages"
										value="${message.id}" /></td>
								<td><ili:getMiniature socialEntity="${message.to}" /></td>
								<td style="width: 15%"><ili:getSocialEntityInfosFirstname
										socialEntity="${message.to}" /></td>
								<td style="width: 15%"><ili:getSocialEntityInfosName
										socialEntity="${message.to}" /></td>
								<td style="width: 40%"><s:a
										action="/DisplaySentMessage">
										<html:param name="messageId" value="${message.id}" />
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
			</html:form>
		</c:otherwise>
	</c:choose>
</fieldset>
