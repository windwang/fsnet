<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="showProfile.title">
			<s:param>${watchedProfile.firstName} ${watchedProfile.name}</s:param>
		</s:text>
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td>
				<table class="inLineTable tableStyle">
					<c:if
						test="${watchedProfile.email != null && not empty watchedProfile.email }">
						<tr>
							<th><s:text name="updateProfile.email" /></th>
							<td><s:a href="mailto:%{watchedProfile.email}">
                    ${watchedProfile.email}
                </s:a></td>
						</tr>
					</c:if>
					<c:if
						test="${watchedProfile.address.address != null && not empty watchedProfile.address.address }">
						<tr>
							<th><s:text name="updateProfile.adress" /></th>
							<td>${watchedProfile.address.address}</td>
						</tr>
					</c:if>
					<c:if
						test="${watchedProfile.address.city != null &&  not empty watchedProfile.address.city}">
						<tr>
							<th><s:text name="updateProfile.city" /></th>
							<td>${watchedProfile.address.city}</td>
						</tr>
					</c:if>
					<c:if test="${birthDay != null && not empty birthDay }">
						<tr>
							<th><s:text name="updateProfile.dateOfBirth" /></th>
							<td>${birthDay}</td>
						</tr>
					</c:if>
					<c:if
						test="${watchedProfile.profession != null && not empty  watchedProfile.profession}">
						<tr>
							<th><s:text name="updateProfile.job" /></th>
							<td>${watchedProfile.profession}</td>
						</tr>
					</c:if>
					<c:if
						test="${watchedProfile.sex != null && not empty  watchedProfile.sex}">
						<tr>
							<th><s:text name="updateProfile.sexe" /></th>
							<td><s:text name="updateProfile.sexe.%{watchedProfile.sex}" /></td>
						</tr>
					</c:if>
					<c:if
						test="${watchedProfile.phone != null && not empty watchedProfile.phone}">
						<tr>
							<th><s:text name="updateProfile.phone" /></th>
							<td>${watchedProfile.phone}</td>
						</tr>
					</c:if>
				</table>
			</td>
		</tr>
		<tr>
			<td><img src="avatar/${watchedProfile.id}.png"
				style="float: right;" alt="Avatar" /></td>
		</tr>
	</table>
</fieldset>


<div class="placeButton">
	<c:if
		test="${watchedProfile.id != currentUser.id && !alreadyInContact}">
		<s:a action="/ContactDemand" styleClass="btn btn-inverse">
			<s:text name="showProfile.ask" />
			<s:param name="entitySelected" value="%{watchedProfile.id}" />
		</s:a>
	</c:if>

	<c:if test="${watchedProfile.id != currentUser.id}">
		<s:a action="/DisplayCreatePrivateMessage"
			styleClass="btn btn-inverse">
			<s:text name="showProfile.send" />
			<s:param name="receiver" value="%{watchedProfile.email}" />
		</s:a>
	</c:if>
	<c:if test="${watchedProfile.id != currentUser.id && isLogged}">
		<a class="btn btn-inverse"
			onclick="javascript:chatWith('%{watchedProfile.name}_%{watchedProfile.id}','habib2@master11.com')">
			<s:text name="showProfile.chat" />
		</a>
	</c:if>
</div>

<div class="clear"></div>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="showInterest.title">
			<s:param>${watchedProfile.firstName} ${watchedProfile.name}</s:param>
		</s:text>
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td><logic:empty name="watchedProfile" property="interests">
					<s:text name="Profile.noInterests" />.
</logic:empty>

				<div class="cloud">
					<c:forEach var="interest"
						items="${requestScope.interestPaginator.resultList}">

						<span class="tag"> <s:a action="/InterestInformations">
								<s:param name="infoInterestId" value="%{interest.id}" />
                ${interest.name}
            </s:a>
						</span>
					</c:forEach>
				</div>
				<div class="clear"></div> <c:set var="paginatorInstance"
					value="${requestScope.interestPaginator}" scope="request" /> <c:set
					var="paginatorAction" value="/DisplayProfile" scope="request" /> <c:set
					var="paginatorTile" value="profileInterests" scope="request" /> <c:import
					url="/content/pagination/Pagination.jsp" /></td>
		</tr>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="profile.showInteraction.title">
			<s:param>${watchedProfile.firstName} ${watchedProfile.name}</s:param>
		</s:text>
	</legend>
	<c:choose>
		<c:when test="${empty requestScope.interactions}">

			<table class="inLineTable tableStyle">
				<tr>
					<td><s:text name="Profile.noInteractions" />.</td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<div class="space"></div>
			<script type="text/javascript">
				$(document).ready(
						function pagination() {
							var nomTable = "tableinteractions";
							var idColonneATrier = 2;
							var sensDeTri = "desc";
							var aoColumns = [ {
								"bSortable" : false
							}, null, {
								"sType" : "date-euro"
							} ];
							miseEnPageTable(nomTable, idColonneATrier,
									sensDeTri, aoColumns, false, 5);
						});
			</script>

			<table id="tableinteractions" class="inLineTable tableStyle">
				<thead>
					<tr>
						<th></th>
						<th><s:text name="tableheader.name" /></th>
						<th><s:text name="interaction.lastModif" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="inter" items="${requestScope.interactions}">
						<tr>
							<td><c:import url="/FavoriteFragment.do">
									<c:param name="interactionId" value="${inter.id}" />
								</c:import></td>
							<td><c:choose>
									<c:when test="${inter.simpleClassName eq 'Announcement'}">
										<s:a action="/DisplayAnnounce">
											<s:param name="idAnnounce" value="%{inter.id}" />
                                ${inter.title}
                            </s:a>
									</c:when>
									<c:when test="${inter.simpleClassName eq 'Meeting'}">
										<s:a action="/DisplayEvent">
											<s:param name="eventId" value="%{inter.id}" />
                                ${inter.title}
                            </s:a>
									</c:when>
									<c:when test="${inter.simpleClassName eq 'Topic'}">
										<s:a action="/Topic">
											<s:param name="topicId" value="%{inter.id}" />
                                ${inter.title}
                                 
                            </s:a>
                            (${fn:length(inter.messages)} messages)
                        </c:when>
									<c:when test="${inter.simpleClassName eq 'Hub'}">
										<s:a action="/DisplayHub">
											<s:param name="hubId" value="%{inter.id}" />
                                ${inter.title}
                            </s:a>
                            (${fn:length(inter.topics)} topics)
                        </c:when>
									<c:when test="${inter.simpleClassName eq 'Community'}">
										<s:a action="/DisplayCommunity">
											<s:param name="communityId" value="%{inter.id}" />
                                ${inter.title}
                            </s:a>
                            (${fn:length(inter.hubs)} hubs)
                        </c:when>
									<c:when test="${inter.simpleClassName eq 'Consultation'}">
										<s:a action="/DisplayAConsultation">
											<s:param name="id" value="%{inter.id}" />
                                ${inter.title}
                            </s:a>
									</c:when>
								</c:choose></td>
							<td><s:date name="lastModified" format="dd/MM/yyyy" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>


<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="showProfile.contacts.title" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td><logic:empty name="watchedProfile" property="contacts">
					<c:choose>
						<c:when test="${edit}">
							<s:text name="showProfile.IHaveNoContacts" />
						</c:when>
						<c:otherwise>
							<s:text name="showProfile.noContacts" />
						</c:otherwise>
					</c:choose>
				</logic:empty> <logic:iterate
					collection="${requestScope.contactsPaginator.resultList}" id="user">
					<ili:getMiniature socialEntity="${user}" />
				</logic:iterate>
				<div class="clear"></div> <c:set var="paginatorInstance"
					value="${requestScope.contactsPaginator}" scope="request" /> <c:set
					var="paginatorAction" value="/DisplayProfile" scope="request" /> <c:set
					var="paginatorTile" value="profileContacts" scope="request" /> <c:import
					url="/content/pagination/Pagination.jsp" /></td>
		</tr>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="showProfile.groups.tree" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td>
				<ul>
					<c:choose>
						<c:when test="${requestScope.treeGroupProfile ne ''}">
							<li><s:a action="/DisplayInformationGroup">
									<s:param name="idGroup" value="%{socialGroup.id}" />
								${requestScope.treeGroupProfile}
								</s:a></li>

						</c:when>
						<c:otherwise>
							<li><s:text name="avatar.member.no.group" /></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</td>
		</tr>
	</table>
</fieldset>


