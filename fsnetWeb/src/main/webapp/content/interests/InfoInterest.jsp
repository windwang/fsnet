<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<c:choose>
	<c:when test="${not empty requestScope.interest}">
		<h2>
			${requestScope.interest.name}
			<c:choose>
				<c:when test="${requestScope.own}">
					<s:a action="/DeleteInterestFromInterestInformations">
						<s:param name="removedInterestId"
							value="%{requestScope.interest.id}" />
						<img src="images/mini-delete.png" alt="delete" />
					</s:a>
				</c:when>
				<c:otherwise>
					<s:a action="/AddInterestFromInterestInformations">
						<img src="images/add.png" alt="add" />
						<s:param name="addedInterestId"
							value="%{requestScope.interest.id}" />
					</s:a>
				</c:otherwise>
			</c:choose>
		</h2>

		<fieldset class="fieldsetCadre">
			<legend>
				<s:text name="interests.title.parent" />
			</legend>
			<table class="inLineTable tableStyle">
				<tr>
					<td><c:choose>
							<c:when test="${not empty requestScope.interest.parentInterest}">
								<div class="cloud">
									<div>
										<span class="tag"> <s:a
												action="/InterestInformations">
												<s:param name="infoInterestId"
													value="%{interest.parentInterest.id}" />
											${interest.parentInterest.name}
							</s:a>
										</span>
									</div>
								</div>
								<div class="clear"></div>
							</c:when>
							<c:otherwise>
								<s:text name="interests.none" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</fieldset>

		<fieldset class="fieldsetCadre">
			<legend >
				<s:text name="interests.title.son" />
			</legend>
			<table class="inLineTable tableStyle">
				<tr>
					<td><c:choose>
							<c:when
								test="${not empty requestScope.interest.childrenInterests}">
								<div class="cloud">
									<c:forEach var="interestChild"
										items="${requestScope.interest.childrenInterests}">
										<div>
											<span class="tag"><s:a
													action="/InterestInformations">
													<s:param name="infoInterestId"
														value="%{interestChild.id}" />
								${interestChild.name}								
							</s:a></span>
										</div>
									</c:forEach>
								</div>
								<div class="clear"></div>
							</c:when>
							<c:otherwise>
								<s:text name="interests.none" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</fieldset>

		<fieldset class="fieldsetCadre">
			<legend >
				<s:text name="interests.title.associate" />
			</legend>
			<table class="inLineTable tableStyle">
				<tr>
					<td><c:choose>
							<c:when test="${not empty requestScope.interest.entities}">
								<div class="cloud">
									<c:forEach var="socialEntity"
										items="${requestScope.socialEntities}">
										<span class="tagSE"> <ili:getMiniature
												socialEntity="${socialEntity}" /> <ili:getSocialEntityInfos
												socialEntity="${socialEntity}" />
										</span>
									</c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<s:text name="interests.none" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</fieldset>

		<div class="clear"></div>
		<fieldset class="fieldsetCadre">
			<legend >
				<s:text name="interests.title.communities" />
			</legend>
			<table class="inLineTable tableStyle">
				<tr>
					<td><c:choose>
							<c:when test="${not empty requestScope.Community}">
								<div class="cloud">
									<c:forEach var="community" items="${requestScope.Community}">
										<div>
											<span class="tagInteraction"> <s:a
													action="/DisplayCommunity">
													<s:param name="communityId" value="%{community.id}" />
											${community.title}								
							</s:a>
											</span>
										</div>
									</c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<s:text name="interests.none" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</fieldset>
		<div class="clear"></div>

		<fieldset class="fieldsetCadre">
			<legend >
				<s:text name="interests.title.hubs" />
			</legend>
			<table class="inLineTable tableStyle">
				<tr>
					<td><c:choose>
							<c:when test="${not empty requestScope.Hub}">
								<div class="cloud">
									<c:forEach var="hub" items="${requestScope.Hub}">
										<div>
											<span class="tagInteraction"> <s:a
													action="/DisplayHub">
													<s:param name="hubId" value="%{hub.id}" />
											${hub.title}								
							</s:a>
											</span>
										</div>
									</c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<s:text name="interests.none" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</fieldset>
		<div class="clear"></div>

		<fieldset class="fieldsetCadre">
			<legend >
				<s:text name="interests.title.topics" />
			</legend>
			<table class="inLineTable tableStyle">
				<tr>
					<td><c:choose>
							<c:when test="${not empty requestScope.Topic}">
								<div class="cloud">
									<c:forEach var="topic" items="${requestScope.Topic}">
										<div>
											<span class="tagInteraction"> <s:a
													action="/Topic">
													<s:param name="topicId" value="%{topic.id}" />
											${topic.title}								
							</s:a>
											</span>
										</div>
									</c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<s:text name="interests.none" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</fieldset>
		<div class="clear"></div>

		<fieldset class="fieldsetCadre">
			<legend >
				<s:text name="interests.title.events" />
			</legend>
			<table class="inLineTable tableStyle">
				<tr>
					<td><c:choose>
							<c:when test="${not empty requestScope.Meeting}">
								<div class="cloud">
									<c:forEach var="meeting" items="${requestScope.Meeting}">
										<div>
											<span class="tagInteraction"> <s:a
													action="/DisplayEvent">
													<s:param name="eventId" value="%{meeting.id}" />
											${meeting.title}								
							</s:a>
											</span>
										</div>
									</c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<s:text name="interests.none" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</fieldset>

		<div class="clear"></div>
		<fieldset class="fieldsetCadre">
			<legend >
				<s:text name="interests.title.announces" />
			</legend>
			<table class="inLineTable tableStyle">
				<tr>
					<td><c:choose>
							<c:when test="${not empty requestScope.Announcement}">
								<div class="cloud">
									<c:forEach var="announce" items="${requestScope.Announcement}">
										<div>
											<span class="tagInteraction"> <s:a
													action="/DisplayAnnounce">
													<s:param name="idAnnounce" value="%{announce.id}" />
											${announce.title}								
							</s:a>
											</span>
										</div>
									</c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<s:text name="interests.none" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</fieldset>
	</c:when>
	<c:otherwise>
		<s:text name="interests.list.no" />
	</c:otherwise>
</c:choose>
