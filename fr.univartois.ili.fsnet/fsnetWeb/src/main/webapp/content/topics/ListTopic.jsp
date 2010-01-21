<%-- 
    Document   : Topic
    Created on : 19 janv. 2010 23:51
    Author     : Zhu rui <zrhurey@gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h3>List of your topics</h3>
<table>
<c:forEach var="topic" items="${listTopics}">
    <tr><td>${topic.sujet}</td></tr>
</c:forEach>
</table>