<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:forEach var="interest" items="${allInterests}">
	<span class="otag"> <s:checkbox property="selectedInterests"
			value="%{interest.id}" /> <s:a
			action="/InterestInformations">
			<s:param name="infoInterestId" value="%{interest.id}" />
            ${interest.name}
        </s:a>
	</span>
</c:forEach>
