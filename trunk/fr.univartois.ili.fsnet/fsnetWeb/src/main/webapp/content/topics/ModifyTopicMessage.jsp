<%-- 
    Document   : ModifyCreateTopicMessage
    Created on : 23 janv. 2010, 12:59:30
    Author     : Cerelia Besnainou et Audrey Ruellan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>

<script type="text/javascript">
    tinyMCE.init({
        // General options
        mode : "textareas",
        theme : "simple"
    });
</script>

<h3>Modify Message</h3>

<html:form action="/ModifyTopicMessage">
    <html:hidden property="topicId" value="${topicId}"/>
    <html:hidden property="messageId" value="${message.id}"/>
    Description:
    <table >
        <tr>
            <td>
                <html:textarea  cols="60" rows="8"
                                property="messageDescription"
                                styleId="messageDescription"
                                value="${message.contenu}">
                </html:textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" class="alignRight">
                <html:submit styleClass="button">Modify Message</html:submit>
            </td>
        </tr>
    </table>

</html:form>