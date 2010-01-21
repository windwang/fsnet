<%-- 
    Document   : ListEvents
    Created on : 18 janv. 2010, 21:05:18
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<h3>Search Event</h3>
<html:form action="/SearchEvent">
	<div id="SearchEvent">
    	<html:text property="searchString" />
    	<html:submit styleClass="button" />
    </div>
</html:form>

<h3>Events :</h3>
<logic:messagesPresent property="searchString">
	<p class="errorMessage"><html:errors property="searchString"/></p>
</logic:messagesPresent>
<table id="EventList">
	<c:forEach var="event" items="${events}">
		<tr class="header">
			<th colspan="2">${event.nom}</th>
		</tr>
		<tr class="content">
			<td class="left">${event.dateManifestation}</td>
    		<td class="right">
    			${event.contenu}
    		</td>
    	</tr>
    	<tr class="gap">
    		<td colspan="2"></td>
    	</tr>
	</c:forEach>
</table>