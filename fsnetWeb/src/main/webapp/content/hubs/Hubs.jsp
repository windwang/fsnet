<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<fieldset class="fieldsetCadre">
	<legend>
		<c:import url="/FavoriteFragment.do">
			<c:param name="interactionId" value="${requestScope.Community.id}" />
		</c:import>

		<bean:write name="Community" property="title" />
	</legend>

	<c:set var="theInteraction" value="${Community}" scope="request" />
	<jsp:include page="/content/interactions/LargeInteractionInfo.jsp" />
</fieldset>
<div class="clear"></div>

<%@ include file="SearchHub.jsp" %>
<%@ include file="ListHubs.jsp" %>
