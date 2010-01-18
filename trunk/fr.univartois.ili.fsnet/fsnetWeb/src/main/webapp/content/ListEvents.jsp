<%-- 
    Document   : ListEvents
    Created on : 18 janv. 2010, 21:05:18
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:forEach var="event" items="${requestScope.listEvents}">
    Evenement : ${event}
</c:forEach>