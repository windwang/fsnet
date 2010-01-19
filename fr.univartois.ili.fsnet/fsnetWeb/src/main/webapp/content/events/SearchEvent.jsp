<%-- 
    Document   : SearchEvent
    Created on : 18 janv. 2010, 20:59:34
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html:form action="/SearchEvent">
    <html:text property="searchString" />
    <html:submit value="Search Event" />
</html:form>