<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan et Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h3>Hubs :</h3>

<table id="HubList">
<c:forEach var="hub" items="${hubResults}">
	<tr class="header">
			<th colspan="2"><html:link action="/DisplayHub?hubId=${hub.id}">${hub.nomCommunaute}</html:link></th>
	</tr>
	<tr class="content">
			<td class="left">${hub.dateCreation}</td>
			<td class="left">${hub.createur.prenom} ${hub.createur.nom}</td>
    		<td class="right">
    			 <c:if test="${sessionScope.user.id eq hub.createur.id}">
	    			 <html:link action="/DeleteHub?hubId=${hub.id}" styleClass="button">Delete Hub</html:link>
				</c:if>
				<html:link action="/ListTopic" styleClass="button">
					<html:param name="hubId" value="${hub.id}"/>
					display topics</html:link>					
    		</td>
    </tr>
    <tr class="gap">
    		<td colspan="2"></td>
    </tr>
</c:forEach>
</table>

	
