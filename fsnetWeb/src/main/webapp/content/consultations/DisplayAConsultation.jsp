<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<h3>${consultation.title }</h3>

${consultation.choices }

<table>
	<tr>
		<c:forEach var="choice" items="${consultation.choices }">
			<td>${choice.intituled }</td>
		</c:forEach>
	</tr>
	<c:forEach var="vote" items="${consultation.consultationVotes }">
	</c:forEach>
	<html:form action="/VoteConsultation" method="post">
		<tr>
			<c:forEach var="choice" items="${consultation.choices }">
				<td><html:multibox property="voteChoice" value="${choice.id}" /></td>
			</c:forEach>
		</tr>
		<tr>
		<td><html:text property="voteComment"/></td>
		<html:hidden property="id" value="${consultation.id }"/>
		<td><html:submit styleClass="button"><bean:message key="consultation.vote" /></html:submit></td>
		</tr>
	</html:form>
</table>