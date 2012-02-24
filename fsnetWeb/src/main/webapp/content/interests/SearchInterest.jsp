<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="interests.title.search" />
	</legend>
	<html:javascript formName="/SearchInterest" />
	<html:form action="/SearchInterest" method="get">
		<table class="inLineTableDashBoardFieldset fieldsetTable">
			<tr>
				<td><html:text property="requestInput" />
					<div class="errorMessage">
						<html:errors property="requestInput" />
					</div> <html:submit styleClass="button">
						<bean:message key="interests.button.search" />
					</html:submit></td>
			</tr>
		</table>
	</html:form>
	<jsp:include page="/content/interests/ResultInterest.jsp" />
</fieldset>
