<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<c:choose>
	<c:when test="${not empty requestScope.interest}">
		<h2>${requestScope.interest.name}</h2>
		<c:if test="${not empty requestScope.interest.parentInterest}">
			<h3><bean:message key="interests.15"/></h3>
			<div class="cloud">
				<div>
					<html:link action="/InterestInformations">
						<html:param name="infoInterestId" value="${interest.parentInterest.id}"/>
						${interest.parentInterest.name}
					</html:link>
				</div>
			</div>
		</c:if>
		<c:if test="${not empty requestScope.interest.childrenInterests}">
			<h3><bean:message key="interests.16"/></h3>
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
		</c:if>
		<c:if test="${not empty requestScope.interest.entities}">
			<h3><bean:message key="interests.14"/></h3>
			<div class="cloud">
				<c:forEach var="socialEntities" items="${requestScope.interest.entities}">
					<div>
						${socialEntities.firstName} ${socialEntities.name}								
					</div>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${not empty requestScope.Hub}">
			<h3><bean:message key="pageTitle.4"/></h3>
			<div class="cloud">
				<c:forEach var="hub" items="${requestScope.Hub}">
					<div>
						${hub.title}								
					</div>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${not empty requestScope.Topic}">
			<h3><bean:message key="pageTitle.6"/></h3>
			<div class="cloud">
				<c:forEach var="topic" items="${requestScope.Topic}">
					<div>
						${topic.title}								
					</div>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${not empty requestScope.Meeting}">
			<h3><bean:message key="pageTitle.1"/></h3>
			<div class="cloud">
				<c:forEach var="meeting" items="${requestScope.Meeting}">
					<div>
						${meeting.title}
					</div>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${not empty requestScope.Announcement}">
			<h3><bean:message key="pageTitle.8"/></h3>
			<div class="cloud">
				<c:forEach var="announce" items="${requestScope.Announcement}">
					<div>
						${announce.title}
					</div>
				</c:forEach>
			</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<bean:message key="interests.8"/>
	</c:otherwise>
</c:choose>
