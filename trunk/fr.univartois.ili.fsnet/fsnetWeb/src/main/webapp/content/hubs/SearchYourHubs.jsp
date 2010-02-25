<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan and Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h3><bean:message key="hubs.search"/></h3>
<html:form action="/SearchYourHubs">
	<html:hidden property="communityId" value="${param.communityId}"/>
    <table id="SearchHub">
        <tr>
            <td>
                <html:text property="hubName" styleId="hubName" />
            </td>
            <td>
                <html:submit styleClass="button"><bean:message key="hubs.search"/></html:submit>
            </td>
        </tr>
        <tr>
            <td>
                <html:errors/>
            </td>
        </tr>
    </table>
</html:form>

<h3><bean:message key="hubs.yourhubs"/></h3>
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
                    ${hub.title}
                </html:link>
            </th>
            <td>
                <bean:message key="hubs.createdOn"/>
                <bean:write name="hub" property="creationDate" format="dd/MM/yyyy"/>
                <bean:message key="hubs.by"/>
                <html:link action="/DisplayProfile">
                    <html:param name="id" value="${hub.creator.id}"/>
                    ${hub.creator.firstName} ${hub.creator.name}
                </html:link>
            </td>
            <td class="tableButton">
                <c:if test="${sessionScope.userId eq hub.creator.id}">
                    <html:link action="/DisplayYourHubs" styleClass="button" onclick="confirmDelete('DeleteYourHub.do?hubId='+${hub.id}+'&communityId='+${hub.community.id})">
                        <html:param name="communityId" value="${hub.community.id}"/>
                        <bean:message key="hubs.delete"/>
                    </html:link>
                </c:if>
            </td>
        </tr>

    </c:forEach>
</table>