<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if test="${not empty requestScope.allInterests}">
	<fieldset class="fieldsetAdmin">
      <legend class="legendAdmin"><bean:message key="interests.5" /></legend>
	
	  <html:javascript formName="/ModifyInterest"/>
	  <div id="modify">
	  <html:form action="/ModifyInterest">
		<table class="fieldsetTableAdmin"><tr><td>
		<div class="errorMessage"><html:errors property="modifiedInterestId" /></div>
		<div>
		<bean:message key="error.interest.create"/>
		<html:select property="modifiedInterestId" styleClass="select" onchange="updateParentInterest()">
			<html:option value="">
				<bean:message key="interests.1" />
			</html:option>
			<c:forEach var="interest" items="${requestScope.allInterests}">
				<html:option value="${interest.id}">${interest.name}</html:option>
			</c:forEach>
		</html:select>
		<bean:message key="interests.15"/>
		
		<html:select property="parentInterestId" styleClass="select">
			<html:option value="">
				<bean:message key="interests.8"/>
			</html:option>
			<c:forEach var="interest" items="${requestScope.allInterests}">
				<html:option value="${interest.id}">${interest.name}</html:option>
			</c:forEach>
		</html:select><br/>
		
		<div class="errorMessage"><html:errors property="modifiedInterestName" /></div>
		<bean:message key="error.interest.name.modified"/>
		<html:text property="modifiedInterestName" />
		<html:hidden property="allInterestsId" value="${ allInterestsId }"/>
		<html:submit styleClass="button" >
      		<bean:message key="interest.validate"/>
      	</html:submit>
      	</div>
      	</td></tr></table>
	</html:form>
	</div>
	</fieldset>
</c:if>