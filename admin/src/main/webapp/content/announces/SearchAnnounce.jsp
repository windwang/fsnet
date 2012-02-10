<%-- 
	author : Bouragba Mohamed
	source : /fsnetWeb/src/main/webapp/content/announces/SearchAnnounce.jsp (Medhi Benzaghar)
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h3><bean:message key="announce.searchAnnounce" /> </h3>
<html:form action="/Announces" method="GET">
	<div id="SearchAnnounce">
    	<html:text property="textSearchAnnounce" styleId="textSearchAnnounce" />

	    <html:submit styleClass="button"><bean:message key="announce.searchButton" /></html:submit>
    	<html:messages id="message" />
    	<div class="errorMessage"><html:errors/></div>
    </div>
</html:form>