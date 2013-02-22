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
<<<<<<< HEAD
						value="%{param.communityId}" />
					<s:textfield property="searchText"
=======
						value="${param.communityId}" /> <html:text property="searchText"
>>>>>>> 75d3d39903713564d348b5ead824e237db25c0ee
						styleId="searchTexte" /> <ili:placeHolder id="searchTexte"
						value="${searchMessage}" /> <s:submit styleClass="btn btn-inverse">
						<s:text name="hubs.button.search" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
</fieldset>