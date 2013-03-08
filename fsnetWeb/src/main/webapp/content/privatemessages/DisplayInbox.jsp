<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="privatemessages.inbox" />
	</legend>

	<c:choose>
		<c:when test="${empty requestScope.inBoxMessages}">
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
			<s:form action="DeleteMultiMessages?fromPage=in">
				<table id="tableinbox" class="tablesorter inLineTable tableStyle">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll1" /></th>
							<th><s:text name="tableheader.from" /></th>
							<th><s:text name="members.firstName" /></th>
							<th><s:text name="members.name" /></th>
							<th><s:text name="tableheader.subject" /></th>
							<th><s:text name="tableheader.date" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="6"><s:submit cssClass="btn btn-inverse"
									key="privatemessages.delete" /></td>
						</tr>
					</tfoot>

					<tbody>
						<c:forEach items="${requestScope.inBoxMessages}" var="message">
							<c:if test="${not message.reed}">
								<tr class="notReed">
									<td><input type="checkbox" name="selectedMessages" value="${message.id}"/></td>
									<td><ili:getMiniature socialEntity="${message.from}" /></td>
									<td style="width: 20%"><s:a action="DisplayMessage">
											<s:param name="messageId">
												<c:out value="${message.id}" />
											</s:param>
											<span>${message.from.firstName}</span>
										</s:a></td>
									<td style="width: 20%"><s:a action="DisplayMessage">
											<s:param name="messageId">
												<c:out value="${message.id}" />
											</s:param>
											<span>${message.from.name}</span>
										</s:a></td>
									<td style="width: 60%"><s:a action="DisplayMessage">
											<s:param name="messageId">
												<c:out value="${message.id}" />
											</s:param>
											<span>${fn:substring(message.subject, 0,20)} : </span>
											<span style="color: gray"> <ili:substring
													beginIndex="0" endIndex="20">
													<ili:noxml>${message.body}</ili:noxml>
												</ili:substring>
											</span>
										</s:a></td>
							</c:if>
							<c:if test="${message.reed}">
								<tr>
									<td><input type="checkbox" name="selectedMessages" value="${message.id}"/>
											</td>
									<td><ili:getMiniature socialEntity="${message.from}" /></td>
									<td style="width: 20%"><s:a action="DisplayMessage">
											<s:param name="messageId">
												<c:out value="${message.id}" />
											</s:param>
											<span>${message.from.firstName}</span>
										</s:a></td>
									<td style="width: 20%"><s:a action="DisplayMessage">
											<s:param name="messageId">
												<c:out value="${message.id}" />
											</s:param>
											<span>${message.from.name}</span>
										</s:a></td>
									<td style="width: 60%"><s:a action="DisplayMessage">
											<s:param name="messageId">
												<c:out value="${message.id}" />
											</s:param>
											<span>${fn:substring(message.subject, 0,20)} : </span>
											<span style="color: gray"> <ili:substring
													beginIndex="0" endIndex="20">
													<ili:noxml>${message.body}</ili:noxml>
												</ili:substring>
											</span>
										</s:a></td>
							</c:if>
							<td><s:property value="creationDate" /></td>
						</c:forEach>
					</tbody>
				</table>
				<br />

			</s:form>
		</c:otherwise>
	</c:choose>
</fieldset>
