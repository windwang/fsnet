<%-- 
    Document   : ModifyCreateTopicMessage
    Created on : 23 janv. 2010, 12:59:30
    Author     : Cerelia Besnainou et Audrey Ruellan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>

<script type="text/javascript" src="js/mceTextArea.js"></script>

<h3><bean:message key="topics.modifyMessage"/></h3>

<html:form action="/ModifyTopicMessage">
    <html:hidden property="topicId" value="${topicId}"/>
    <html:hidden property="messageId" value="${message.id}"/>
    <bean:message key="topics.descriptionMessage"/>
    <table style="width: 100%;">
        <tr>
            <td>
                <html:textarea  cols="60" rows="8"
                                property="messageDescription"
                                styleId="messageDescription"
                                styleClass="mceTextArea"
                                style="width: 100%;"
                                value="${message.contenu}">
                </html:textarea>
                 <div class="errorMessage">
                 	<html:errors property="messageDescription"/>
                 </div>
            </td>
        </tr>
        <tr>
            <td colspan="2" class="alignRight">
                <html:submit styleClass="button"><bean:message key="topics.modifyMessage"/></html:submit>
            </td>
        </tr>
    </table>

</html:form>