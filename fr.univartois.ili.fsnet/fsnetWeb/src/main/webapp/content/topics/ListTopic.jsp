<%-- 
    Document   : Topic
    Created on : 19 janv. 2010 23:51
    Author     : Zhu rui <zrhurey@gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<h3>the topics</h3>
<table>
<c:forEach var="topic" items="${listTopics}">
    <tr><td>${topic.sujet}</td></tr>
</c:forEach>
</table>

<html:form action="/CreateTopic">
	<h3>Create a topic :</h3>
	<table>
		<tr>
		<td><label>Sujet of the topic : </label></td>
		<td><html:text property="topicSujet" styleId="topicSujet" /></td>
		<html:hidden property="hubId" value="${hubId}"/>
		<td><html:submit value="Add" styleClass="button"/></td>
		</tr>
    </table>
</html:form>

