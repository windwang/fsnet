<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<s:set id="searchMessage" >
	<s:text name="announce.placeHolder.search" />
</s:set>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="announce.title.search" />
	</legend>
	<table id="SearchAnnounce"
		class="inLineTable tableStyle">
		<s:form action="Announces" method="GET">
			<tr>
				<td><s:textfield property="textSearchAnnounce"
						styleId="textSearchAnnounce" styleClass="search-query"/> <ili:placeHolder
						id="textSearchAnnounce" value="${searchMessage}" /> <s:submit
						styleClass="btn btn-inverse">
						<s:text name="announce.button.search" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
</fieldset>
