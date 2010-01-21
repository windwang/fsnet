<%-- 
    Document   : Topic
    Created on : 21 janv. 2010 16:00
    Author     : Zhu rui <zrhurey@gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3>Update</h3>
<table>
	<html:form action="/ChangeTopic">
		<c:if test="${not empty listTopics}">
			<html:select property="topicId">
				<c:forEach var="topic" items="${listTopics}">
					<html:option value="${topic.id}">${topic.sujet}</html:option>
				</c:forEach>	
			</html:select>
			<html:submit value="Ok" property="find"/>
		</c:if>
		<c:if test="${not empty topicFind}">
			<table>
			<tr><th>Sujet</th><td>${topicFind.sujet}</td></tr>
			<tr><th>Date create</th><td>${topicFind.dateSujet}</td></tr>
			<tr><th>Creator</th><td>${creatorTopic.nom}</td></tr>
			<tr>
				<th>Appear</th><td>
				<c:if test="${not empty listHubs}">
				<html:select property="hubNom" value="${hubTopic.nomCommunaute}">
				<c:forEach var="hub" items="${listHubs}">
					<html:option value="${hub.nomCommunaute}">${hub.nomCommunaute}</html:option>
				</c:forEach>	
				</html:select>
				</c:if>
				</td>
			</tr>
			<tr><th>Messages</th><td></td></tr>
			</table>
			<html:hidden property="topicId" value="${topicFind.id}"/>
			<html:submit value="Update" property="update"/>
		</c:if>
	</html:form>
</table>