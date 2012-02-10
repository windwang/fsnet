<%-- 
    Document   : CreateAnnounce
    Created on : 18 janv. 2010, 18:06:12
    Author     : Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<bean:define id="searchMessage"><bean:message key="announce.search"/></bean:define>

<h3><bean:message key="announce.searchAnnounce" /> </h3>
<table  class="inLineTable"><tr><td>
<html:form action="/Announces" method="GET">
	<div id="SearchAnnounce">
    	<html:text property="textSearchAnnounce" styleId="textSearchAnnounce" />
        <ili:placeHolder id="textSearchAnnounce" value="${searchMessage}" />
	    <html:submit styleClass="button"><bean:message key="announce.searchButton" /></html:submit>
    	<html:messages id="message" />
    	<div class="errorMessage"><html:errors/></div>
    </div>
</html:form>
  </td></tr></table>