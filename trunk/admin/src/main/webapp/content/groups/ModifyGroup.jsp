<%-- 
		 Author : SAID Mohamed
		
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3><bean:message key="groups.Modify" /></h3>



<html:form action="/ModifyGroup">
	<jsp:include page="/content/groups/SamePartForGroup.jsp"/>
</html:form>