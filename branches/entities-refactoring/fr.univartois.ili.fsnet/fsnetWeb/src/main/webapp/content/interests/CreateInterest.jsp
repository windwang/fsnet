<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<h3><bean:message key="interests.3"/></h3>
<html:javascript formName="/CreateInterest"/>
<html:form action="/CreateInterest">
    <html:text property="createdInterestName"/>
    <html:errors property="createdInterestName"/>
    <html:submit styleClass="button"/>
</html:form>