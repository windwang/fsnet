<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<link rel=stylesheet type="text/css" href="css/osx.css" />
<script type="text/javascript" src="js/osx.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="members.search" />
	</legend>
	<table class="inLineTable tableStyle">
		<s:form action="SearchMemberAdmin">
		<tr>
			<td>
				<s:textfield property="searchText" styleClass="search-query" />
				<s:submit styleClass="btn btn-inverse">
					<s:text name="members.searchButton" />
				</s:submit>
			</td>
		</tr>
		</s:form>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="members.listMembers" />
	</legend>

	<c:choose>
		<c:when test="${not empty requestScope.membersList}">

			<script type="text/javascript">
				$(document).ready(
						function pagination() {
							var nomTable = "searchmemberstable";
							var idColonneATrier = 0;
							var sensDeTri = "asc";
							var aoColumns = [ null, null, null, {
								"bSortable" : false
							} ];
							miseEnPageTable(nomTable, idColonneATrier,
									sensDeTri, aoColumns, false, 10);

						});
			</script>
			<table id="searchmemberstable"
				class="tablesorter inLineTable tableStyle">
				<thead>
					<tr>
						<th><s:text name="tableheader.firstname" /></th>
						<th><s:text name="tableheader.name" /></th>
						<th><s:text name="tableheader.group" /></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="member" items="${requestScope.membersList}">
						<tr class="content">
							<td><s:url action="/DisplayMember">${member.firstName}
                		<s:param name="idMember" value="%{member.id}" />
								</s:url></td>
							<td><s:url action="/DisplayMember">${member.name}
                		<s:param name="idMember" value="%{member.id}" />
								</s:url></td>
							<td><ili:getSocialGroupInfos socialGroup="${member.group}" /></td>
							<td class="tableButton"><c:choose>
									<c:when test="${member.group.isEnabled}">
										<s:url action="/SwitchState" styleClass="btn btn-inverse">
											<s:param name="entitySelected" value="%{member.id}" />
											<c:choose>
												<c:when test="${member.isEnabled}">
													<s:text name="members.searchDisable" />
												</c:when>
												<c:otherwise>
													<s:text name="members.searchEnable" />
												</c:otherwise>
											</c:choose>
										</s:url>
									</c:when>
									<c:when test="${ member.group.isEnabled == false }">
										<s:text name="members.groupDisable" />
									</c:when>
									<c:otherwise>
										<s:text name="members.groupNull" />
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<table class="inLineTable fieldsetTableAppli tableStyle">
				<tr>
					<td><s:text name="members.noResult" /></td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>

<c:if test="${!empty success}">
	<script type="text/javascript">
		jQuery(function() {
			popup();
		});
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


