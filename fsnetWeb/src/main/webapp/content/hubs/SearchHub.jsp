<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:set name="searchMessage">
	<s:text name="hubs.placeholder.search" />
</s:set>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="hubs.title.search" />
	</legend>

	<table id="SearchHub"
		class="inLineTable tableStyle">
		<s:form action="/SearchHub" method="GET">
			<tr>
				<td><s:hidden name="communityId"
						value="%{param.communityId}" />
					<s:textfield property="searchText"
						styleId="searchTexte" /> <ili:placeHolder id="searchTexte"
						value="${searchMessage}" /> <s:submit styleClass="btn btn-inverse">
						<s:text name="hubs.button.search" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
</fieldset>