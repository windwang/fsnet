<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<h3><bean:message key="interests.0"/></h3>
<c:choose>
    <c:when test="${not empty requestScope.listInterests}">
        <html:javascript formName="/AddInterest"/>
        <div class="cloud">
            <c:forEach var="interest" items="${requestScope.listInterests}">

                <span class="otag">
                    <html:link action="/AddInterest">
                        <img src="images/add.png"/>
                        <html:param name="addedInterestId" value="${interest.id}"/>
                    </html:link>
                    <html:link action="/InterestInformations">
                        <html:param name="infoInterestId" value="${interest.id}"/>
                        ${interest.name}
                    </html:link>
                </span>

            </c:forEach>
        </div>
        <html:errors property="addedInterestId"/>
    </c:when>
    <c:otherwise>
        <bean:message key="interests.2"/>
    </c:otherwise>
</c:choose>
