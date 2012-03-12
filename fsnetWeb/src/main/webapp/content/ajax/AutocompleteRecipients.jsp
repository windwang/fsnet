<%@ page language="java" contentType="text/xml; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<root>
	 <c:forEach var="socialEntity" items="${matchesSocialEntity}"> 
		<item id="${socialEntity.id}" label="${socialEntity.email} (${socialEntity.name} ${socialEntity.firstName}), "/>
	 </c:forEach> 
</root>
