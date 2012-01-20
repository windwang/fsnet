<%-- 
    Document   : CreateTopicMessage
    Created on : 23 janv. 2010, 12:59:30
    Author     : Cerelia Besnainou and Audrey Ruellan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<h3><bean:message key="topics.createMessage"/></h3>
<html:form action="/CreateTopicMessage">
    <html:hidden property="topicId" value="${topicId}"/>
    <bean:message key="topics.description"/> :
    <table style="width: 100%;">
        <tr>
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
            <td colspan="2" class="alignRight">
                <html:submit styleClass="button"><bean:message key="topics.createMessage"/></html:submit>
            </td>              
        </tr>
    </table>
    <bean:message key="topics.lastmessages"/> :
	<c:forEach var="message" items="${lastMessages}">
	
    <div class="lastMessageOwner">
    	<ili:getSocialEntityInfos socialEntity="${message.from}"/>
    </div>
    <div class="topicTable"> 
    	${message.body}
    </div>
    </c:forEach>

</html:form>