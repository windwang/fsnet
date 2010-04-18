<%-- 
    Document   : CreateInterest
    Created on : 18 janv. 2010, 18:06:12
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<h3><bean:message key="events.0"/></h3>
<html:form action="/CreateEvent">
    <table id="CreateEvent">
        <tr>
            <td>
                <label for="eventName">
                    <bean:message key="events.1"/> :
                </label>
            </td>
            <td>
                <html:text  property="eventName"
                            styleId="eventName"
                            errorStyleClass="error" />
                <logic:messagesPresent property="eventName">
                    <div class="errorMessage">
                        <html:errors property="eventName"/>
                    </div>
                </logic:messagesPresent>
            </td>
        </tr>
        <tr>
            <td></td>
            <td >
                <c:import url="/InterestCheckBoxes.do" />
            </td>
        </tr>
        <tr>
            <td>
                <label for="eventDescription">
                    <bean:message key="events.2"/> :
                </label>
            </td>
            <td>
                <html:textarea  property="eventDescription"
                                errorStyleClass="error"
                                styleClass="mceTextArea"
                                style="width: 100%;"
                                />
                <logic:messagesPresent property="eventDescription">
                    <div class="errorMessage">
                        <html:errors property="eventDescription"/>
                    </div>
                </logic:messagesPresent>
            </td>
        </tr>
        <tr>
            <td>
                <label for="eventDate">
                    <bean:message key="events.3"/> :
                </label>
            </td>
            <td>
                <html:text  property="eventBeginDate" 
                            styleId="eventBeginDate"
                            errorStyleClass="error"/>
                            
                <logic:messagesPresent property="eventBeginDate">
                    <div class="errorMessage">
                        <html:errors property="eventBeginDate"/>
                    </div>
                </logic:messagesPresent>
            </td>
        </tr>
        <tr>
            <td>
                <label for="eventDate">
                    <bean:message key="events.20"/> :
                </label>
            </td>
            <td>
                <html:text  property="eventEndDate" 
                            styleId="eventEndDate"
                            errorStyleClass="error"/>

                <html:submit styleClass="button">
                    <bean:message key="events.4"/>
                </html:submit>
                <logic:messagesPresent property="eventEndDate">
                    <div class="errorMessage">
                        <html:errors property="eventEndDate"/>
                    </div>
                </logic:messagesPresent>
            </td>
        </tr>
    </table>
</html:form>

<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript">
    $(function() {
        $.datepicker.setDefaults($.extend(
        {
            minDate: 0,
            dateFormat: 'dd/mm/yy',
            showOn: 'button',
            buttonImage: 'images/calendar.gif',
            buttonImageOnly: true,
            showMonthAfterYear: false
        }));
        $("#eventBeginDate").datepicker($.datepicker.regional['fr']);
        $("#eventEndDate").datepicker($.datepicker.regional['fr']);
    });
</script> 
