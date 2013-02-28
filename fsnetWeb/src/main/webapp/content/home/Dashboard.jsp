<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
		<s:a action="/Inbox">
			<s:text name="dashBoard.messages.last" />
		</s:a>
	</legend>
	<table id="dashboardMessages" class="inLineTable tableStyle">
		<s:if test="%{messages==null}">
			<tr>
				<td><s:text name="dashBoard.messages.empty" />.</td>
			</tr>
		</s:if>
		<s:else>
			<c:forEach items="${requestScope.messages}" var="message" begin="0"
				end="2">
				<c:if test="${not message.reed}">
					<tr class="notReed">
						<td class="messagePhoto"><ili:getMiniature
								socialEntity="${message.from}" /></td>
						<td style="width: 0%"><s:a action="/DisplayMessage">
								<s:param name="messageId" value="%{message.id}" />
								<span> <ili:substring beginIndex="0" endIndex="20">
										<ili:noxml>${message.subject}</ili:noxml>
									</ili:substring> :
								</span>
								<span style="color: gray"> <ili:substring beginIndex="0"
										endIndex="20">
										<ili:noxml>${message.body}</ili:noxml>
									</ili:substring>
								</span>
							</s:a></td>
					</tr>
				</c:if>
				<c:if test="${message.reed}">
					<tr>
						<td class="messagePhoto"><ili:getMiniature
								socialEntity="${message.from}" /></td>
						<td><s:a action="/DisplayMessage">
								<s:param name="messageId" value="%{message.id}" />
								<span> <ili:substring beginIndex="0" endIndex="20">
										<ili:noxml>${message.subject}</ili:noxml>
									</ili:substring> :
								</span>
								<span style="color: gray"> <ili:substring beginIndex="0"
										endIndex="20">
										<ili:noxml>${message.body}</ili:noxml>
									</ili:substring>
								</span>
							</s:a></td>
					</tr>
				</c:if>
			</c:forEach>
		</s:else>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<s:a action="/Visits">
			<s:text name="visite.last.title" />
		</s:a>
	</legend>
	<table id="lastVisits" class="inLineTable tableStyle">
		<s:if test="%{visitors==null}">
			<tr>
				<td><s:text name="dashBoard.visites.empty" />.</td>
			</tr>
		</s:if>
		<s:else>
			<c:forEach var="pv" items="${visitors}">
				<tr>
					<td class="messagePhoto"><ili:getMiniature
							socialEntity="${pv.visitor}" /></td>
					<td><ili:getSocialEntityInfos socialEntity="${pv.visitor}" />
					</td>
					<td><s:property value="pv" default="lastVisite"/></td>
				</tr>
			</c:forEach>

		</s:else>
	</table>
</fieldset>

<div class="clear homeGap"></div>
<c:choose>
	<c:when test="${sessionScope.numNewContactsRequests gt 0}">
		<fieldset class="fieldsetCadre">
			<legend>
				<s:text name="dashBoard.contacts.ask" />
			</legend>
			<table id="contactsAsked" class="inLineTable homeFrame tableStyle">
				<c:forEach var="contact" items="${contactsAsked}">
					<tr class="notReed">
						<td class="miniatureContainer"><ili:getMiniature
								socialEntity="${contact}" /></td>
						<td><ili:getSocialEntityInfos socialEntity="${contact}" /></td>
						<td class="tableButton"><s:a action="/AcceptContactHome"
								styleClass="btn btn-inverse">
								<s:param name="entityAccepted" value="%{contact.id}" />
								<s:text name="contact.button.accept" />
							</s:a> <s:a action="/RefuseContactHome" styleClass="btn btn-inverse">
								<s:param name="entityRefused" value="%{contact.id}" />
								<s:text name="contact.button.refuse" />
							</s:a></td>
					</tr>
				</c:forEach>
			</table>
		</fieldset>
	</c:when>
	<c:otherwise>
		<fieldset class="fieldsetCadre">
			<legend>
				<s:text name="dashBoard.contacts.proposals" />
			</legend>
			<table id="contactProposals" class="inLineTable homeFrame tableStyle">
				<s:if test="%{contacts==null}">
					<tr>
						<td><s:text name="dashBoard.contacts.empty" />.</td>
					</tr>
				</s:if>
				<s:else>
					<c:forEach var="contact" items="${contacts}">
						<tr>
							<td class="messagePhoto"><ili:getMiniature
									socialEntity="${contact}" /></td>
							<td><ili:getSocialEntityInfos socialEntity="${contact}" /></td>
							<td class="tableButton"><s:a action="/ContactDemandHome">
									<img src="images/add.png"
										alt="<bean:message key='dashBoard.contact.button.add.alt'/>"
										title="<bean:message key='dashBoard.contact.button.add'/> ${contact.firstName} ${contact.name}" />
									<s:param name="entitySelected" value="%{contact.id}" />
								</s:a></td>
						</tr>
					</c:forEach>
				</s:else>
			</table>
		</fieldset>
	</c:otherwise>
</c:choose>

<fieldset class="fieldsetCadre">
	<legend>
		<s:a action="/InterestInformations">
			<s:text name="dashBoard.interests.proposals" />
		</s:a>
	</legend>
	<table id="interestProposals" class="inLineTable homeFrame tableStyle">
		<s:if test="%{interests==null}">

			<tr>
				<td><s:text name="dashBoard.interests.empty" />.</td>
			</tr>
		</s:if>
		<s:else>
			<c:forEach var="interest" items="${interests}">
				<tr class="interestDashboardContainer">
					<td><s:a action="/InterestInformations">
							<s:param name="infoInterestId" value="%{interest.id}" />
                        ${interest.name}
                    </s:a></td>
					<td class="tableButton"><s:a action="/AddInterest">
							<img src="images/add.png"
								alt="<s:text name='dashBoard.interest.button.add.alt'/>"
								title="<s:text name='dashBoard.interest.button.add'/> ${interest.name}" />
							<s:param name="addedInterestId" value="%{interest.id}" />
						</s:a></td>
				</tr>
			</c:forEach>

		</s:else>
	</table>
</fieldset>

<div class="clear homeGap"></div>

<fieldset class="fieldsetCadre">
	<legend>
		<s:a action="/Consultations">
			<s:text name="lastInteractions.title" />
		</s:a>
	</legend>
	<table id="lastInteractions" class="inLineTable homeFrame tableStyle">
		<s:if test="%{lastInteractions==null}">
			<tr>
				<td><s:text name="dashBoard.interaction.empty" />.</td>
			</tr>
		</s:if>

		<s:else>
			<c:forEach var="triple" items="${requestScope.lastInteractions}">
				<tr>
					<td class="messagePhoto"><ili:getMiniature
							socialEntity="${triple.interaction.creator}" /></td>
					<td><s:text name="events.by" /> <ili:getSocialEntityInfos
							socialEntity="${triple.interaction.creator}" /></td>
					<td><s:a action="%{triple.path}">
							<s:param name="%{triple.id}" value="%{triple.interaction.id}" />
                        ${triple.interaction.title}
                    </s:a></td>
					<td class="tableButton"><s:set id="interkey" name="triple"
							var="interaction" /> <s:property value="interkey"
							default="lastModified" /></td>
				</tr>

			</c:forEach>

		</s:else>
	</table>
</fieldset>
