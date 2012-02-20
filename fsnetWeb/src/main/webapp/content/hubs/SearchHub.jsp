<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan and Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<bean:define id="searchMessage">
	<bean:message key="hubs.search" />
</bean:define>

<fieldset class="fieldsetAppli">
  <legend class="legendHome"><bean:message key="hubs.searchHubs" /></legend>
  
  <table class="inLineTableDashBoardFieldset fieldsetTable">
	<tr>
		<td><html:form action="/SearchHub" method="get">
				<html:hidden property="communityId" value="${param.communityId}" />
				<table id="SearchHub">
					<tr>
						<td><html:text property="searchText" styleId="hubName" /> <ili:placeHolder
								id="hubName" value="${searchMessage}" /></td>
						<td><html:submit styleClass="button">
								<bean:message key="hubs.searchButton" />
							</html:submit></td>
					</tr>
				</table>
			</html:form></td>
	</tr>
</table>
</fieldset>