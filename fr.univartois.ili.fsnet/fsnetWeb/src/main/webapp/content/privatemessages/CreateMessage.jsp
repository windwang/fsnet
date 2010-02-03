<%-- 
    Document   : CreateMessage
    Created on : 2 févr. 2010, 18:30:44
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>


<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>



<h3>Envoyer un message</h3>

<html:form action="/CreatePrivateMessage">

    <table style="width: 100%">
        <tr>
            <td>
                <label for="messageTo">
                    Destinataire
                </label>
            </td>
            <td>
                <html:text  property="messageTo"
                            errorStyleClass="error"
                            style="width: 100%"/>
                <logic:messagesPresent property="messageTo">
                    <div class="errorMessage">
                        <html:errors property="messageTo"/>
                    </div>
                </logic:messagesPresent>
            </td>
        </tr>
        <tr>
            <td>
                <label for="messageSubject">
                    Sujet
                </label>
            </td>
            <td>
                <html:text  property="messageSubject"
                            errorStyleClass="error"
                            style="width: 100%"/>
                <logic:messagesPresent property="messageSubject">
                    <div class="errorMessage">
                        <html:errors property="messageSubject"/>
                    </div>
                </logic:messagesPresent>
            </td>
        </tr>
        <tr>
            <td>
               
            </td>
            <td>
                <html:textarea
                    property="messageBody"
                    styleId="messageBody"
                    styleClass="mceTextArea"
                    style="width: 100%;"
                    >
                </html:textarea>
                <div class="errorMessage">
                    <html:errors property="messageBody"/>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2" class="alignRight">
                <html:submit styleClass="button">
                    Envoyer
                </html:submit>
            </td>
        </tr>
    </table>

</html:form>