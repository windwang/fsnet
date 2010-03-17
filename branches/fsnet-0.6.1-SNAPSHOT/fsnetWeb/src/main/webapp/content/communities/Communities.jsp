<%-- 
    Document   : Communities
    Author     : Audrey Ruellan and Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${param.searchText == null}">
<jsp:include page="/content/communities/YourCommunities.jsp"/>
</c:if>
<c:if test="${param.searchCommunityText == null}">
<jsp:include page="/content/communities/SearchCommunity.jsp"/>
</c:if>
