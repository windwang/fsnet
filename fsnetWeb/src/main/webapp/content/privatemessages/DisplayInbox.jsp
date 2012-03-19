<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="privatemessages.inbox" />
	</legend>

  <c:choose>
	<c:when test="${empty requestScope.inBoxMessages}">
		<table class="inLineTable fieldsetTableAppli">
			<tr>
				<td><bean:message key="privatemessages.nomessages" /></td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
	    <div class="space"></div>
		<script type="text/javascript">
			$(document).ready(function(){
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
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, true);
					}
						pagination();
						
						function allSelect(){	
							$('input[name=selectedMessages]').attr('checked', true);
						}
						
						function allNoSelect(){	
							$('input[name=selectedMessages]').attr('checked', false);
						}
						
						$(".checkThemAll1").click(function() {
							if(this.checked){
								allSelect();
							}else{
								allNoSelect();
							}
						});
					});
		</script>
		   <html:form action="/DeleteMultiMessages">
			<table id="tableinbox" class="tablesorter inLineTable fieldsetTableAppli">
				<thead>
					<tr>
						<th class="thMessage"><input type="checkbox" name="selected" class="checkThemAll1" /></td><td><bean:message key="tableheader.from" /> </th>
						<th><bean:message key="members.firstName" /></th>
						<th><bean:message key="members.name" /></th>
						<th><bean:message key="tableheader.subject" /></th>
						<th><bean:message key="tableheader.date" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${requestScope.inBoxMessages}" var="message">
						<c:if test="${not message.reed}">
							<tr class="notReed">
								<td><html:multibox property="selectedMessages"
										value="${message.id}" /></td>
								<td><ili:getMiniature socialEntity="${message.from}" /></td>
								<td style="width: 20%"><html:link action="/DisplayMessage">
										<html:param name="messageId" value="${message.id}" />
										<span>${message.from.firstName}</span>
									</html:link></td>
								<td style="width: 20%"><html:link action="/DisplayMessage">
										<html:param name="messageId" value="${message.id}" />
										<span>${message.from.name}</span>
									</html:link></td>
								<td style="width: 60%"><html:link action="/DisplayMessage">
										<html:param name="messageId" value="${message.id}" />
										<span>${fn:substring(message.subject, 0,20)} : </span>
										<span style="color: gray"> <ili:substring
												beginIndex="0" endIndex="20">
												<ili:noxml>${message.body}</ili:noxml>
											</ili:substring>
										</span>
									</html:link></td>
						</c:if>
						<c:if test="${message.reed}">
							<tr>
								<td><html:multibox property="selectedMessages"
										value="${message.id}" /></td>
								<td><ili:getMiniature socialEntity="${message.from}" /></td>
								<td style="width: 20%"><html:link action="/DisplayMessage">
										<html:param name="messageId" value="${message.id}" />
										<span>${message.from.firstName}</span>
									</html:link></td>
								<td style="width: 20%"><html:link action="/DisplayMessage">
										<html:param name="messageId" value="${message.id}" />
										<span>${message.from.name}</span>
									</html:link></td>
								<td style="width: 60%"><html:link action="/DisplayMessage">
										<html:param name="messageId" value="${message.id}" />
										<span>${fn:substring(message.subject, 0,20)} : </span>
										<span style="color: gray"> <ili:substring
												beginIndex="0" endIndex="20">
												<ili:noxml>${message.body}</ili:noxml>
											</ili:substring>
										</span>
									</html:link></td>
						</c:if>
						<td><bean:write name="message"
								property="creationDate" formatKey="date.format" /></td>
					</c:forEach>
				</tbody>
				</table>
				<br />
				<html:submit styleClass="button">
					<bean:message key="privatemessages.delete" />
				</html:submit>
			</html:form>
		</c:otherwise>
	</c:choose>
</fieldset>
