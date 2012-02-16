<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<bean:define id="searchMessage">
	<bean:message key="community.search" />
</bean:define>

<h3>
	<bean:message key="communities.search" />
</h3>
<table class="inLineTable">
	<tr>
		<td><html:form action="SearchCommunity" method="GET">
				<div id="SearchCommunity">
					<html:text property="searchText" styleId="searchTexte" />
					<ili:placeHolder id="searchTexte" value="${searchMessage}" />
					<html:submit styleClass="button">
						<bean:message key="communities.searchButton" />
					</html:submit>
				</div>
			</html:form></td>
	</tr>
</table>

<h3>
	<bean:message key="communities.listCommunities" />
</h3>

<c:choose>
	<c:when test="${! empty requestScope.communitiesSearch}">
		<script type="text/javascript">
			$(document).ready(
					function pagination() {
						var nomTable = "seachCommTables";
						var idColonneATrier = 1;
						var sensDeTri = "asc";
						var aoColumns = [ {
							"bSortable" : false
						}, null, {
							"bSortable" : false
						}, null, null, {
							"bSortable" : false
						} ];
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, false);
					});
		</script>
		<table id="seachCommTables" class="tablesorter inLineTable">
			<thead>
				<tr>
					<th width="10%"></th>
					<th width="30%"><bean:message key="tableheader.communityname" /></th>
					<th><bean:message key="tableheader.by" /></th>
					<th width="20%"><bean:message key="members.firstName" /></th>
					<th width="20%"><bean:message key="members.name" /></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="community" items="${requestScope.communitiesSearch}">
					<tr class="content">
						<td><c:import url="/FavoriteFragment.do">
								<c:param name="interactionId" value="${community.id}" />
							</c:import></td>
						<td><html:link action="/DisplayCommunity"
								title='${empty community.interests ? "" : community.interests}'>
								<html:param name="communityId" value="${community.id}" />
                            ${community.title}
                        </html:link> <c:choose>
								<c:when test="${fn:length(community.hubs) eq 0}">
                         		(<bean:message key="communities.notany.hubs" /> hub)
                         	</c:when>
								<c:when test="${fn:length(community.hubs) eq 1}">
                         		(1 hub)
                         	</c:when>
								<c:when test="${fn:length(community.hubs) gt 1}">
                         		(${fn:length(community.hubs)} hubs)
                         	</c:when>
							</c:choose></td>
						<td></td>
						<td><ili:getSocialEntityInfosFirstname
								socialEntity="${community.creator}" /></td>
						<td><ili:getSocialEntityInfosName
								socialEntity="${community.creator}" /></td>
						<td class="tableButton"
							onclick="confirmDelete2('deleteid${community.id}');"><c:if
								test="${sessionScope.userId eq community.creator.id}">
								<html:form action="DeleteCommunity.do"
									styleId="deleteid${community.id}" method="post"
									styleClass="cursorPointer">
									<html:hidden property="communityId" value="${community.id}" />
									<span class="button"> <bean:message
											key="communities.delete" />
									</span>
								</html:form>
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</c:when>
	<c:otherwise>
		<table class="inLineTable">
			<tr>
				<td><bean:message key="communities.noResult" /></td>
			</tr>
		</table>
	</c:otherwise>
</c:choose>


