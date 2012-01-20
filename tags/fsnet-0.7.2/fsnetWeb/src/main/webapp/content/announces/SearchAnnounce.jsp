<%-- 
    Document   : CreateAnnounce
    Created on : 18 janv. 2010, 18:06:12
    Author     : Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h3><bean:message key="announce.searchAnnounce" /> </h3>
<html:form action="/Announces" method="GET">
	<div id="SearchAnnounce">
    	<html:text property="textSearchAnnounce" styleId="textSearchAnnounce" />

	    <html:submit styleClass="button"><bean:message key="announce.button.search" /></html:submit>
    	<html:messages id="message" />
    	<html:errors/>
    </div>
</html:form>