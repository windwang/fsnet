<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>  
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>
    <bean:message key="showProfile.title" arg0="${watchedProfile.firstName} ${watchedProfile.name}"/>
    <c:if test="${edit}">
    	<html:link href="Profile.do" styleClass="profileLink link">
        	<bean:message key="showProfile.edit"/>
        </html:link>
    </c:if>
    
</h3>

<img src="GetPhoto.do?memberId=${watchedProfile.id}" style="float: right;"/>

<table class="watchedProfile">
	<c:if test="${watchedProfile.email != null && not empty watchedProfile.email }">
	    <tr>
	        <th><bean:message key="updateProfile.email"/></th>
	        <td>
	            <html:link href="mailto:${watchedProfile.email}">
	                ${watchedProfile.email}
	            </html:link>
	        </td>
	    </tr>
    </c:if>
    <c:if test="${watchedProfile.address.address != null && not empty watchedProfile.address.address }">
	    <tr>
	        <th><bean:message key="updateProfile.adress"/></th>
	        <td>${watchedProfile.address.address}</td>
	    </tr>
    </c:if>
    <c:if test="${watchedProfile.address.city != null &&  not empty watchedProfile.address.city}">
	    <tr>
	        <th><bean:message key="updateProfile.city"/></th>
	        <td>${watchedProfile.address.city}</td>
	    </tr>
    </c:if>
    <c:if test="${birthDay != null && not empty birthDay }">
	    <tr>
	        <th><bean:message key="updateProfile.dateOfBirth"/></th>
	        <td>${birthDay}</td>
	    </tr>
    </c:if>
    <c:if test="${watchedProfile.profession != null && not empty  watchedProfile.profession}">
	    <tr>
	        <th><bean:message key="updateProfile.job"/></th>
	        <td>${watchedProfile.profession}</td>
	    </tr>
    </c:if>
    <c:if test="${watchedProfile.sex != null && not empty  watchedProfile.sex}">
	    <tr>
	        <th><bean:message key="updateProfile.sexe"/></th>
	        <td>
	            <bean:message key="updateProfile.sexe.${watchedProfile.sex}"/>
	        </td>
	    </tr>
    </c:if>
    <c:if test="${watchedProfile.phone != null && not empty watchedProfile.phone}">	    
	    <tr>
	        <th><bean:message key="updateProfile.phone"/></th>
	        <td>${watchedProfile.phone}</td>
	    </tr>    
    </c:if>
</table>

<c:if test="${watchedProfile.id != currentUser.id && !alreadyInContact}">	
    <html:link action="/ContactDemand" styleClass="button">
        <bean:message key="showProfile.ask"/>
        <html:param name="entitySelected" value="${watchedProfile.id}"/>
    </html:link>
</c:if>

<c:if test="${watchedProfile.id != currentUser.id}">
	<html:link action="/DisplayCreatePrivateMessage" styleClass="button">
    	<bean:message key="showProfile.send"/>
    	<html:param name="receiver" value="${watchedProfile.email}"/>
	</html:link>
</c:if>

<div class="clear"></div>

<h3>
    <bean:message key="showInterest.title" arg0="${watchedProfile.firstName} ${watchedProfile.name}"/>
    <c:if test="${edit}">
    	<html:link styleClass="profileLink link" action="/Interests">
        	<bean:message key="showProfile.edit.interests"/>
    	</html:link>
	</c:if>
</h3>

<div class="cloud" >
    <c:forEach var="interest" items="${watchedProfile.interests}">

        <span class="tag">
            <html:link action="/InterestInformations">
                <html:param name="infoInterestId" value="${interest.id}"/>
                ${interest.name}
            </html:link>
        </span>
    </c:forEach>
</div>
<div style="clear: both" ></div>