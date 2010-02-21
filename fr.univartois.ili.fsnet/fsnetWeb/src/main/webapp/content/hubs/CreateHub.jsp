<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan et Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<h3><bean:message key="hubs.create"/></h3>
<html:form action="/CreateHub">
	<html:hidden property="communityId" value="${param.communityId}"/>
    <table id="CreateHub">
        <tr>
            <td>
                <label for="hubName"><bean:message key="hubs.name"/> :</label>
            </td>
            <td>
                <html:text property="hubName" styleId="hubName" />

            </td>
            
            <td>
                <html:submit styleClass="button"><bean:message key="hubs.create"/></html:submit>
            </td>
        </tr>

        <tr class="errorMessage">
            <td>
                <html:errors property="hubName"/>
            </td>
        </tr>
    </table>
                <c:import url="/InterestCheckBoxes.do"/>
</html:form>
<div class="clear"></div>