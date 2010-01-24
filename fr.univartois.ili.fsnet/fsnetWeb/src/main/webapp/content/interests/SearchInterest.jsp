<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<h3><bean:message key="interests.11"/></h3>
<html:javascript formName="/SearchInterest"/>
<html:form action="/SearchInterest">
    <html:text property="searchInterestName"/>
    <html:errors property="searchInterestName"/>
    <html:submit styleClass="button"/>
</html:form>

<jsp:include page="/content/interests/ResultInterest.jsp"/>