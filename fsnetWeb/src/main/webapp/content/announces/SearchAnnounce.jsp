<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<bean:define id="searchMessage">
	<bean:message key="announce.placeHolder.search" />
</bean:define>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="announce.title.search" />
	</legend>
	<table id="SearchAnnounce"
		class="inLineTable tableStyle">
		<html:form action="/Announces" method="GET">
			<tr>
				<td><html:text property="textSearchAnnounce"
						styleId="textSearchAnnounce" /> <ili:placeHolder
						id="textSearchAnnounce" value="${searchMessage}" /> <html:submit
						styleClass="btn btn-inverse">
						<bean:message key="announce.button.search" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>