<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${param.searchText == null}">
	<%@ include file="YourCommunities.jsp" %>
</c:if>

<c:if test="${param.searchCommunityText == null}">
	<%@ include file="SearchCommunity.jsp" %>
</c:if>
