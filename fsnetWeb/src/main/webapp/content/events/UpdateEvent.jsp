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
<table  class="inLineTable"><tr><td>
<html:form action="/UpdateEvent">
    <table id="UpdateEvent">
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
                
                <html:hidden property="eventId" styleId="id" />
                
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
                <label for="eventAddress">
                    <bean:message key="events.23"/> :
                </label>
            </td>
            <td>
                <html:text  property="eventAddress"
                            styleId="eventAddress"
                            errorStyleClass="error" />
                <logic:messagesPresent property="eventAddress">
                    <div class="errorMessage">
                        <html:errors property="eventAddress"/>
                    </div>
                </logic:messagesPresent>
            </td>
        </tr>
        <tr>
            <td>
                <label for="eventCity">
                    <bean:message key="events.24"/> :
                </label>
            </td>
            <td>
                <html:text  property="eventCity"
                            styleId="eventCity"
                            errorStyleClass="error" />
                <logic:messagesPresent property="eventCity">
                    <div class="errorMessage">
                        <html:errors property="eventCity"/>
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

                <logic:messagesPresent property="eventEndDate">
                    <div class="errorMessage">
                        <html:errors property="eventEndDate"/>
                    </div>
                </logic:messagesPresent>
            </td>
        </tr>
        <tr>
        	<td>
        		<label for="eventRecallTime">
                    <bean:message key="events.recall"/> :
             	</label>
            </td>
            <td>
       			<html:text  property="eventRecallTime"
                            styleId="eventRecallTime"
                            errorStyleClass="error" />
 
                <html:select property="eventRecallTypeTime" styleId="eventRecallTypeTime">
                    <html:option value="minute">
                    	<bean:message key="event.recall.minute"/>
                    </html:option>
                    <html:option value="hour">
                    	<bean:message key="event.recall.hour"/>
                    </html:option>
                    <html:option value="day">
                    	<bean:message key="event.recall.day"/>
                    </html:option>
                </html:select>
                
                <logic:messagesPresent property="eventRecallTime">
                    <div class="errorMessage">
                        <html:errors property="eventRecallTime"/>
                    </div>
                </logic:messagesPresent>
			</td>
            <td>
            	<html:submit styleClass="button">
                    <bean:message key="events.update"/>
                </html:submit>
            </td>
        </tr>
    </table>
</html:form>
</td></tr></table>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript">
$(function() {

    $.datepicker.setDefaults($.datepicker.regional['fr']);
    $.datepicker.setDefaults($.extend(
            {
                minDate: 0,
                dateFormat: 'dd/mm/yy',
                showOn: 'both',
                buttonImage: 'images/calendar.gif',
                buttonImageOnly: true,
                showMonthAfterYear: false
            }));
    $.timepicker.regional['fr'] = {
    		timeOnlyTitle: 'Temps',
    		timeText: 'Temps',
    		hourText: 'Heure',
    		minuteText: 'Minute',
    		secondText: 'Seconde',
    		millisecText: 'milliseconde',
    		currentText: 'Maintenant',
    		closeText: 'Ok',
    		ampm: false,
    		timeFormat: 'hh:mm',
    	};
    $.timepicker.setDefaults($.timepicker.regional['fr']);
    
	$('#eventBeginDate').datetimepicker();
	$('#eventEndDate').datetimepicker();

});
</script> 


