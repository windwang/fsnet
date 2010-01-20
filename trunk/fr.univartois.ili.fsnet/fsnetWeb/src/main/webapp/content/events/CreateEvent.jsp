<%-- 
    Document   : CreateInterest
    Created on : 18 janv. 2010, 18:06:12
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<h3>Create Event</h3>
<html:form action="/CreateEvent">
	<table id="CreateEvent">
		<tr>
			<td>
				<label for="eventName">Name :</label>
			</td>
			<td>
    			<html:text property="eventName" styleId="eventName" />
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<label for="eventDescription">Description: </label>
    		</td>
    		<td>
    			<html:textarea cols="40" rows="8" property="eventDescription" styleId="eventDescription"/>
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<label for="eventDate">Date :</label>
    		</td>
    		<td>
    			<html:text property="eventDate" styleId="eventDate"/>
    			<html:submit styleClass="button">Create Event</html:submit>
    		</td>
    	</tr>
    	<tr>
    		<td colspan="2">
    			
    			<html:errors/>
    		</td>
    	</tr>
    </table>
</html:form>