<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<c:if test="${consultation eq null }"><p><bean:message key="consultation.unavailable" /></p></c:if>

<c:if test="${consultation ne null }">
<h3>${consultation.title }</h3>
<ul>
	<li><bean:message key="consultation.creator" /> : <ili:getSocialEntityInfos socialEntity="${consultation.creator }" /></li>
	<c:if test="${consultation.description ne '' }"><li><bean:message key="consultation.description" /> : ${consultation.description }</li></c:if>
	<li><bean:message key="consultation.createdAtDate" /> <bean:write name="consultation" property="creationDate" format="dd/MM/yyyy" />
		<bean:message key="consultation.createdAtHour" /> <bean:write name="consultation" property="creationDate" format="HH:mm" />
		</li>
</ul>
<br />
<table>
	<tr>
		<td></td>
		<td class="consultationPerticipant"><bean:message key="consultation.voter" /></td>
		<c:forEach var="choice" items="${consultation.choices }">
			<td class="consultationResultChoice">${choice.intituled }</td>
		</c:forEach>
		<c:if test="${consultation.type eq 'YES_NO_OTHER' }"><td class="consultationResultChoice"><bean:message key="consultation.other" /></td></c:if>
		<td class="consultationComment"><bean:message key="consultation.comment" /></td>
	</tr>
	<c:forEach var="vote" items="${consultation.consultationVotes }">
		<tr>
		<td><c:if test="${member.id eq vote.voter.id }">
			<html:link action="/DeleteVoteConsultation?consultation=${consultation.id}&amp;vote=${vote.id}">
				<html:img src="images/mini-delete.png" altKey="consultation.delete" />
			</html:link>
		</c:if></td>
		<td class="consultationPerticipant"><ili:getSocialEntityInfos socialEntity="${vote.voter }" /></td>
		<c:forEach var="choice" items="${consultation.choices }">
			<c:set var="isVoted" value="false"/>
			<c:set var="isIfNecessary" value="false"/>
			<c:forEach var="choiceVote" items="${vote.choices }">
				<c:if test="${choice.id eq choiceVote.choice.id }">
					<c:set var="isVoted" value="true"/>
					<c:if test="${choiceVote.ifNecessary }"><c:set var="isIfNecessary" value="true"/></c:if>
				</c:if>
			</c:forEach>
			<td <c:if test="${isVoted and not isIfNecessary}">class="consultationIsVoted"</c:if>
			<c:if test="${isIfNecessary}">class="consultationIsIfNecessary"</c:if>
			<c:if test="${not isVoted}">class="consultationIsNotVoted"</c:if> /></td>
		</c:forEach>
		<c:if test="${consultation.type eq 'YES_NO_OTHER' }"><td class="consultationOther">${vote.other}</td></c:if>
		<td class="consultationComment">${vote.comment}</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="2"></td>
		<ili:consultationResults consultation="${consultation}" number="number" percent="percent" maximum="max">
			<td class="${max?'consultationResultMax':'consultationResult' }">${number}<br />${percent }%</td>
		</ili:consultationResults>
	<tr>
	<html:form action="/VoteConsultation" method="post">
		<tr>
			<td colspan="2"></td>
			<c:forEach var="choice" items="${consultation.choices }">
				<td class="consultationFormChoices">
					<c:choose>
						<c:when test="${consultation.type eq 'YES_NO_IFNECESSARY' }">
							<html:select property="voteChoice">
								<html:option value="no${choice.id}"><bean:message key="consultation.choiceNo" /></html:option>
								<html:option value="yes${choice.id}"><bean:message key="consultation.choiceYes" /></html:option>
								<html:option value="ifNecessary${choice.id}"><bean:message key="consultation.choiceIfNecessary" /></html:option>
							</html:select>
						</c:when>
						<c:otherwise>
							<html:multibox property="voteChoice" value="${choice.id}" />
						</c:otherwise>
					</c:choose>
				</td>
			</c:forEach>
			<c:if test="${consultation.type eq 'YES_NO_OTHER' }"><td><html:text property="voteOther"/></td></c:if>
			<td><html:text property="voteComment"/></td>
			<html:hidden property="id" value="${consultation.id }"/>
			<td><html:submit styleClass="button"><bean:message key="consultation.vote" /></html:submit></td>
		</tr>
	</html:form>
</table>

<h3><bean:message key="consultation.histogramme" /></h3>

<div style="float:right; text-align:right;">
	<c:forEach var="choice" items="${consultation.choices }">
		<div class="consultationHistoIndication">${choice.intituled}</div>
	</c:forEach>
</div>

<div>
	<ili:consultationResults consultation="${consultation}" percent="percent">
		<div class="consultationHistoStick" style="width:${percent}%;">
			<div class="consultationHistoPercent">${percent}%</div>
		</div>
	</ili:consultationResults>
</div>


</c:if>