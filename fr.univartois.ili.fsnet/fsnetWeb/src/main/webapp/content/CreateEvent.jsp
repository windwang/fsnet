<%-- 
    Document   : CreateInterest
    Created on : 18 janv. 2010, 18:06:12
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html:form action="/CreateEvent">
    <html:text property="eventName" />
    <html:submit value="Create Event" />
</html:form>