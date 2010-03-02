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
                <bean:message key="privatemessages.to"/> :
                <html:link action="/DisplayProfile">
                    <html:param name="id" value="${theMessage.to.id}"/>
                    ${theMessage.to.firstName} ${theMessage.to.name}
                </html:link>
                ${theMessage.to.email}
                <span style="float: right">
                    <bean:write name="theMessage" property="creationDate" formatKey="date.format"/>
                </span>
            </td>
        </tr>
        <tr>
            <td>
                ${theMessage.body}
            </td>
        </tr>
    </table>

    <!-- TODO factorise this code with createMessage.jsp -->
    <html:link action="/DeletePrivateMessage" styleClass="button" style="float: right">
        <html:param name="messageId" value="${theMessage.id}"/>
        <bean:message key="privatemessages.delete"/>
    </html:link>

</logic:notEmpty>