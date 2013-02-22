<%-- 
    Document   : Display research result
    Created on : 24 févr. 2012,
    Author     : Diane Dutartre <LiDaYuRan at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="s" uri="/struts-tags"%>


<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="research.title" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td><s:form action="Research" method="post">
					<div id="Research">
						<s:textfield property="searchText" />
						<br> <span class="checkboxSearch"> <s:checkbox
								property="selectedResearch" value="tous" /> <s:text name="research.all" />
						</span> <span class="checkboxSearch"> <s:checkbox
								property="selectedResearch" value="members" />  <s:text name="members.leftMenu" />
						</span> <span class="checkboxSearch"> <s:checkbox
								property="selectedResearch" value="consultations" />
						 <s:text name="consultations.leftMenu.my" />
						</span> <span class="checkboxSearch"> <s:checkbox
								property="selectedResearch" value="annonces" />  <s:text name="research.announces" />
						</span> <span class="checkboxSearch"> <s:checkbox
								property="selectedResearch" value="evenements" />  <s:text name="research.events" />
						</span> <span class="checkboxSearch"> <s:checkbox
								property="selectedResearch" value="communaute" />  <s:text name="research.communities" />
						</span><br />
						<s:submit styleClass="btn btn-inverse" />
					</div>
				</s:form></td>
		</tr>
	</table>
</fieldset>


	<c:if test="${searchMembers}">
			<fieldset class="fieldsetCadre">
				<legend>
					<s:text name="members.title.searchResult" />
				</legend>

				<c:choose>
					<c:when
						test="${ empty membersContactsResult && empty membersRequestedResult 
						&& empty membersAskedResult && empty membersResult}">
						<table class="inLineTable tableStyle">
							<tr>
								<td><s:text name="research.member.emptyList" /></td>
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
							class="tablesorter inLineTable tableStyle">
							<thead>
								<tr>
									<th></th>
									<th><s:text name="members.firstName" /></th>
									<th><s:text name="members.name" /></th>
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
										<td class="tableButton"><s:a
												action="/DisplayCreatePrivateMessage" styleClass="btn btn-inverse">
												<s:text name="showProfile.send" />
												<s:param name="receiver" value="%{member.email}" />
											</s:a> <s:a action="/DeleteContact" styleClass="btn btn-inverse">
												<s:text name="contact.button.delete" />
												<s:param name="entityDeleted" value="%{member.id}" />
											</s:a></td>
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
										<td class="tableButton"><s:a
												action="/DisplayCreatePrivateMessage" styleClass="btn btn-inverse">
												<s:text name="showProfile.send" />
												<s:param name="receiver" value="%{member.email}" />
											</s:a> <s:a action="/CancelAskContact" styleClass="btn btn-inverse">
												<s:param name="id" value="%{member.id}" />
												<s:text name="contacts.cancel" />
											</s:a></td>
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
										<td class="tableButton"><s:a
												action="/AcceptContact" styleClass="btn btn-inverse">
												<s:text name="members.button.accept" />
												<s:param name="entityAccepted" value="%{member.id}" />
											</s:a> <s:a action="/RefuseContact" styleClass="btn btn-inverse">
												<s:text name="members.button.refuse" />
												<s:param name="entityRefused" value="%{member.id}" />
											</s:a></td>
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
										<td class="tableButton"><s:a
												action="/ContactDemand" styleClass="btn btn-inverse">
												<s:text name="members.button.add" />
												<s:param name="entitySelected" value="%{member.id}" />
											</s:a></td>
									</tr>
								</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</fieldset>
		</c:if>


		<c:if test="${searchConsultations}">
			<fieldset class="fieldsetCadre">
				<legend>
					<s:text name="research.consultations.search" />
				</legend>

				<c:choose>
					<c:when test="${empty consultationsResult}">
						<table class="inLineTable tableStyle">
							<tr>
								<td><s:text name="research.consultation.emptyList" /></td>
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
							class="tablesorter inLineTable tableStyle">
							<thead>
								<tr>
									<th width="5%"></th>
									<th width="25%"><s:text
											name="tableheader.consultationname" /></th>
									<th width="20%"><s:text
											name="consultations.createdAtDate" /></th>
									<th><s:text name="tableheader.by" /></th>
									<th><s:text name="members.firstName" /></th>
									<th><s:text name="members.name" /></th>
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
										<td><s:a
												href="/DisplayAConsultation?id=%{consultation.id }">%{consultation.title }</s:a>
										<td><s:property value="creationDate" /></td>
										<td></td>
										<td><ili:getSocialEntityInfosFirstname
												socialEntity="${consultation.creator}" /></td>
										<td><ili:getSocialEntityInfosName
												socialEntity="${consultation.creator}" /></td>
										<td class="tableButton"
											onclick="confirmDelete2(${consultation.id}	)"><c:if
												test="${sessionScope.userId eq consultation.creator.id}">
												<s:form action="/DeleteAConsultation" method="post"
													styleId="${consultation.id}" styleClass="cursorPointer">
													<s:hidden name="id" value="%{consultation.id}" />
													<span class="btn btn-inverse"> <s:text
															name="consultations.button.delete" />
													</span>
												</s:form>
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
			<fieldset class="fieldsetCadre">
				<legend>
					<s:text name="research.annonces.search" />
				</legend>

				<c:choose>
					<c:when test="${empty annoncesResult}">
						<table class="inLineTable tableStyle">
							<tr>
								<td><s:text name="research.announce.emptyList" /></td>
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
							class="tablesorter inLineTable tableStyle">
							<thead>
								<tr>
									<th></th>
									<th><s:text name="tableheader.announcename" /></th>
									<th><s:text name="tableheader.by" /></th>
									<th><s:text name="members.firstName" /></th>
									<th><s:text name="members.name" /></th>
									<th><s:text name="tableheader.expirdate" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="announce" items="${annoncesResult}">
									<tr>
										<s:text id="idAnnounce" name="announce" var="id" />
										<td><c:import url="/FavoriteFragment.do">
												<c:param name="interactionId" value="${announce.id}" />
											</c:import></td>
										<td><s:a action="/DisplayAnnounce.do"
												paramId="idAnnounce" paramName="idAnnounce">
												<s:property value="title" />
											</s:a></td>
										<td></td>
										<td><ili:getSocialEntityInfosFirstname
												socialEntity="${announce.creator}" /></td>
										<td><ili:getSocialEntityInfosName
												socialEntity="${announce.creator}" /></td>
										<td><s:property value="endDate" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</fieldset>
		</c:if>



		<c:if test="${searchEvents}">
			<fieldset class="fieldsetCadre">
				<legend>
					<s:text name="research.events.search" />
				</legend>
				<c:choose>
					<c:when test="${empty eventsResult}">
						<table class="inLineTable tableStyle">
							<tr>
								<td><s:text name="research.event.emptyList" /></td>
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
							class="tablesorter inLineTable tableStyle">
							<thead>
								<tr>
									<th></th>
									<th><s:text name="tableheader.eventname" /></th>
									<th><s:text name="tableheader.willoccur" /></th>
									<th><s:text name="tableheader.expirdate" /></th>
									<th><s:text name="tableheader.by" /></th>
									<th><s:text name="tableheader.firstname" /></th>
									<th><s:text name="tableheader.name" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="event" items="${eventsResult}">
									<tr>
										<td><c:import url="/FavoriteFragment.do">
												<c:param name="interactionId" value="${event.id}" />
											</c:import></td>
										<td><s:a action="/DisplayEvent">
		                    ${event.title}
		                    <s:param name="eventId" value="%{event.id}" />
											</s:a> <span style="color: gray"> : <ili:substring
													beginIndex="0" endIndex="30">
													<ili:noxml>${event.content}</ili:noxml>
												</ili:substring>
										</span></td>
										<td class="left"><s:property value="startDate" /></td>
										<td class="left"><s:property value="endDate" /></td>
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
			<fieldset class="fieldsetCadre">
				<legend>
					<s:text name="research.communities.search" />
				</legend>
				<c:choose>
					<c:when test="${! empty communitiesResult}">
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
						}, null, null, {
							"bSortable" : false
						} ];
						miseEnPageTable(nomTable, idColonneATrier, sensDeTri,
								aoColumns, false, 10);
					});
		</script>
						<table id="seachCommTables"
							class="tablesorter inLineTable tableStyle">
							<thead>
								<tr>
									<th width="10%"></th>
									<th width="30%"><s:text
											name="tableheader.communityname" /></th>
									<th><s:text name="tableheader.by" /></th>
									<th width="20%"><s:text name="members.firstName" /></th>
									<th width="20%"><s:text name="members.name" /></th>
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
							<td><s:a href="/DisplayCommunity"
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
										<td></td>
										<td><ili:getSocialEntityInfosFirstname
												socialEntity="${community.creator}" /></td>
										<td><ili:getSocialEntityInfosName
												socialEntity="${community.creator}" /></td>
										<td class="tableButton"
											onclick="confirmDelete2('deleteid${community.id}');"><c:if
												test="${sessionScope.userId eq community.creator.id}">
												<s:form action="DeleteCommunity.do"
													styleId="deleteid${community.id}" method="post"
													styleClass="cursorPointer">
													<s:hidden name="communityId" value="%{community.id}" />
													<span class="btn btn-inverse"> <s:text
															name="communities.button.delete" />
													</span>
												</s:form>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</c:when>
					<c:otherwise>
						<table class="inLineTable tableStyle">
							<tr>
								<td><s:text name="research.community.emptyList" /></td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</fieldset>
		</c:if>
