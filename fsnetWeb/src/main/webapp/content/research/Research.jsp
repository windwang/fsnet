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


<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="research.title" />
	</legend>
	<table class="inLineTable tableStyle">
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
						<html:submit styleClass="btn btn-inverse" />
					</div>
				</html:form></td>
		</tr>
	</table>
</fieldset>


	<c:if test="${searchMembers}">
			<fieldset class="fieldsetCadre">
				<legend>
					<bean:message key="members.title.searchResult" />
				</legend>

				<c:choose>
					<c:when
						test="${ empty membersContactsResult && empty membersRequestedResult 
						&& empty membersAskedResult && empty membersResult}">
						<table class="inLineTable tableStyle">
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
							class="tablesorter inLineTable tableStyle">
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
										<td class="tableButton"><s:a
												action="/DisplayCreatePrivateMessage" styleClass="btn btn-inverse">
												<bean:message key="showProfile.send" />
												<html:param name="receiver" value="${member.email}" />
											</s:a> <s:a action="/DeleteContact" styleClass="btn btn-inverse">
												<bean:message key="contact.button.delete" />
												<html:param name="entityDeleted" value="${member.id}" />
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
												<bean:message key="showProfile.send" />
												<html:param name="receiver" value="${member.email}" />
											</s:a> <s:a action="/CancelAskContact" styleClass="btn btn-inverse">
												<html:param name="id" value="${member.id}" />
												<bean:message key="contacts.cancel" />
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
												<bean:message key="members.button.accept" />
												<html:param name="entityAccepted" value="${member.id}" />
											</s:a> <s:a action="/RefuseContact" styleClass="btn btn-inverse">
												<bean:message key="members.button.refuse" />
												<html:param name="entityRefused" value="${member.id}" />
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
												<bean:message key="members.button.add" />
												<html:param name="entitySelected" value="${member.id}" />
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
					<bean:message key="research.consultations.search" />
				</legend>

				<c:choose>
					<c:when test="${empty consultationsResult}">
						<table class="inLineTable tableStyle">
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
							class="tablesorter inLineTable tableStyle">
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
										<td><s:a
												action="/DisplayAConsultation?id=${consultation.id }">${consultation.title }</s:a>
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
													<s:hidden name="id" value="${consultation.id}" />
													<span class="btn btn-inverse"> <bean:message
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
			<fieldset class="fieldsetCadre">
				<legend>
					<bean:message key="research.annonces.search" />
				</legend>

				<c:choose>
					<c:when test="${empty annoncesResult}">
						<table class="inLineTable tableStyle">
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
							class="tablesorter inLineTable tableStyle">
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
										<td><s:a action="/DisplayAnnounce.do"
												paramId="idAnnounce" paramName="idAnnounce">
												<bean:write name="announce" property="title" />
											</s:a></td>
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
			<fieldset class="fieldsetCadre">
				<legend>
					<bean:message key="research.events.search" />
				</legend>
				<c:choose>
					<c:when test="${empty eventsResult}">
						<table class="inLineTable tableStyle">
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
							class="tablesorter inLineTable tableStyle">
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
										<td><s:a action="/DisplayEvent">
		                    ${event.title}
		                    <html:param name="eventId" value="${event.id}" />
											</s:a> <span style="color: gray"> : <ili:substring
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
			<fieldset class="fieldsetCadre">
				<legend>
					<bean:message key="research.communities.search" />
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
							<td><s:a action="/DisplayCommunity"
									title='${empty community.interests ? "" : community.interests}'>
									<html:param name="communityId" value="${community.id}" />
                            ${community.title}
                        </s:a> <c:choose>
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
													<s:hidden name="communityId" value="${community.id}" />
													<span class="btn btn-inverse"> <bean:message
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
						<table class="inLineTable tableStyle">
							<tr>
								<td><bean:message key="research.community.emptyList" /></td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</fieldset>
		</c:if>
