<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<bean:define id="searchMessage">
	<bean:message key="hubs.placeholder.search" />
</bean:define>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="hubs.title.search" />
	</legend>

	<table id="SearchHub"
		class="inLineTable fieldsetTableAppli">
		<html:form action="/SearchHub" method="GET">
			<tr>
				<td><html:hidden property="communityId"
						value="${param.communityId}" /> <html:text property="searchText"
						styleId="searchTexte" /> <ili:placeHolder id="searchTexte"
						value="${searchMessage}" /> <html:submit styleClass="button">
						<bean:message key="hubs.button.search" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>