<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<h2>Créer un intérêt</h2>
<html:javascript formName="/CreateInterest"/>
<html:form action="/CreateInterest">
	<html:text property="interestName"/>
	<html:errors property="error.interest.create"/>
	<html:submit/>
</html:form>