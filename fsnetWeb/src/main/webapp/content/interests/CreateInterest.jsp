<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3><bean:message key="interests.3"/></h3>
<html:javascript formName="/CreateInterest"/>
<html:form action="/CreateInterest">
	<bean:message key="interests.15"/>
	<html:select property="parentInterestId" styleClass="select">
		<html:option value=""><bean:message key="interests.8"/></html:option>
		<c:forEach var="interest" items="${requestScope.allInterests}">
			<html:option value="${interest.id}">${interest.name}</html:option>
		</c:forEach>
	</html:select>
    <html:text property="createdInterestName"/>
    <html:errors property="createdInterestName"/>
    <html:submit styleClass="button">
    	<bean:message key="interests.create"/>
    </html:submit>
</html:form>