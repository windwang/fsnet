<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>


<h3>Search Member</h3>
<html:form action="SearchMember">
	<div id="SearchMember">
    	<html:text property="searchText" />
    	<html:submit styleClass="button" />
    </div>
</html:form>

<h3>Members :</h3>
<table id="MemberList">
	<c:if test="${! empty membersContactsResult}">
	<tr class="header"><th colspan="3">Contacts</th></tr>
	<c:forEach var="member" items="${membersContactsResult}">
		<tr class="content">
			<td>${member.nom}</td>
			<td>${member.prenom}</td>
			<td>
				<html:link action="/DisplayProfile?id=${member.id}" styleClass="button">Profil</html:link>
			</td>
		</tr>	
	</c:forEach>
	</c:if>	
	
	<c:if test="${! empty membersRequestedResult}">
	<tr class="header"><th colspan="3">Requested</th></tr>
	<c:forEach var="member" items="${membersRequestedResult}">
		<tr class="content">
			<td>${member.nom}</td>
			<td>${member.prenom}</td>
			<td>
				<html:link action="/DisplayProfile?id=${member.id}" styleClass="button">Profil</html:link>
			</td>
		</tr>	
	</c:forEach>
	</c:if>	
	
	<c:if test="${! empty membersAskedResult}">
	<tr class="header"><th colspan="3">Asked</th></tr>
	<c:forEach var="member" items="${membersAskedResult}">
		<tr class="content">
			<td>${member.nom}</td>
			<td>${member.prenom}</td>
			<td>
				<html:link action="/AcceptContact?entityAccepted=${member.id}" styleClass="button">Accepter</html:link>
				<html:link action="/RefuseContact?entityRefused=${member.id}" styleClass="button">Refuser</html:link>	 
			</td>
		</tr>	
	</c:forEach>
	</c:if>
	
	<c:if test="${! empty membersResult}">
	<tr class="header"><th colspan="3">Others</th></tr>
	<c:forEach var="member" items="${membersResult}">
		<tr class="content">
			<td>${member.nom}</td>
			<td>${member.prenom}</td>
			<td>
				<html:link action="/ContactDemand?entitySelected=${member.id}" styleClass="button">Ajouter</html:link>
			</td>
		</tr>	
	</c:forEach>
	</c:if>
</table>