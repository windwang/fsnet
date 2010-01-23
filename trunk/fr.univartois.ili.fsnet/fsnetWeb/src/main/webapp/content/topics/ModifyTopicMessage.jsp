<%-- 
    Document   : ModifyCreateTopicMessage
    Created on : 23 janv. 2010, 12:59:30
    Author     : Cerelia Besnainou et Audrey Ruellan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<h3>Modify Message</h3>
<html:form action="/ModifyTopicMessage">
    <table id="CreateTopicMessage">
        <tr>
            <td>
                <label for="messageDescription">Description: </label>
            </td>
            <td>
                <html:textarea  cols="40" rows="8"
                                property="messageDescription"
                                styleId="messageDescription" value="${message.contenu}"> 

               </html:textarea>
            </td>
            <td><html:hidden property="topicId" value="${topicId}"/></td>
            <td><html:hidden property="messageId" value="${message.id}"/></td>
            <td>
                <html:submit styleClass="button">Modify Message</html:submit>
            </td>
        </tr>
    </table>
</html:form>