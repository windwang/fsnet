<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table class="inLineTable tableStyle">
	<tr>
		<td><s:text name="interactions.owner" /> : <ili:getSocialEntityInfos
				socialEntity="%{theInteraction.creator}" /> <br />
			<div style="color: #808080">
				<c:if test="${not empty subscribers}">		
        	${fn:length(subscribers)} 
        	<s:text name="interactions.subscribers" />
					<br />
				</c:if>
				<s:text name="interactions.created" />
				<s:property value="theInteraction.creationDate" />
				<br /> ${fn:length(theInteraction.followingEntitys)}
				<s:text name="interactions.followers" />
				<br />
				<s:text name="interactions.visibility" />
				<br />

<!-- 				<logic:notEmpty name="theInteraction" property="interests"> </logic:notEmpty> -->
				<s:if test="(theInteraction.interests != null || !theInteraction.interests.equals(''))">
					<s:text name="interactions.interest" /> :
           		 	<div class="cloud">
						<c:forEach var="interest" items="${theInteraction.interests}">
							<span class="otag"> 
								<s:a href="/InterestInformations">
									<s:param name="infoInterestId" value="%{interest.id}" />
	                           		${interest.name}
	                        	</s:a> 
	                        	<c:if test="${sessionScope.userId eq theInteraction.creator.id}">
									<s:a href="/RemoveInterestOfInteraction">
										<s:param name="interactionId" value="%{theInteraction.id}" />
										<s:param name="interestId" value="%{interest.id}" />

										<img src="images/mini-delete.png" alt="delete" />
									</s:a>
								</c:if>
							</span>
						</c:forEach>
					</div>
				</s:if>
			</div>
		</td>
	</tr>
</table>
