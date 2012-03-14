<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<link rel=stylesheet type="text/css" href="css/osx.css" />
<!-- <script type="text/javascript" src="js/jquery.js"></script>-->
<script type="text/javascript" src="js/jquery.simplemodal.js"></script>
<script type="text/javascript" src="js/osx.js"></script>

<bean:define id="searchCommunitie">
	<bean:message key="communities.placeholder.search" />
</bean:define>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="communities.title.search" />
	</legend>

	<table class="inLineTable fieldsetTableAdmin">
		<html:form action="SearchCommunity">
			<tr>
				<td><html:text property="searchText" styleId="searchTexte" />
					<ili:placeHolder id="searchTexte" value="${searchCommunitie}" /> <html:submit
						styleClass="button">
						<bean:message key="communities.button.search" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="communities.title.listCommunities" />
	</legend>

	<c:choose>
		<c:when test="${not empty requestScope.communitiesList}">
			<table id="communitiesTable"
				class="tablesorter inLineTable fieldsetTableAdmin">
				<thead>
					<tr>
						<th><bean:message key="tableheader.communityname" /></th>
						<th><bean:message key="tableheader.by" /></th>
						<th><bean:message key="members.firstName" /></th>
						<th><bean:message key="members.name" /></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="community" items="${requestScope.communitiesList}">
						<tr class="content">
							<td>${community.title}</td>
							<td></td>
							<td><html:link action="/DisplayMember">
									<html:param name="idMember" value="${community.creator.id}" />
	                    ${community.creator.firstName}
	                </html:link></td>
							<td><html:link action="/DisplayMember">
									<html:param name="idMember" value="${community.creator.id}" />
	                    ${community.creator.name}
	                </html:link></td>
							<td class="tableButton"><a class="button"
								onclick="confirmDelete('DeleteCommunity.do?communityId='+${community.id})">
									<bean:message key="communities.button.delete" />
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<table class="inLineTable fieldsetTableAdmin">
				<tr>
					<td><bean:message key="communities.noResult" /></td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>

<c:if test="${!empty success}">
	<script type="text/javascript">
		jQuery(function () { popup(); });
		success = null;
	</script>
	<div id="osx-modal-content" class="simplemodal-data">
		<div id="osx-modal-title">Message</div>
		<div class="close">
			<a class="simplemodal-close" href="#">X</a>
		</div>
		<div id="osx-modal-data">
			<p>
				<c:out value="${success}" />
			</p>
		</div>
	</div>
</c:if>

<script type="text/javascript">
	$(document).ready(
		function pagination() {
			var nomTable = "communitiesTable";
			var idColonneATrier = 0;
			var sensDeTri = "asc";
			var aoColumns = [ null,{
				"bSortable" : false
			}, null, null,{
				"bSortable" : false
			}];
			miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
					aoColumns, false);
		});
</script>

