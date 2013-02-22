<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set name="searchMessage">
	<s:text name="communities.placeholder.search" />
</s:set>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="communities.title.search" />
	</legend>
	<table id="SearchCommunity"
		class="inLineTable tableStyle">
		<s:form action="SearchCommunity" method="GET">
			<tr>
				<td>
					<s:textfield property="searchText" var="searchTexte" styleClass="search-query" />
					<ili:placeHolder id="searchTexte" value="%{searchMessage}" /> 
					<s:submit
						styleClass="btn btn-inverse">
						<s:text name="communities.button.search" />
					</s:submit>
				</td>
			</tr>
		</s:form>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="communities.title.listCommunities" />
	</legend>

	<c:choose>
		<c:when test="${! empty requestScope.communitiesSearch}">
			<div class="space"></div>
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
							}, null, null];
							miseEnPageTable(nomTable, idColonneATrier,
									sensDeTri, aoColumns, false, 10);
						});
			</script>
			<table id="seachCommTables"
				class="tablesorter inLineTable tableStyle">
				<thead>
					<tr>
						<th width="10%"></th>
						<th width="30%"><s:text name="tableheader.communityname" /></th>
						<th><s:text name="tableheader.by" /></th>
						<th width="20%"><s:text name="members.firstName" /></th>
						<th width="20%"><s:text name="members.name" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="community" items="${requestScope.communitiesSearch}">
						<tr class="content">
							<td>
								<c:import url="/FavoriteFragment.do">
									<c:param name="interactionId" value="%{community.id}" />
								</c:import>
							</td>
							<td>
								<s:a href="/DisplayCommunity"
									title='%{empty community.interests ? "" : community.interests}'>
									<s:param name="communityId" value="%{community.id}" />
                            %{community.title}
                        		</s:a> 
                        		<c:choose>
									<c:when test="%{fn:length(community.hubs) eq 0}">
                         		(<s:text name="communities.hubs.notAny" /> hub)
                         	</c:when>
									<c:when test="%{fn:length(community.hubs) eq 1}">
                         		(1 hub)
                         	</c:when>
									<c:when test="%{fn:length(community.hubs) gt 1}">
                         		(%{fn:length(community.hubs)} hubs)
                         	</c:when>
								</c:choose></td>
							<td></td>
							<td><ili:getSocialEntityInfosFirstname
									socialEntity="%{community.creator}" /></td>
							<td><ili:getSocialEntityInfosName
									socialEntity="%{community.creator}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</c:when>
		<c:otherwise>
			<table class="inLineTable tableStyle">
				<tr>
					<td><s:text name="communities.noResult" /></td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>

