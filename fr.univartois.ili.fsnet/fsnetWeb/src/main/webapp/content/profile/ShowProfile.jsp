<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>  
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>
	<bean:message key="showProfile.title" arg0="${watchedProfile.firstName} ${watchedProfile.name}"/>
</h3>
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
			<bean:message key="updateProfile.sexe.${watchedProfile.sex}"/>
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

<h3>
	<bean:message key="showInterest.title" arg0="${watchedProfile.firstName} ${watchedProfile.name}"/>
</h3>


<table>
	<c:forEach var="interest" items="${watchedProfile.interests}">
		<tr>
			<td>
				<html:link action="/InterestInformations">
               		<html:param name="InterestId" value="${interest.id}"/>
               		${interest.name}
            	</html:link>
			</td>
		</tr>
	</c:forEach>
	    <c:if test="${edit}">
    	<tr>
    		<td colspan="1" align="right">
    			<html:link styleClass="button" action="/Interests">
					<bean:message key="showProfile.edit"/>
				</html:link>
			</td>
		</tr>
    </c:if>
</table>