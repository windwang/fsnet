<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="communities.title.listYourCommunities" />
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
										sensDeTri, aoColumns, false, 5, true);
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
			<s:form action="/DeleteMultiCommunities">
				<table id="myCommTables"
					class="tablesorter inLineTable tableStyle">
					<thead>
						<tr>
							<th class="thCheckbox"><input type="checkbox"
								name="selected" class="checkThemAll1" /></th>
							<th><s:text name="tableheader.communityname" /></th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="2">
								<s:submit styleClass="btn btn-inverse">
									<s:text name="privatemessages.delete" />
								</s:submit>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="community" items="${requestScope.myCommunities}">
							<tr class="content">
								<td>
									<s:checkboxlist list="selectedCommunities" value="%{community.id}" />				
								</td>
								<td>
									<s:a href="/DisplayCommunity"
										title='%{empty community.interests ? "" : community.interests}'>
										<s:param name="communityId" value="%{community.id}" />
                           			 ${community.title}
                        		</s:a> <c:choose>
										<c:when test="${fn:length(community.hubs) eq 0}">
                         		(<s:text name="communities.hubs.notAny" /> hub)
                         	</c:when>
										<c:when test="${fn:length(community.hubs) eq 1}">
                         		(1 hub)
                         	</c:when>
										<c:when test="${fn:length(community.hubs) gt 1}">
                         		(${fn:length(community.hubs)} hubs)
                         	</c:when>
									</c:choose></td>
								<!-- onclick="confirmDelete2('deleteid${community.id}', '<s:text name="message.confirmation.delete" />');">  -->
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</s:form>
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
