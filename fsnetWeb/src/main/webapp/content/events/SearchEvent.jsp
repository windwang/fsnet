<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:set name="searchMessage">
	<s:text name="events.placeholder.search" />
</s:set>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="events.title.search" />
	</legend>

	<table id="SearchEvent"
		class="inLineTable  tableStyle">
		<s:form action="Events" method="get">
			<tr>
				<td>
				<s:textfield property="searchString" var="searchTexte" cssClass="search-query"/>
					<ili:placeHolder id="searchTexte" value="%{searchMessage}" /> <s:submit
						styleClass="button btn btn-inverse">
						<s:text name="events.button.search" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
</fieldset>
