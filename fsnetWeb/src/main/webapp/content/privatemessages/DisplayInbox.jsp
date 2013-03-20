<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="privatemessages.inbox" />
	</legend>

	<c:choose>
		<c:when test="${empty requestScope.inBoxMessages}">
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
								var nomTable = "tableinbox";
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
										sensDeTri, aoColumns, true, 10, true);
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

							$(".checkThemAll1").click(function() {
								if (this.checked) {
									allSelect();
								} else {
									allNoSelect();
								}
							});
						});
			</script>
			
			
<style>
	table.cutMessage {
		table-layout: fixed;
	}

	.inLineTable .cutMessage {
		text-overflow:ellipsis; 
		overflow: hidden; 
		white-space:nowrap; 
		text-align:left;
	}
</style>
			<html:form action="/DeleteMultiMessages?fromPage=in">
				<table id="tableinbox" class="tablesorter inLineTable tableStyle cutMessage" >
					<thead>
						<tr>
							<th style="width: 15px" class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll1" /></th>
							<th style="width: 50px"><bean:message key="tableheader.from" /></th>
							<th><bean:message key="members.firstName" /></th>
							<th><bean:message key="members.name" /></th>
							<th style="width: 50%"><bean:message key="tableheader.subject" /></th>
							<th style="width: 10%"><bean:message key="tableheader.date" /></th>
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
						<c:forEach items="${requestScope.inBoxMessages}" var="message">
							<tr class="${message.reed ? '' : 'notReed'}">
								<td><html:multibox property="selectedMessages"
										value="${message.id}" /></td>
								<td><ili:getMiniature socialEntity="${message.from}" /></td>
								<td><html:link action="/DisplayMessage">
										<html:param name="messageId" value="${message.id}" />
										<span>${message.from.firstName}</span>
									</html:link></td>
								<td><html:link action="/DisplayMessage">
										<html:param name="messageId" value="${message.id}" />
										<span>${message.from.name}</span>
									</html:link></td>
								<td class="cutMessage">
										<html:link action="/DisplayMessage">
											<html:param name="messageId" value="${message.id}" />
											<span>${message.subject} : </span>
											<span style="color: gray"><ili:noxml>${message.body}</ili:noxml></span>
										</html:link>
									</td>
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
