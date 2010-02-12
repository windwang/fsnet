<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<c:forEach var="interest" items="${allInterests}">
    <html:multibox
        property="selectedInterests"
        value="${interest.id}"
        />${interest.name}
</c:forEach>
