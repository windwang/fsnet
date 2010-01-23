<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<h3>Créer un intérêt</h3>
<html:javascript formName="/CreateInterest"/>
<html:form action="/CreateInterest">
    <html:text property="createdInterestName"/>
    <html:errors property="createdInterestName"/>
    <html:submit/>
</html:form>