<%-- 
    Document   : ModifyCreateTopicMessage
    Created on : 23 janv. 2010, 12:59:30
    Author     : Cerelia Besnainou et Audrey Ruellan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"/>

<h3>Create Message</h3>
<html:form action="/CreateTopicMessage">
    <html:hidden property="topicId" value="${topicId}"/>
    Description:
    <table id="CreateTopicMessage">
        <tr>
            <td>
                <html:textarea  cols="60" rows="8"
                                property="messageDescription"
                                styleId="messageDescription"
                                styleClass="mceTextArea">
                </html:textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" class="alignRight">
                <html:submit styleClass="button">Create Message</html:submit>
            </td>
        </tr>
    </table>

</html:form>