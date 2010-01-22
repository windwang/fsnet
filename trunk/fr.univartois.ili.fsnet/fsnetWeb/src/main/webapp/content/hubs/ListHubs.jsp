<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan et Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>


<h3><bean:message key="hubs.hubs"/></h3>

<table id="HubList">
<c:forEach var="hub" items="${hubResults}">
	<tr class="header">
			<th colspan="2">
				<html:link action="/DisplayHub">
					<html:param name="hubId" value="${hub.id}"/>
					${hub.nomCommunaute}
				</html:link>
			</th>
	</tr>
	<tr class="content">
			<td class="left">${hub.dateCreation}</td>
			<td class="left">${hub.createur.prenom} ${hub.createur.nom}</td>
    		<td class="right">
    			 <c:if test="${sessionScope.user.id eq hub.createur.id}">
	    			 <html:link action="/DeleteHub" styleClass="button">
		    			 <html:param name="hubId" value="${hub.id}"/>
		    			 <bean:message key="hubs.delete"/>
	    			 </html:link>
				</c:if>			
    		</td>
    </tr>
    <tr class="gap">
    		<td colspan="2"></td>
    </tr>
</c:forEach>
</table>

	
