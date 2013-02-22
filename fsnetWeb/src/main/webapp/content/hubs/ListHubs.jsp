<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<fieldset class="fieldsetCadre">
	<legend>
		<c:if test="${not empty requestScope.Community}">
			<s:a href="/DisplayCommunity">
				<s:param name="communityId" value="%{requestScope.Community.id}" />
            ${requestScope.Community.title}
        </s:a>
		</c:if>
		-&gt;
		<s:text name="hubs.title.hubs" />
	</legend>
	<c:choose>
		<c:when test="${empty requestScope.listHubPaginator.resultList}">

			<table class="inLineTable tableStyle">
				<tr>
					<td><s:text name="hubs.hubNotFound" /></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<div class="space"></div>
			<script type="text/javascript">
			$(document).ready(
					function pagination() {
						var nomTable = "tablehub";
						var idColonneATrier = 2;
						var sensDeTri = "desc";
						var aoColumns = [ {
							"bSortable" : false
						}, null,  {
							"sType" : "date-euro"
						} ,{
							"bSortable" : false
						},  null, null, {
							"bSortable" : false
						}];
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, false, 10);
					});
		</script>
			<table id="tablehub" class="tablesorter inLineTable tableStyle">
				<thead>
					<tr>
						<th></th>
						<th><s:text name="tableheader.hubname" /></th>
						<th><s:text name="hubs.createdOn" /></th>
						<th><s:text name="tableheader.by" /></th>
						<th><s:text name="members.firstName" /></th>
						<th><s:text name="members.name" /></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="hub"
						items="${requestScope.listHubPaginator.resultList}">
						<tr>
							<td><c:import url="/FavoriteFragment.do">
									<c:param name="interactionId" value="${hub.id}" />
								</c:import></td>
							<td><s:a href="/DisplayHub" title='%{empty hub.interests? "" : hub.interests}'>
									<s:param name="hubId" value="%{hub.id}" />
                    ${hub.title}
                </s:a> (${fn:length(hub.topics)} topics)</td>
							<td>
							<s:property value="creationDate"/></td>
							<td></td>
							<td><ili:getSocialEntityInfosFirstname
									socialEntity="${hub.creator}" /></td>
							<td style="width: 15%"><ili:getSocialEntityInfosName
									socialEntity="${hub.creator}" /></td>
							<td class="tableButton"><c:if
									test="${sessionScope.userId eq hub.creator.id}">
									<a class="btn btn-inverse"
										onclick="confirmDelete('DeleteHub.do?hubId='+${hub.id}+'&communityId='+${hub.community.id}, '<bean:message key="message.confirmation.delete" />');">
										<s:text name="hubs.button.delete" />
									</a>
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>