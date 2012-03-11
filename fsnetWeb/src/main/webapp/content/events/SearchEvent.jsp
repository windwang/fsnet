<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<bean:define id="searchMessage">
	<bean:message key="events.placeholder.search" />
</bean:define>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="events.title.search" />
	</legend>

	<table id="SearchEvent"
		class="inLineTableDashBoardFieldset fieldsetTable">
		<html:form action="/Events" method="get">
			<tr>
				<td><html:text property="searchString" styleId="searchTexte" />
					<ili:placeHolder id="searchTexte" value="${searchMessage}" /> <html:submit
						styleClass="button">
						<bean:message key="events.button.search" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>
