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
		
	<tr class="header"><th colspan="3">Contacts</th></tr>
	<c:forEach var="member" items="${membersContactsResult}">
		<tr class="content">
			<td>${member.nom}</td>
			<td>${member.prenom}</td>
			<td>${member.email}</td>
		</tr>	
	</c:forEach>
	<tr class="header"><th colspan="3">Requested</th></tr>
	<c:forEach var="member" items="${membersRequestedResult}">
		<tr class="content">
			<td>${member.nom}</td>
			<td>${member.prenom}</td>
			<td>${member.email}</td>
		</tr>	
	</c:forEach>
	<tr class="header"><th colspan="3">Asked</th></tr>
	<c:forEach var="member" items="${membersAskedResult}">
		<tr class="content">
			<td>${member.nom}</td>
			<td>${member.prenom}</td>
			<td>${member.email}</td>
		</tr>	
	</c:forEach>
	<tr class="header"><th colspan="3">All</th></tr>
	<c:forEach var="member" items="${membersResult}">
		<tr class="content">
			<td>${member.nom}</td>
			<td>${member.prenom}</td>
			<td>${member.email}</td>
		</tr>	
	</c:forEach>
</table>