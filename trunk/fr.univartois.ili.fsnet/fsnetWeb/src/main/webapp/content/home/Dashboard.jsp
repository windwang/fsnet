<%-- 
    Document   : dashboard
    Created on : 23 janv. 2010, 13:55:48
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<h3>Welcome on FSNet, a Firm Social Network Project.</h3>
<table class="inLineTableDashBoard">
<caption>Vos derniers messages</caption>
	<c:forEach items="${requestScope.messages}" var="message" begin="0"
		end="1">
		<c:if test="${not message.reed}">
			<tr class="notReed">
				<td><img src="GetMiniature.do?memberId=${message.from.id}" />
				</td>
				<td style="width: 0%"><html:link action="/DisplayMessage">
					<html:param name="messageId" value="${message.id}" />
					<span>${message.subject} : </span>
					<span style="color: gray"> <ili:substring beginIndex="0" endIndex="20">
						<ili:noxml>${message.body}</ili:noxml>
					</ili:substring> </span>
					</html:link>
				</td>
			</tr>
		</c:if>
		<c:if test="${message.reed}">
			<tr>
				<td><img src="GetMiniature.do?memberId=${message.from.id}" />
				</td>
				<td style="width: 0%"><html:link action="/DisplayMessage">
					<html:param name="messageId" value="${message.id}" />
					<span>${message.subject} : </span>
					<span style="color: gray"> <ili:substring beginIndex="0" endIndex="20">
						<ili:noxml>${message.body}</ili:noxml>
					</ili:substring> </span>
					</html:link>
				</td>
			</tr>
		</c:if>
	</c:forEach>
</table>

<p>The aim of this project is to develop a social network
application in Java/JEE technologie to connect the members of a specific
structure, such as a company or a faculty.</p>

<p>At the difference of current social networking applications, the
membership is limited to the sole members of the structures or some
people invited by those members.</p>