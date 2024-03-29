<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<link rel=stylesheet type="text/css" href="css/osx.css" />
<script type="text/javascript" src="js/osx.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="group.listGroups" />
	</legend>
	
	<c:choose>
		<c:when test="${! empty requestScope.groupsList}">
			<script type="text/javascript">
				$(document).ready(
						function pagination() {
							var nomTable = "searchGroupsTable";
							var idColonneATrier = 0;
							var sensDeTri = "asc";
							var aoColumns = [ null, {
								"bSortable" : false
							}, null, null, {
								"bSortable" : false
							} ];
							miseEnPageTable(nomTable, idColonneATrier,
									sensDeTri, aoColumns, false, 10);
						});
			</script>
			<table id="searchGroupsTable"
				class="tablesorter inLineTable fieldsetTableAppli tableStyle">
				<thead>
					<tr>
						<th><bean:message key="tableheader.group" /></th>
						<th><bean:message key="tableheader.created" /></th>
						<th><bean:message key="tableheader.name" /></th>
						<th><bean:message key="tableheader.firstname" /></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="group" items="${requestScope.groupsList}">
						<c:choose>
							<c:when test="${group.masterGroup.id == currentUserId}">
								<tr>
									<td><html:link action="/DisplayGroup">${group.name} 
		                			<html:param name="idGroup" value="${group.id}" />
										</html:link> <!--${group.name}--></td>
									<td></td>
									<td><ili:getSocialEntityInfosFirstname
											socialEntity="${group.masterGroup}" /></td>
									<td><ili:getSocialEntityInfosName
											socialEntity="${group.masterGroup}" /></td>
		
									<c:choose>
										<c:when test="${ group.group.isEnabled == false }">
											<td class="tableButton"><bean:message
													key="members.groupDisable" /></td>
										</c:when>
										<c:otherwise>
											<td class="tableButton"><html:link
													action="/SwitchStateGroup" styleClass="btn btn-inverse">
													<html:param name="groupSelected" value="${group.id}" />
													<c:choose>
														<c:when test="${group.isEnabled}">
															<bean:message key="members.searchDisable" />
														</c:when>
														<c:otherwise>
															<bean:message key="members.searchEnable" />
														</c:otherwise>
													</c:choose>
												</html:link></td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td>${group.name}</td>
									<td></td>
									<td><ili:getSocialEntityInfosFirstname
											socialEntity="${group.masterGroup}" /></td>
									<td><ili:getSocialEntityInfosName
											socialEntity="${group.masterGroup}" /></td>
									<td></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<table class="inLineTable fieldsetTableAppli">
				<tr>
					<td><bean:message key="group.noResult" />.</td>
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
