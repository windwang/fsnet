<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="communities.title.listYourCommunities" />
	</legend>

	<c:choose>
		<c:when test="${not empty requestScope.myCommunities}">
			<div class="space"></div>
			<script type="text/javascript">
				$(document).ready(
						function() {
							function pagination() {
								var nomTable = "myCommTables";
								var idColonneATrier = 1;
								var sensDeTri = "asc";
								var aoColumns = [ {
									"bSortable" : false
								}, null ];
								miseEnPageTable(nomTable, idColonneATrier,
										sensDeTri, aoColumns, false, 5);
							}
							pagination();

							function allSelect() {
								$('input[name=selectedCommunities]').attr(
										'checked', true);
							}

							function allNoSelect() {
								$('input[name=selectedCommunities]').attr(
										'checked', false);
							}

							$(".checkThemAll1").click(function() {
								if (this.checked) {
									allSelect();
								} else {
									allNoSelect();
								}
							});
						});
			</script>
			<html:form action="/DeleteMultiCommunities">
				<table id="myCommTables"
					class="tablesorter inLineTable fieldsetTableAppli ">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll1" /></th>
							<th><bean:message key="tableheader.communityname" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="2"><html:submit styleClass="button">
									<bean:message key="privatemessages.delete" />
								</html:submit></td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="community" items="${requestScope.myCommunities}">
							<tr class="content">
								<td><html:multibox property="selectedCommunities"
										value="${community.id}" /></td>
								<td><html:link action="/DisplayCommunity"
										title='${empty community.interests ? "" : community.interests}'>
										<html:param name="communityId" value="${community.id}" />
                            ${community.title}
                        </html:link> <c:choose>
										<c:when test="${fn:length(community.hubs) eq 0}">
                         		(<bean:message key="communities.hubs.notAny" /> hub)
                         	</c:when>
										<c:when test="${fn:length(community.hubs) eq 1}">
                         		(1 hub)
                         	</c:when>
										<c:when test="${fn:length(community.hubs) gt 1}">
                         		(${fn:length(community.hubs)} hubs)
                         	</c:when>
									</c:choose></td>
								<!-- onclick="confirmDelete2('deleteid${community.id}', '<bean:message key="message.confirmation.delete" />');">  -->
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</html:form>
		</c:when>
		<c:otherwise>
			<table class="inLineTable fieldsetTableAppli">
				<tr>
					<td><bean:message key="communities.noResult" /></td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>
