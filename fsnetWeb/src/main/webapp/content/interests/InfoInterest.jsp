<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<c:choose>
	<c:when test="${not empty requestScope.interest}">
		<h2>
			${requestScope.interest.name}
			<c:choose>
				<c:when test="${requestScope.own}">
					<html:link action="/RemoveInterest">
	                    <html:param name="removedInterestId" value="${requestScope.interest.id}"/>
	                    <img src="images/mini-delete.png" />
	                </html:link>
				</c:when>
				<c:otherwise>
					<html:link action="/AddInterest">
						<img src="images/add.png"/>
						<html:param name="addedInterestId" value="${requestScope.interest.id}"/>
					</html:link>
				</c:otherwise>
			</c:choose>
		</h2>
		
		<h3><bean:message key="interests.15"/></h3>
		<c:choose>
			<c:when test="${not empty requestScope.interest.parentInterest}">
				<div class="cloud">
					<div>
						<html:link action="/InterestInformations">
							<html:param name="infoInterestId" value="${interest.parentInterest.id}"/>
							${interest.parentInterest.name}
						</html:link>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<bean:message key="interests.17"/>
			</c:otherwise>
		</c:choose>
		
		<h3><bean:message key="interests.16"/></h3>
		<c:choose>
			<c:when test="${not empty requestScope.interest.childrenInterests}">
				<div class="cloud">
					<c:forEach var="interestChild" items="${requestScope.interest.childrenInterests}">
						<div>
							<html:link action="/InterestInformations">
								<html:param name="infoInterestId" value="${interestChild.id}"/>
								${interestChild.name}								
							</html:link>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<bean:message key="interests.17"/>
			</c:otherwise>
		</c:choose>
		
		<h3><bean:message key="interests.14"/></h3>
		<c:choose>
			<c:when test="${not empty requestScope.interest.entities}">
				<div class="cloud">
					<c:forEach var="socialEntities" items="${requestScope.interest.entities}">
						<div>
							<html:link action="/DisplayProfile">
								<html:param name="id" value="${socialEntities.id}"/>
								${socialEntities.firstName} ${socialEntities.name}								
							</html:link>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<bean:message key="interests.17"/>
			</c:otherwise>
		</c:choose>
		
		<h3><bean:message key="pageTitle.15"/></h3>
		<c:choose>
			<c:when test="${not empty requestScope.Community}">
				<div class="cloud">
					<c:forEach var="community" items="${requestScope.Community}">
						<div>
							<html:link action="/DisplayCommunity">
								<html:param name="communityId" value="${community.id}"/>
								${community.title}								
							</html:link>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<bean:message key="interests.17"/>
			</c:otherwise>
		</c:choose>
		
		<h3><bean:message key="pageTitle.4"/></h3>
		<c:choose>
			<c:when test="${not empty requestScope.Hub}">
				<div class="cloud">
					<c:forEach var="hub" items="${requestScope.Hub}">
						<div>
							<html:link action="/DisplayHub">
								<html:param name="hubId" value="${hub.id}"/>
								${hub.title}								
							</html:link>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<bean:message key="interests.17"/>
			</c:otherwise>
		</c:choose>
		
		<h3><bean:message key="pageTitle.6"/></h3>
		<c:choose>
			<c:when test="${not empty requestScope.Topic}">
				<div class="cloud">
					<c:forEach var="topic" items="${requestScope.Topic}">
						<div>
							<html:link action="/Topic">
								<html:param name="topicId" value="${topic.id}"/>
								${topic.title}								
							</html:link>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<bean:message key="interests.17"/>
			</c:otherwise>
		</c:choose>		
		
		<h3><bean:message key="pageTitle.1"/></h3>
		<c:choose>
			<c:when test="${not empty requestScope.Meeting}">
				<div class="cloud">
					<c:forEach var="meeting" items="${requestScope.Meeting}">
						<div>
							<html:link action="/DisplayEvent">
								<html:param name="eventId" value="${meeting.id}"/>
								${meeting.title}								
							</html:link>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<bean:message key="interests.17"/>
			</c:otherwise>
		</c:choose>
		
		<h3><bean:message key="pageTitle.8"/></h3>
		<c:choose>
			<c:when test="${not empty requestScope.Announcement}">
				<div class="cloud">
					<c:forEach var="announce" items="${requestScope.Announcement}">
						<div>
							<html:link action="/DisplayAnnounce">
								<html:param name="idAnnounce" value="${announce.id}"/>
								${announce.title}								
							</html:link>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<bean:message key="interests.17"/>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<bean:message key="interests.8"/>
	</c:otherwise>
</c:choose>
