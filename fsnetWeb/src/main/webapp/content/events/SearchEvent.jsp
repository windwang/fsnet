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

	<table id="SearchEvent" class="inLineTable  tableStyle">
		<s:form action="Events" method="get">
			<tr>
				<td><s:textfield name="textSearchAnnounce"
						id="textSearchAnnounce" cssClass="search-query"/> <ili:placeHolder
						id="textSearchAnnounce" value="${searchMessage}" /> 
						<s:submit cssClass="btn btn-inverse" key="events.button.search"/>
					</td>
			</tr>
		</s:form>
	</table>
</fieldset>
