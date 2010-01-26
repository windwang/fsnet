<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<h3><bean:message key="interests.0"/></h3>
<c:choose>
    <c:when test="${not empty requestScope.listInterests}">
        <html:javascript formName="/AddInterest"/>
        <html:form action="/AddInterest">
            <html:select property="addedInterestId" styleClass="select">
                <html:option value="0"><bean:message key="interests.1"/></html:option>
                <c:forEach var="interest" items="${requestScope.listInterests}">
                    <html:option value="${interest.id}">${interest.nomInteret}</html:option>
                </c:forEach>
            </html:select>
            <html:submit styleClass="button">
            	<bean:message key="interests.add"/>
            </html:submit>
        </html:form>
        <html:errors property="addedInterestId"/>
    </c:when>
    <c:otherwise>
        <bean:message key="interests.2"/>
    </c:otherwise>
</c:choose>
