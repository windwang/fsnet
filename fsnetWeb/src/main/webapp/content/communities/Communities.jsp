<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<c:if test="${param.searchText == null}">
	<%@ include file="YourCommunities.jsp" %>
</c:if>

<c:if test="${param.searchCommunityText == null}">
	<%@ include file="SearchCommunity.jsp" %>
</c:if>
