<%-- 
    Document   : DisplayOutbox
    Created on : 8 févr. 2010, 21:38:32
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3><bean:message key="privatemessages.Messagessent"/></h3>
<c:choose>
 <c:when test="${empty requestScope.outBoxMessagesPaginator.resultList}">
 	<bean:message key="privatemessages.nomessages"/>
 </c:when>
	<c:otherwise>
		<html:form action="/DeleteMultiSentMessages">
			<table class="inLineTable">
			    <c:forEach items="${requestScope.outBoxMessagesPaginator.resultList}" var="message">
			        <tr>
			            <td>
			                <html:multibox property="selectedMessages" value="${message.id}"/>
			            </td>
			            <td style="width: 25%">
			                <bean:message key="privatemessages.sentTO"/> :
			                <html:link action="/DisplaySentMessage">
			                    <html:param name="messageId" value="${message.id}"/>
			                    ${message.to.firstName} ${message.to.name}
			                </html:link>
			            </td>
			            <td style="width: 50%">
			
			                <html:link action="/DisplaySentMessage">
			                    <html:param name="messageId" value="${message.id}"/>
			                    <span>${fn:substring(message.subject, 0,20)} : </span>
			                    <span style="color: gray">
			                    	<ili:substring beginIndex="0" endIndex="20"><ili:noxml>${message.body}</ili:noxml></ili:substring>
			                    </span>
			                </html:link>
			            </td>
			            <td class="alignRight">
			                <bean:write name="message" property="creationDate" format="dd/MM/yyyy HH:mm"/>
			            </td>
			        </tr>
			    </c:forEach>
			</table>
		    <html:submit styleClass="button">
		    	<bean:message key="privatemessages.delete"/>
		    </html:submit>
		</html:form>
		<c:set var="paginatorInstance" value="${requestScope.outBoxMessagesPaginator}" scope="request"/>
		<c:set var="paginatorAction" value="/Outbox" scope="request"/>
		<c:set var="paginatorTile" value="outboxMessages" scope="request"/>
		<c:import url="/content/pagination/Pagination.jsp"/>
	</c:otherwise>
</c:choose>