<%-- 
    Document   : CreateTopic
    Created on : 24 janv. 2010, 15:21:34
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>



<h3><bean:message key="hubs.createTopic"/></h3>
<html:form action="/CreateTopic">

    <html:hidden property="hubId" value="${param.hubId}"/>
    <table id="CreateEvent">
        <tr>
            <td>
                <label for="topicSubject">
                    <bean:message key="hubs.subjectTopic"/>
                </label>
            </td>
            <td>
                <html:text  property="topicSubject"
                            styleId="eventName"
                            errorStyleClass="error" />
                <logic:messagesPresent property="topicSubject">
                    <div class="errorMessage">
                        <html:errors property="topicSubject"/>
                    </div>
                </logic:messagesPresent>
            </td>
        </tr>
        <tr>
            <td>
                <label for="messageDescription">
                    <bean:message key="topics.descriptionMessage"/>
                </label>
            </td>
            <td>
                <html:textarea
                    property="messageDescription"
                    styleId="messageDescription"
                    styleClass="mceTextArea"
                    style="width: 100%;"
                    >
                </html:textarea>
                <div class="errorMessage">
                    <html:errors property="messageDescription"/>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <html:submit styleClass="button alignRight">
                    <bean:message key="topics.submit"/>
                </html:submit>
            </td>
        </tr>
    </table>
</html:form>