<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<bean:define id="searchMember">
	<bean:message key="members.placeholder.search" />
</bean:define>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="members.search" />
	</legend>

	<html:form action="SearchMember">
		<table class="inLineTable fieldsetTableAdmin">
			<tr>
				<td><html:text property="searchText" styleId="searchTexte" />
					<ili:placeHolder id="searchTexte" value="${searchMember}" /> <html:submit
						styleClass="button">
						<bean:message key="members.searchButton" />
					</html:submit></td>
			</tr>
		</table>
	</html:form>
</fieldset>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="members.listMembers" />
	</legend>

	<c:choose>
		<c:when test="${not empty requestScope.membersList}">
			<table id="memberTable"
				class="inLineTable fieldsetTableAdmin tablesorter">
				<thead>
					<tr>
						<th><bean:message key="tableheader.name" /></th>
						<th><bean:message key="tableheader.firstname" /></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="member" items="${requestScope.membersList}">
						<tr class="content">
							<td><s:a action="/DisplayMember">${member.name}
                		<html:param name="idMember" value="${member.id}" />
								</s:a></td>
							<td><s:a action="/DisplayMember">${member.firstName}
                		<html:param name="idMember" value="${member.id}" />
								</s:a></td>
							<td class="tableButton"><c:choose>
									<c:when test="${member.group.isEnabled}">
										<s:a action="/SwitchState" styleClass="button">
											<html:param name="entitySelected" value="${member.id}" />
											<c:choose>
												<c:when test="${member.isEnabled}">
													<bean:message key="members.searchDisable" />
												</c:when>
												<c:otherwise>
													<bean:message key="members.searchEnable" />
												</c:otherwise>
											</c:choose>
										</s:a>
									</c:when>
									<c:when test="${ member.group.isEnabled == false }">
										<bean:message key="members.groupDisable" />
									</c:when>
									<c:otherwise>
										<bean:message key="members.groupNull" />
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<table class="inLineTable fieldsetTableAdmin">
				<tr>
					<td><bean:message key="members.noResult" /></td>
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

<script type="text/javascript">
	$(document).ready(
			function pagination() {
				var nomTable = "memberTable";
				var idColonneATrier = 0;
				var sensDeTri = "asc";
				var aoColumns = [ null, null, {
					"bSortable" : false
				} ];
				miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
						aoColumns, false);
			});
</script>

