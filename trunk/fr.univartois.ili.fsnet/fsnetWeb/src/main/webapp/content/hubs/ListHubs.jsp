<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan et Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<h3><bean:message key="hubs.hubs"/></h3>
<c:set var="hub" value="${hubResults}"/>
<logic:empty name="hub">
        <bean:message key="hubs.hubNotFound"/>
</logic:empty>
<table class="inLineTable">

    <c:forEach var="hub" items="${hubResults}">
        <tr>
            <th>
                <html:link action="/DisplayHub">
                    <html:param name="hubId" value="${hub.id}"/>
                    ${hub.nomCommunaute}
                </html:link>
            </th>
            <td>
                <bean:message key="hubs.createdOn"/>
                <bean:write name="hub" property="dateCreation" format="dd/MM/yyyy"/>
                <bean:message key="hubs.by"/>
                <html:link action="/DisplayProfile">
                    <html:param name="id" value="${hub.createur.id}"/>
                    ${hub.createur.prenom} ${hub.createur.nom}
                </html:link>
            </td>
            <td class="tableButton">
                <c:if test="${sessionScope.user.id eq hub.createur.id}">
                    <html:link action="/DeleteHub" styleClass="button">
                        <html:param name="hubId" value="${hub.id}"/>
                        <bean:message key="hubs.delete"/>
                    </html:link>
                </c:if>
            </td>
        </tr>

    </c:forEach>
</table>


