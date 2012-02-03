<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan and Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<h3><bean:message key="hubs.search"/></h3>
<table  class="inLineTable"><tr><td>
<html:form action="/SearchHub" method="GET">
	<html:hidden property="communityId" value="${param.communityId}"/>
    <table id="SearchHub">
        <tr>
            <td>
                <html:text property="searchText" styleId="hubName" />
            </td>
            <td>
                <html:submit styleClass="button"><bean:message key="hubs.search"/></html:submit>
            </td>
        </tr>
    </table>
</html:form>
</td></tr></table>