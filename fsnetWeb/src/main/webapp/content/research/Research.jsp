<%-- 
    Document   : Display research result
    Created on : 24 févr. 2012,
    Author     : Diane Dutartre <LiDaYuRan at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="research.title" />
	</legend>
	<table class="inLineTable fieldsetTableAppli">
		<tr>
			<td><html:form action="Research" method="post">
					<div id="Research">
						<html:text property="searchText" />
						<br> <span class="checkboxSearch"> <html:multibox
								property="selectedResearch" value="tous" /> <bean:message key="research.all" />
						</span> <span class="checkboxSearch"> <html:multibox
								property="selectedResearch" value="members" />  <bean:message key="members.leftMenu" />
						</span> <span class="checkboxSearch"> <html:multibox
								property="selectedResearch" value="consultations" />
						 <bean:message key="consultations.leftMenu.my" />
						</span> <span class="checkboxSearch"> <html:multibox
								property="selectedResearch" value="annonces" />  <bean:message key="research.announces" />
						</span> <span class="checkboxSearch"> <html:multibox
								property="selectedResearch" value="evenements" />  <bean:message key="research.events" />
						</span> <span class="checkboxSearch"> <html:multibox
								property="selectedResearch" value="communaute" />  <bean:message key="research.communities" />
						</span><br />
						<html:submit styleClass="button" />
					</div>
				</html:form></td>
		</tr>
	</table>
</fieldset>


	<c:if test="${searchMembers}">
			<fieldset class="fieldsetAppli">
				<legend class="legendHome">
					<bean:message key="members.title.searchResult" />
				</legend>

				<c:choose>
					<c:when
						test="${ empty membersContactsResult && empty membersRequestedResult 
						&& empty membersAskedResult && empty membersResult}">
						<table class="inLineTable fieldsetTableAppli">
							<tr>
								<td><bean:message key="research.member.emptyList" /></td>
							</tr>
						</table>
					</c:when>


					<c:otherwise>
						<script type="text/javascript">
							$(document).ready(
									function pagination() {
										var nomTable = "researchMembers";
										var idColonneATrier = 1;
										var sensDeTri = "asc";
										var aoColumns = [ {
											"bSortable" : false
										}, null, null, {
											"bSortable" : false
										}, ];
										miseEnPageTable(nomTable,
												idColonneATrier, sensDeTri,
												aoColumns, false, 10);
									});
						</script>

						<table id="researchMembers"
							class="tablesorter inLineTable fieldsetTableAppli">
							<thead>
								<tr>
									<th></th>
									<th><bean:message key="members.firstName" /></th>
									<th><bean:message key="members.name" /></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="member" items="${membersContactsResult}">
									<tr class="content">
										<td class="miniatureContainer"><ili:getMiniature
												socialEntity="${member}" /></td>
										<td><ili:getSocialEntityInfosFirstname
												socialEntity="${member}" /></td>
										<td><ili:getSocialEntityInfosName
												socialEntity="${member}" /></td>
										<td class="tableButton"><html:link
												action="/DisplayCreatePrivateMessage" styleClass="button">
												<bean:message key="showProfile.send" />
												<html:param name="receiver" value="${member.email}" />
											</html:link> <html:link action="/DeleteContact" styleClass="button">
												<bean:message key="contact.button.delete" />
												<html:param name="entityDeleted" value="${member.id}" />
											</html:link></td>
									</tr>
								</c:forEach>
								<c:forEach var="member" items="${membersRequestedResult}">
									<tr class="content">
										<td class="miniatureContainer"><ili:getMiniature
												socialEntity="${member}" /></td>
										<td><ili:getSocialEntityInfosFirstname
												socialEntity="${member}" /></td>
										<td><ili:getSocialEntityInfosName
												socialEntity="${member}" /></td>
										<td class="tableButton"><html:link
												action="/DisplayCreatePrivateMessage" styleClass="button">
												<bean:message key="showProfile.send" />
												<html:param name="receiver" value="${member.email}" />
											</html:link> <html:link action="/CancelAskContact" styleClass="button">
												<html:param name="id" value="${member.id}" />
												<bean:message key="contacts.cancel" />
											</html:link></td>
									</tr>
								</c:forEach>
								<c:forEach var="member" items="${membersAskedResult}">
									<tr class="content">
										<td class="miniatureContainer"><ili:getMiniature
												socialEntity="${member}" /></td>
										<td><ili:getSocialEntityInfosFirstname
												socialEntity="${member}" /></td>
										<td><ili:getSocialEntityInfosName
												socialEntity="${member}" /></td>
										<td class="tableButton"><html:link
												action="/AcceptContact" styleClass="button">
												<bean:message key="members.button.accept" />
												<html:param name="entityAccepted" value="${member.id}" />
											</html:link> <html:link action="/RefuseContact" styleClass="button">
												<bean:message key="members.button.refuse" />
												<html:param name="entityRefused" value="${member.id}" />
											</html:link></td>
									</tr>
								</c:forEach>
								<c:forEach var="member" items="${membersResult}">
									<tr class="content">
										<td class="miniatureContainer"><ili:getMiniature
												socialEntity="${member}" /></td>
										<td><ili:getSocialEntityInfosFirstname
												socialEntity="${member}" /></td>
										<td><ili:getSocialEntityInfosName
												socialEntity="${member}" /></td>
										<td class="tableButton"><html:link
												action="/ContactDemand" styleClass="button">
												<bean:message key="members.button.add" />
												<html:param name="entitySelected" value="${member.id}" />
											</html:link></td>
									</tr>
								</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</fieldset>
		</c:if>


		<c:if test="${searchConsultations}">
			<fieldset class="fieldsetAppli">
				<legend class="legendHome">
					<bean:message key="research.consultations.search" />
				</legend>

				<c:choose>
					<c:when test="${empty consultationsResult}">
						<table class="inLineTable fieldsetTableAppli">
							<tr>
								<td><bean:message key="research.consultation.emptyList" /></td>
							</tr>
						</table>
					</c:when>
					<c:otherwise>

						<script type="text/javascript">
	$(document).ready(function pagination() {
		var nomTable = "searchConsults";
		var idColonneATrier = 2;
		var sensDeTri = "desc";
		var aoColumns = [ {
			"bSortable" : false
		}, null, {
			"sType" : "date-euro"
		} ,{
			"bSortable" : false
		}, null, null, {
			"bSortable" : false
		}];
		miseEnPageTable(nomTable, idColonneATrier, sensDeTri, aoColumns, false, 10);
	});
</script>
						<table id="searchConsults"
							class="tablesorter inLineTable fieldsetTableAppli">
							<thead>
								<tr>
									<th width="5%"></th>
									<th width="25%"><bean:message
											key="tableheader.consultationname" /></th>
									<th width="20%"><bean:message
											key="consultations.createdAtDate" /></th>
									<th><bean:message key="tableheader.by" /></th>
									<th><bean:message key="members.firstName" /></th>
									<th><bean:message key="members.name" /></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="consultation"
									items="${requestScope.consultationsResult}">
									<tr>
										<td><c:import url="/FavoriteFragment.do">
												<c:param name="interactionId" value="${consultation.id}" />
											</c:import></td>
										<td><html:link
												action="/DisplayAConsultation?id=${consultation.id }">${consultation.title }</html:link>
										<td><bean:write name="consultation"
												property="creationDate" formatKey="date.format" /></td>
										<td></td>
										<td><ili:getSocialEntityInfosFirstname
												socialEntity="${consultation.creator}" /></td>
										<td><ili:getSocialEntityInfosName
												socialEntity="${consultation.creator}" /></td>
										<td class="tableButton"
											onclick="confirmDelete2(${consultation.id}	)"><c:if
												test="${sessionScope.userId eq consultation.creator.id}">
												<html:form action="/DeleteAConsultation" method="post"
													styleId="${consultation.id}" styleClass="cursorPointer">
													<html:hidden property="id" value="${consultation.id}" />
													<span class="button"> <bean:message
															key="consultations.button.delete" />
													</span>
												</html:form>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</fieldset>
		</c:if>



		<c:if test="${searchAnnonce}">
			<fieldset class="fieldsetAppli">
				<legend class="legendHome">
					<bean:message key="research.annonces.search" />
				</legend>

				<c:choose>
					<c:when test="${empty annoncesResult}">
						<table class="inLineTable fieldsetTableAppli">
							<tr>
								<td><bean:message key="research.announce.emptyList" /></td>
							</tr>
						</table>
					</c:when>
					<c:otherwise>

						<script type="text/javascript">
			$(document).ready(
					function pagination() {
						var nomTable = "annonceTable";
						var idColonneATrier = 5;
						var sensDeTri = "desc";
						var aoColumns = [ {
							"bSortable" : false
						}, null,{
							"bSortable" : false
						}, null, null, {
							"sType" : "date-euro"
						} ];
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, false, 10);

					});
		</script>
						<table id="annonceTable"
							class="tablesorter inLineTable fieldsetTableAppli">
							<thead>
								<tr>
									<th></th>
									<th><bean:message key="tableheader.announcename" /></th>
									<th><bean:message key="tableheader.by" /></th>
									<th><bean:message key="members.firstName" /></th>
									<th><bean:message key="members.name" /></th>
									<th><bean:message key="tableheader.expirdate" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="announce" items="${annoncesResult}">
									<tr>
										<bean:define id="idAnnounce" name="announce" property="id" />
										<td><c:import url="/FavoriteFragment.do">
												<c:param name="interactionId" value="${announce.id}" />
											</c:import></td>
										<td><html:link action="/DisplayAnnounce.do"
												paramId="idAnnounce" paramName="idAnnounce">
												<bean:write name="announce" property="title" />
											</html:link></td>
										<td></td>
										<td><ili:getSocialEntityInfosFirstname
												socialEntity="${announce.creator}" /></td>
										<td><ili:getSocialEntityInfosName
												socialEntity="${announce.creator}" /></td>
										<td><bean:write name="announce" property="endDate"
												format="dd/MM/yyyy HH:mm" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</fieldset>
		</c:if>



		<c:if test="${searchEvents}">
			<fieldset class="fieldsetAppli">
				<legend class="legendHome">
					<bean:message key="research.events.search" />
				</legend>
				<c:choose>
					<c:when test="${empty eventsResult}">
						<table class="inLineTable fieldsetTableAppli">
							<tr>
								<td><bean:message key="research.event.emptyList" /></td>
							</tr>
						</table>
					</c:when>
					<c:otherwise>
						<div class="space"></div>
						<script type="text/javascript">
			$(document).ready(
					function pagination() {
						var nomTable = "eventsTable";
						var idColonneATrier = 2;
						var sensDeTri = "desc";
						var aoColumns = [ {
							"bSortable" : false
						}, null, {
							"sType" : "date"
						}, {
							"sType" : "date"
						}, {
							"bSortable" : false
						}, null, null ];
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, false, 10);
					});
		</script>
						<table id="eventsTable"
							class="tablesorter inLineTable fieldsetTableAppli">
							<thead>
								<tr>
									<th></th>
									<th><bean:message key="tableheader.eventname" /></th>
									<th><bean:message key="tableheader.willoccur" /></th>
									<th><bean:message key="tableheader.expirdate" /></th>
									<th><bean:message key="tableheader.by" /></th>
									<th><bean:message key="tableheader.firstname" /></th>
									<th><bean:message key="tableheader.name" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="event" items="${eventsResult}">
									<tr>
										<td><c:import url="/FavoriteFragment.do">
												<c:param name="interactionId" value="${event.id}" />
											</c:import></td>
										<td><html:link action="/DisplayEvent">
		                    ${event.title}
		                    <html:param name="eventId" value="${event.id}" />
											</html:link> <span style="color: gray"> : <ili:substring
													beginIndex="0" endIndex="30">
													<ili:noxml>${event.content}</ili:noxml>
												</ili:substring>
										</span></td>
										<td class="left"><bean:write name="event"
												property="startDate" format="dd/MM/yyyy HH:mm" /></td>
										<td class="left"><bean:write name="event"
												property="endDate" format="dd/MM/yyyy HH:mm" /></td>
										<td></td>
										<td><ili:getSocialEntityInfosFirstname
												socialEntity="${event.creator}" /></td>
										<td><ili:getSocialEntityInfosName
												socialEntity="${event.creator}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</fieldset>
		</c:if>




		<c:if test="${searchCommunauties}">
			<fieldset class="fieldsetAppli">
				<legend class="legendHome">
					<bean:message key="research.communities.search" />
				</legend>
				<c:choose>
					<c:when test="${! empty communitiesResult}">
						<div class="space"></div>
						<script type="text/javascript">
					$(document).ready(
					function pagination() {
						var nomTable = "seachCommTables";
						searchCommunauties	var idColonneATrier = 1;
						var sensDeTri = "asc";
						var aoColumns = [ {
							"bSortable" : false
						}, null, {
							"bSortable" : false
						}, null, null, {
							"bSortable" : false
						} ];
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, false, 10);
					});
		</script>
						<table id="seachCommTables"
							class="tablesorter inLineTable fieldsetTableAppli">
							<thead>
								<tr>
									<th width="10%"></th>
									<th width="30%"><bean:message
											key="tableheader.communityname" /></th>
									<th><bean:message key="tableheader.by" /></th>
									<th width="20%"><bean:message key="members.firstName" /></th>
									<th width="20%"><bean:message key="members.name" /></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="community"
									items="${communitiesResult}">
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
                         		(<bean:message key="communities.hubs.notAny" /> hub)
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
															key="communities.button.delete" />
													</span>
												</html:form>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</c:when>
					<c:otherwise>
						<table class="inLineTable fieldsetTableAppli">
							<tr>
								<td><bean:message key="research.community.emptyList" /></td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</fieldset>
		</c:if>
