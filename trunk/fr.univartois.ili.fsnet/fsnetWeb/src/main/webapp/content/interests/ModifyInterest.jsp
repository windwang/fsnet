<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<c:if test="${not empty requestScope.allInterests}">
    <h3>Modifier un intérêt</h3>
	<html:javascript formName="/ModifyInterest"/>
	<html:form action="/ModifyInterest">
		<html:select property="modifiedInterestId">
        	<html:option value="0">Intérêt</html:option>
        	<c:forEach var="interest" items="${requestScope.allInterests}">
            	<html:option value="${interest.id}">${interest.nomInteret}</html:option>
        	</c:forEach>
        </html:select>
        <html:errors property="modifiedInterestId"/>
	    <html:text property="modifiedInterestName"/>
	    <html:errors property="modifiedInterestName"/>
	    <html:submit/>
	</html:form>
</c:if>