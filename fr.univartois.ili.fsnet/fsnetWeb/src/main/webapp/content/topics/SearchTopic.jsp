<%-- 
    Document   : Topic
    Created on : 21 janv. 2010 14:00
    Author     : Zhu rui <zrhurey@gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3>Search</h3>
<table>
	<html:form action="/SearchTopic">
		<tr>
		<td><label>Key of search :</label></td>
		<td><html:text property="topicSujetSearch" styleId="topicSujet" /></td>
		<td><html:submit value="Ok" styleClass="button"/></td>
		</tr>
	</html:form>
	<c:if test="${not empty resRearchTopics}">
		<tr>
		<th>Result of rearch</th>
		</tr>
		<tr>
		<c:forEach var="topic" items="${resRearchTopics}">
			<td>${topic.sujet}</td>
		</c:forEach>
		</tr>		
	</c:if>
</table>
