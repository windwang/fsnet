<%-- 
	author : Bouragba Mohamed
	source : /fsnetWeb/src/main/webapp/content/announces/SearchAnnounce.jsp (Medhi Benzaghar)
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<bean:define id="searchAnnounce">
	<bean:message key="announce.placeholder.search" />
</bean:define>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="announce.title.searchAnnounce" />
	</legend>
	<html:form action="/Announces" method="get">
		<div>
			<table class="fieldsetTableAdmin">
				<tr>
					<td><html:text property="textSearchAnnounce"
							styleId="textSearchAnnounce" /> <ili:placeHolder
							id="textSearchAnnounce" value="${searchAnnounce}" /> <html:submit
							styleClass="button">
							<bean:message key="announce.button.search" />
						</html:submit> <html:messages id="message" /></td>
				</tr>
			</table>
			<div class="errorMessage">
				<html:errors />
			</div>
		</div>
	</html:form>
</fieldset>