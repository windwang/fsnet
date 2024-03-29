<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<bean:define id="searchMessage">
	<bean:message key="interests.placeholder.search" />
</bean:define>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="interests.title.search" />
	</legend>
	<html:javascript formName="/SearchInterest" />

	<table class="inLineTable tableStyle">
		<tr>
			<td><html:form action="/SearchInterest" method="get">
					<div id="SearchInterest">
						<html:text property="searchInterests" styleId="searchTexte" styleClass="search-query"/>
						<ili:placeHolder id="searchTexte" value="${searchMessage}" />
						<html:submit styleClass="btn btn-inverse">
							<bean:message key="interests.button.search" />
						</html:submit>
					</div>
				</html:form></td>
		</tr>
	</table>

	<jsp:include page="/content/interests/ResultInterest.jsp" />
</fieldset>
