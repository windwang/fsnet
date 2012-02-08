<%-- 
    Document   : DisplayMessages
    Created on : 2 févr. 2010, 18:29:45
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
 

<script type="text/javascript">
	$(document).ready(function() {
	    $('#tablesorter').dataTable( {
	    	"aaSorting" : [ [ 4, "desc" ] ],
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, null, { "sType": "date-euro" }],
			"bInfo" : true,
			"bPaginate" : true,
			"sDom": '<"top"ip><"clear">rt<"bottom"p>',   
			"sPaginationType": "scrolling"
	    } );
	} );
</script>

<h3><bean:message key="privatemessages.inbox" /></h3>
<c:choose>
	<c:when test="${empty requestScope.inBoxMessages}">
		<bean:message key="privatemessages.nomessages" />
	</c:when>
	<c:otherwise>
		<html:form action="/DeleteMultiMessages">
			<table id=tablesorter class="tablesorter inLineTable">
				<thead>
					<tr>
						<th></th>
						<th></th>
						<th><bean:message key="privatemessages.from" /></th>
						<th><bean:message key="privatemessages.subject" /></th>
						<th><bean:message key="privatemessages.date" /></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${requestScope.inBoxMessages}" var="message">
					<c:if test="${not message.reed}">
						<tr class="notReed">
							<td>
								<html:multibox property="selectedMessages" value="${message.id}" />
							</td>
							<td>
								<ili:getMiniature socialEntity="${message.from}"/>
							</td>
							<td style="width: 25%">
								<html:link action="/DisplayMessage">
								<html:param name="messageId" value="${message.id}" />
								<span>${message.from.firstName} ${message.from.name}</span>
								
							</html:link></td>
							<td style="width: 50%"><html:link action="/DisplayMessage">
								<html:param name="messageId" value="${message.id}" />
								<span>${fn:substring(message.subject, 0,20)} : </span>
								<span style="color: gray"> <ili:substring beginIndex="0"
									endIndex="20">
									<ili:noxml>${message.body}</ili:noxml>
								</ili:substring> </span>
							</html:link></td>
					</c:if>
					<c:if test="${message.reed}">
						<tr>
							<td><html:multibox property="selectedMessages"
								value="${message.id}" /></td>
							<td>
								<ili:getMiniature socialEntity="${message.from}"/>
							</td>
							<td style="width: 25%">
								<html:link action="/DisplayMessage">
								<html:param name="messageId" value="${message.id}" />
								<span>${message.from.firstName} ${message.from.name}</span>
								</html:link>
		                    </td>
							<td style="width: 50%"><html:link action="/DisplayMessage">
								<html:param name="messageId" value="${message.id}" />
								<span>${fn:substring(message.subject, 0,20)} : </span>
								<span style="color: gray"> <ili:substring beginIndex="0"
									endIndex="20">
									<ili:noxml>${message.body}</ili:noxml>
								</ili:substring> </span>
							</html:link></td>
					</c:if>
					<td class="alignRight"><bean:write name="message"
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