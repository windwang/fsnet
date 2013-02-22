<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:text name="interests.placeholder.search" var="searchMessage" />

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="interests.title.search" />
	</legend>
	<html:javascript formName="/SearchInterest" />

	<table class="inLineTable tableStyle">
		<tr>
			<td><s:form action="/SearchInterest" method="get">
					<div id="SearchInterest">
						<s:textfield property="searchInterests" styleId="searchTexte"
							styleClass="search-query" />
						<ili:placeHolder id="searchTexte" value="${searchMessage}" />
						<s:submit styleClass="btn btn-inverse">
							<s:text name="interests.button.search" />
						</s:submit>
					</div>
				</s:form></td>
		</tr>
	</table>

	<jsp:include page="/content/interests/ResultInterest.jsp" />
</fieldset>
