<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if test="${not empty requestScope.user.interests}">
    <h3><bean:message key="interests.9"/></h3>
    <html:javascript formName="/RemoveInterest"/>
    <html:form action="/RemoveInterest">
        <html:select property="removedInterestId" styleClass="select">
            <html:option value="0"><bean:message key="interests.1"/></html:option>
            <c:forEach var="interest" items="${requestScope.user.interests}">
                <html:option value="${interest.id}">${interest.name}</html:option>
            </c:forEach>
        </html:select>
        <html:submit styleClass="button"/>
    </html:form>
    <html:errors property="removedInterestId"/>
</c:if>
