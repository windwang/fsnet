<%-- 
    Document   : DisplayPrivateMessage
    Created on : 2 févr. 2010, 18:29:26
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<logic:empty name="theMessage" scope="request">
    <h3><bean:message key="privatemessages.impossible"/></h3>
</logic:empty>
<h3>
    ${theMessage.subject}
</h3>
<logic:notEmpty name="theMessage" scope="request">
    <table class="topicTable">
        <tr class="topicHeader">
            <td>
                <bean:message key="privatemessages.from"/> :
                <html:link action="/DisplayProfile">
                    <html:param name="id" value="${theMessage.from.id}"/>
                    ${theMessage.from.firstName} ${theMessage.from.name}
                </html:link>
                ${theMessage.from.email}
                <span style="float: right">
                    <bean:write name="theMessage" property="creationDate" format="dd/MM/yyyy HH:mm"/>
                </span>
            </td>
        </tr>
        <tr>
            <td>
                ${theMessage.body}
            </td>
        </tr>
    </table>
    <a class="button" onclick="document.getElementById('quickResponse').style.display='table'">
        <img src="images/quickResponse.png" style="vertical-align: bottom"/>
        <bean:message key="privatemessages.Quickresponse"/>
    </a>

    <!-- TODO factorise this code with createMessage.jsp -->
    <html:link action="/DeletePrivateMessage" styleClass="button" style="float: right">
        <html:param name="messageId" value="${theMessage.id}"/>
        <bean:message key="privatemessages.delete"/>
    </html:link>

    <html:form action="/CreatePrivateMessage">
        <html:hidden property="messageTo" value="${theMessage.from.email}"/>
        <table id="quickResponse" style="width: 100%; display : none ; margin-top : 10px;">
            <tr>
                <td>
                    <label for="messageTo">
                        <bean:message key="privatemessages.to"/> :
                    </label>
                </td>
                <td>
                    <html:text  property="messageTo"
                                errorStyleClass="error"
                                value="${theMessage.from.email}"
                                readonly="true"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="messageSubject">
                        <bean:message key="privatemessages.subject"/> :
                    </label>
                </td>
                <td>
                    <html:text  property="messageSubject" errorStyleClass="error" value="RE: ${theMessage.subject}"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <html:textarea
                        property="messageBody"
                        styleClass="mceTextArea"
                        style="width:100%">
                    </html:textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="alignRight">
                    <html:submit styleClass="button" onclick="this.disabled=true; this.value='Sending Message'; this.form.submit();">
                        <bean:message key="privatemessages.send"/>
                    </html:submit>
                </td>
            </tr>
        </table>
    </html:form>


</logic:notEmpty>