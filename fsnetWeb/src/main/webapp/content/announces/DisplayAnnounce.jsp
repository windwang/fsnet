<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<fieldset class="fieldsetCadre">
	<legend>
		<c:import url="/FavoriteFragment.do">
			<c:param name="interactionId" value="${announce.id}" />
		</c:import>
		<s:property value="announce" default="title" />
	</legend>

	<div class="interactionDisplay">
		<table class="inLineTable tableStyle">
			<tr class="authorDate">
				<td><s:text name="announce.createdBy" /> <ili:getSocialEntityInfos
						socialEntity="${announce.creator}" />, <s:text
						name="announce.expiryDate" /> <s:property value="announce"
						default="endDate" format="dd/MM/yyyy HH:mm" /></td><td>
			</tr>
			<tr>
				<td>${announce.content}</td>
			</tr>

			<tr>
				<td colspan="2">
<!-- 				<html:messages id="message" /> -->
				<s:text name="message" var="message" />
<!-- 					<div class="errorMessage"> -->
<!-- 						<html:errors /> -->
<!-- 					</div></td> -->
			</tr>

			<tr>

				<td class="alignRight">
						<s:set id="idAnnounce" name="announce" var="id" />
						<s:a action="/DisplayForModifyAnnounce" paramId="idAnnounce"
							paramName="idAnnounce" styleClass="btn btn-inverse">
							<s:text name="announce.button.update" />
						</s:a>
						<s:a action="/DeleteAnnounce" paramId="idAnnounce"
							paramName="idAnnounce" styleClass="btn btn-inverse">
							<s:text name="announce.button.delete" />
						</s:a>
					</td>

			</tr>
		</table>
	</div>

	<c:set var="theInteraction" value="${announce}" scope="request" />
	<jsp:include page="/content/interactions/InteractionInfo.jsp" />
</fieldset>
