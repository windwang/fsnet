<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<fieldset class="fieldsetCadre">
	<legend>
		<c:if test="${not empty requestScope.Community}">
			<s:a action="/DisplayCommunity">
				<html:param name="communityId" value="${requestScope.Community.id}" />
            ${requestScope.Community.title}
        </s:a>
		</c:if>
		-&gt;
		<bean:message key="hubs.title.hubs" />
	</legend>
	<c:choose>
		<c:when test="${empty requestScope.listHubPaginator.resultList}">

			<table class="inLineTable tableStyle">
				<tr>
					<td><bean:message key="hubs.hubNotFound" /></td>
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
						<th><bean:message key="tableheader.hubname" /></th>
						<th><bean:message key="hubs.createdOn" /></th>
						<th><bean:message key="tableheader.by" /></th>
						<th><bean:message key="members.firstName" /></th>
						<th><bean:message key="members.name" /></th>
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
							<td><s:a action="/DisplayHub"
									title='${empty hub.interests? "" : hub.interests}'>
									<html:param name="hubId" value="${hub.id}" />
                    ${hub.title}
                </s:a> (${fn:length(hub.topics)} topics)</td>
							<td><bean:write name="hub" property="creationDate"
									format="dd/MM/yyyy" /></td>
							<td></td>
							<td><ili:getSocialEntityInfosFirstname
									socialEntity="${hub.creator}" /></td>
							<td style="width: 15%"><ili:getSocialEntityInfosName
									socialEntity="${hub.creator}" /></td>
							<td class="tableButton"><c:if
									test="${sessionScope.userId eq hub.creator.id}">
									<a class="btn btn-inverse"
										onclick="confirmDelete('DeleteHub.do?hubId='+${hub.id}+'&communityId='+${hub.community.id}, '<bean:message key="message.confirmation.delete" />');">
										<bean:message key="hubs.button.delete" />
									</a>
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>