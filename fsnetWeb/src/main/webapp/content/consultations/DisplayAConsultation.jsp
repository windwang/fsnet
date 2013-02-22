<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<c:if test="${consultation eq null }">
	<p>
		<s:text name="consultations.unavailable" />
	</p>
</c:if>

<c:if test="${consultation ne null }">
	<fieldset class="fieldsetCadre">
		<legend>${consultation.title }</legend>
		<table class="inLineTable tableStyle">
			<tr>
				<td>
					<ul>
						<li><s:text name="consultations.creator" /> : <ili:getSocialEntityInfos
								socialEntity="${consultation.creator }" /></li>
						<c:if test="${consultation.description ne '' }">
							<li><s:text name="consultations.form.description" /> :
								${consultation.description }</li>
						</c:if>
						<li><s:text name="consultations.createdAtDate" /> <s:property
								value="creationDate" /> <s:text
								name="consultations.createdAtHour" /> <s:property
								value="creationDate" />
						<li><s:text name="consultations.typeConsultation" /> : <c:choose>
								<c:when test="${consultation.type eq 'YES_NO'}">
									<s:text name="consultations.typeYesNo" />
								</c:when>
								<c:when test="${consultation.type eq 'YES_NO_OTHER'}">
									<s:text name="consultations.typeYesNoOther" />
								</c:when>
								<c:when test="${consultation.type eq 'YES_NO_IFNECESSARY'}">
									<s:text name="consultations.typeYesNoIfNecessary" />
								</c:when>
								<c:when test="${consultation.type eq 'PREFERENCE_ORDER'}">
									<s:text name="consultations.typePreferenceOrder" />
								</c:when>
							</c:choose></li>
						<li><s:text name="consultations.state" /> : <c:choose>
								<c:when test="${consultation.opened }">
									<s:text name="consultations.opened" />
									<c:if test="${member eq consultation.creator }">
					(<s:a href="/CloseConsultation?id=%{consultation.id}">
											<s:text name="consultations.button.close" />
										</s:a>)
				</c:if>
								</c:when>
								<c:when test="${not consultation.opened }">
									<s:text name="consultations.closed" />
									<c:if test="${member eq consultation.creator }">
					(<s:a href="/OpenConsultation?id=%{consultation.id}">
											<s:text name="consultations.button.open" />
										</s:a>)
				</c:if>
								</c:when>
							</c:choose></li>
						<c:if test="${consultation.limitChoicesPerParticipant}">
							<li><s:text name="consultations.limitChoicesPerVoter" /> -
								min : ${consultation.limitChoicesPerParticipantMin }, max :
								${consultation.limitChoicesPerParticipantMax}</li>
						</c:if>
						<c:if test="${consultation.closingAtMaxVoters}">
							<li><s:text name="consultations.closingAtMaxVoters" /> :
								${consultation.maxVoters}</li>
						</c:if>
						<c:if test="${consultation.closingAtDate}">
							<li><s:text name="consultations.deadline" /> : <s:property
									value="maxDate" /></li>
						</c:if>
					</ul> <br /> <c:if test="${errorChoicesPerParticipant}">
						<p>
							<s:text name="consultations.error.choicesPerParticipant" />
						</p>
					</c:if> <c:if test="${errorPreferenceOrderDistinct}">
						<p>
							<s:text name="consultations.error.preferenceOrderDistinct" />
						</p>
					</c:if>
					<table class="tableStyle">
						<tr>
							<td></td>
							<td class="consultationPerticipant"><s:text
									name="consultations.voter" /></td>
							<c:forEach var="choice" items="${consultation.choices }">
								<td class="consultationResultChoice">${choice.intituled }</td>
							</c:forEach>
							<c:if test="${consultation.type eq 'YES_NO_OTHER' }">
								<td class="consultationResultChoice"><s:text
										name="consultations.other" /></td>
							</c:if>
							<td class="consultationComment"><s:text
									name="consultations.comment" /></td>
						</tr>

						<c:forEach var="vote" items="${consultation.consultationVotes }">
							<c:if test="${allowedToShowResults or vote.voter eq member}">
								<tr>
									<td><c:if
											test="${consultation.opened and member.id eq vote.voter.id }">
											<s:a
												href="/DeleteVoteConsultation?consultation=%{consultation.id}&amp;vote=%{vote.id}">
												<img src="images/mini-delete.png"
													alt="consultations.button.delete" />
											</s:a>
										</c:if></td>
									<td class="consultationPerticipant"><ili:getSocialEntityInfos
											socialEntity="${vote.voter }" /></td>
									<c:forEach var="choice" items="${consultation.choices }">
										<c:choose>
											<c:when test="${consultation.type eq 'PREFERENCE_ORDER'}">
												<c:forEach var="choiceVote" items="${vote.choices }">
													<c:if test="${choice.id eq choiceVote.choice.id }">
														<td class="consultationPreferenceOrder">${choiceVote.preferenceOrder}</td>
													</c:if>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:set var="isVoted" value="false" />
												<c:set var="isIfNecessary" value="false" />
												<c:forEach var="choiceVote" items="${vote.choices }">
													<c:if test="${choice.id eq choiceVote.choice.id }">
														<c:set var="isVoted" value="true" />
														<c:if test="${choiceVote.ifNecessary }">
															<c:set var="isIfNecessary" value="true" />
														</c:if>
													</c:if>
												</c:forEach>
												<td
													<c:if test="${isVoted and not isIfNecessary}">class="consultationIsVoted"</c:if>
													<c:if test="${isIfNecessary}">class="consultationIsIfNecessary"</c:if>
													<c:if test="${not isVoted}">class="consultationIsNotVoted"</c:if> /></td>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<c:if test="${consultation.type eq 'YES_NO_OTHER' }">
										<td class="consultationOther">${vote.other}</td>
									</c:if>
									<td class="consultationComment">${vote.comment}</td>
								</tr>
							</c:if>
						</c:forEach>
						<c:if test="${allowedToShowResults}">
							<tr>
								<td colspan="2"></td>
								<c:choose>
									<c:when test="${consultation.type eq 'PREFERENCE_ORDER' }">
										<ili:consultationResults consultation="${consultation}"
											number="number">
											<td
												class="${number eq 1?'consultationResultMax':'consultationResult' }">${number}</td>
										</ili:consultationResults>
									</c:when>
									<c:otherwise>
										<ili:consultationResults consultation="${consultation}"
											number="number" percent="percent" maximum="max">
											<td
												class="${max?'consultationResultMax':'consultationResult' }">${number}<br />${percent
												}%
											</td>
										</ili:consultationResults>
									</c:otherwise>
								</c:choose>
							<tr>
						</c:if>
						<c:if test="${allowedToVote }">
							<s:form action="/VoteConsultation" method="post"
								styleId="voteForm">
								<tr class="consultationVote">
									<td colspan="2"></td>
									<c:set var="i" value="0" />
									<c:forEach var="choice" items="${consultation.choices }">
										<td class="consultationFormChoices"><c:choose>
												<c:when test="${consultation.type eq 'YES_NO_IFNECESSARY' }">
													<select name="voteChoice" disabled="${disabledList[i]}">
														<option value="no${choice.id}">
															<s:text name="consultations.choiceNo" />
														</option>
														<option value="yes${choice.id}">
															<s:text name="consultations.choiceYes" />
														</option>
														<option value="ifNecessary${choice.id}">
															<s:text name="consultations.choiceIfNecessary" />
														</option>
													</select>
												</c:when>
												<c:when test="${consultation.type eq 'PREFERENCE_ORDER'}">


													<select name="voteChoice">
														<c:forEach var="index" begin="1"
															end="${fn:length(consultation.choices) }">
															<option value="${choice.id}_${index}">${index }</option>
														</c:forEach>
													</select>
												</c:when>
												<c:otherwise>

													<s:checkbox property="voteChoice" value="%{choice.id}"
														disabled="%{disabledList[i]}" />
															

												</c:otherwise>

											</c:choose></td>
										<c:set var="i" value="${i + 1}" />
									</c:forEach>
									<c:if test="${consultation.type eq 'YES_NO_OTHER' }">
										<td><s:textfield property="voteOther" styleId="voteOther" />
											<div class="consultationAutoCompleteList"
												id="autoCompleteList"><jsp:include
													page="autocompleteOtherChoice.jsp" /></div></td>
									</c:if>
									<td><s:textfield property="voteComment" /></td>

									<s:hidden name="id" value="%{consultation.id }" />
									<td><s:submit styleClass="btn btn-inverse">
											<s:text name="consultations.button.vote" />
										</s:submit></td>
								</tr>
							</s:form>
						</c:if>
					</table>
				</td>
			</tr>
		</table>
	</fieldset>

	<c:if
		test="${ allowedToShowResults and consultation.type ne 'PREFERENCE_ORDER' }">
		<fieldset class="fieldsetCadre">
			<legend>
				<s:text name="consultations.histogramme" />
			</legend>
			<table class="inLineTable tableStyle">
				<tr>
					<td>

						<div>
							<ili:consultationResults consultation="${consultation}"
								percent="percent" number="number" choice="choice"
								histogram="yes">
								<span class="consultationHistoPercent">${choice}
									${percent}% (${number})</span>
								<div class="consultationHistoStick" style="width:${percent}%;">

								</div>
							</ili:consultationResults>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</c:if>

</c:if>

<script type="text/javascript">
	$(function() {
		$("#voteOther").attr("autocomplete", "off");
		$("#voteOther").keyup(function(e) {
			var datas = $("#voteForm").serialize();
			$.ajax({
				type : 'POST', // envoi des données en POST
				url : 'AutocompleteOther.do',
				data : datas, // sélection des champs à envoyer
				success : function(data) {
					$("#autoCompleteList").html(data);
					$(".autoCompleteListMember").click(function(e) {
						$("#voteOther").val($(this).text());
						$("#autoCompleteList").html("");
					});
					$("body").click(function() {
						$("#autoCompleteList").html("");
					});
				}
			});
		});
	});

	
</script>