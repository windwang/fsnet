<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan and Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<fieldset class="fieldsetAppli">
  <legend class="legendHome">
    <c:import url="/FavoriteFragment.do">
        <c:param name="interactionId" value="${requestScope.Community.id}"/>
    </c:import>
    
    <bean:write name="Community" property="title" />
  </legend>

<c:set var="theInteraction" value="${Community}" scope="request"/>
<jsp:include page="/content/interactions/LargeInteractionInfo.jsp" />
</fieldset>
<div class="clear"></div>

<jsp:include page="/content/hubs/SearchHub.jsp"/>
<jsp:include page="/content/hubs/ListHubs.jsp"/>
