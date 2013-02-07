<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<fieldset class="fieldsetCadre">
	<legend>
		<c:import url="/FavoriteFragment.do">
			<c:param name="interactionId" value="${announce.id}" />
		</c:import>
		<bean:write name="announce" property="title" />
	</legend>

	<div class="interactionDisplay">
		<table class="inLineTable tableStyle">
			<tr class="authorDate">
				<td><bean:message key="announce.createdBy" /> <ili:getSocialEntityInfos
						socialEntity="${announce.creator}" />, <bean:message
						key="announce.expiryDate" /> <bean:write name="announce"
						property="endDate" format="dd/MM/yyyy HH:mm" /></td>
			</tr>
			<tr>
				<td>${announce.content}</td>
			</tr>

			<tr>
				<td colspan="2"><html:messages id="message" />
					<div class="errorMessage">
						<html:errors />
					</div></td>
			</tr>

			<tr>
				<td class="alignRight"><logic:present name="owner">
						<bean:define id="idAnnounce" name="announce" property="id" />
						<html:link action="/DisplayForModifyAnnounce" paramId="idAnnounce"
							paramName="idAnnounce" styleClass="btn btn-inverse">
							<bean:message key="announce.button.update" />
						</html:link>
						<html:link action="/DeleteAnnounce" paramId="idAnnounce"
							paramName="idAnnounce" styleClass="btn btn-inverse">
							<bean:message key="announce.button.delete" />
						</html:link>
					</logic:present></td>
			</tr>
		</table>
	</div>

	<c:set var="theInteraction" value="${announce}" scope="request" />
	<jsp:include page="/content/interactions/InteractionInfo.jsp" />
</fieldset>
