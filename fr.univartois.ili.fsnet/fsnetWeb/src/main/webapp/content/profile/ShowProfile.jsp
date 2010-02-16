<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>  
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>
    <bean:message key="showProfile.title" arg0="${watchedProfile.firstName} ${watchedProfile.name}"/>
</h3>
	
<img class="memberPhoto" src="GetPhoto.do?memberId=${watchedProfile.id}" style="float: right;"/>
	
<table class="watchedProfile">
    <tr>
        <th><bean:message key="updateProfile.email"/></th>
        <td>
            <html:link href="mailto:${watchedProfile.email}">
                ${watchedProfile.email}
            </html:link>
        </td>
    </tr>
    <tr>
        <th><bean:message key="updateProfile.adress"/></th>
        <td>${watchedProfile.address.address}</td>
    </tr>
    <tr>
        <th><bean:message key="updateProfile.city"/></th>
        <td>${watchedProfile.address.city}</td>
    </tr>
    <tr>
        <th><bean:message key="updateProfile.dateOfBirth"/></th>
        <td>${birthDay}</td>
    </tr>
    <tr>
        <th><bean:message key="updateProfile.job"/></th>
        <td>${watchedProfile.profession}</td>
    </tr>
    <tr>
        <th><bean:message key="updateProfile.sexe"/></th>
        <td>
            <c:if test="${watchedProfile.sex != null }">
                <bean:message key="updateProfile.sexe.${watchedProfile.sex}"/>
            </c:if>
        </td>
    </tr>
    <tr>
        <th><bean:message key="updateProfile.phone"/></th>
        <td>${watchedProfile.phone}</td>
    </tr>
    <c:if test="${edit}">
        <tr>
            <td colspan="2" align="right">
                <html:link styleClass="button" href="Profile.do">
                    <bean:message key="showProfile.edit"/>
                </html:link>
            </td>
            
        </tr>
    </c:if>
</table>
<c:if test="${watchedProfile.id != currentUser.id && !alreadyInContact}">	
    	
                    <html:link action="/ContactDemand" styleClass="button">
                        <bean:message key="showProfile.ask"/>
                        <html:param name="entitySelected" value="${watchedProfile.id}"/>
                    </html:link>
</c:if>
                    <html:link action="/DisplayCreatePrivateMessage" styleClass="button">
                        <bean:message key="showProfile.send"/>
                        <html:param name="receiver" value="${watchedProfile.email}"/>
                    </html:link>

<h3>
    <bean:message key="showInterest.title" arg0="${watchedProfile.firstName} ${watchedProfile.name}"/>
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

<c:if test="${edit}">
    <br/>
    <html:link styleClass="button" action="/Interests">
        <bean:message key="showProfile.edit"/>
    </html:link>
</c:if>