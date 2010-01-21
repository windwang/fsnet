<%-- 
    Document   : Topic
    Created on : 21 janv. 2010 16:00
    Author     : Zhu rui <zrhurey@gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3>Delete</h3>
<table>
	<html:form action="/DeleteTopic">
		<c:if test="${not empty listTopics}">
			<html:select property="topicId">
				<c:forEach var="topic" items="${listTopics}">
					<html:option value="${topic.id}">${topic.sujet}</html:option>
				</c:forEach>	
			</html:select>
			<html:submit value="Del"/>
		</c:if>
	</html:form>
</table>

