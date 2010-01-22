<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan et Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>


<h3><bean:message key="hubs.hub"/> ${hubResult.nomCommunaute}</h3>
<table id="HubList">
	<tr class="content">
			<td class="left">${hubResult.dateCreation}</td>
			<td class="left">${hubResult.createur.prenom} ${hubResult.createur.nom}</td>
    		<td class="right">
    			 <c:if test="${sessionScope.user.id eq hubResult.createur.id}">
	    			 <html:link action="/DeleteHub?hubId=${hubResult.id}" styleClass="button"><bean:message key="hubs.create"/></html:link>
				</c:if>
    		</td>
    </tr>
   
    	<c:forEach var="topic" items="${hubResult.lesTopics}">
    	 <tr>
    	 	<td>${topic.sujet}</td>
    	 </tr>
    	</c:forEach>
    
    <tr class="gap">
    		<td colspan="2"></td>
    </tr>
</table>