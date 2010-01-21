<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>Rechercher un intérêt</h2>
<html:javascript formName="/SearchInterest"/>
<html:form action="/SearchInterest">
	<html:text property="interestName"/>
	<html:errors property="error.interest.remove"/>
	<html:submit/>
</html:form>

<jsp:include page="/content/interests/ResultInterest.jsp"/>