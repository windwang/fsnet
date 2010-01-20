<%-- 
    Document   : Topic
    Created on : 20 janv. 2010 20:59
    Author     : Zhu rui <zrhurey@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/CreateTopic">
	<table>
		<tr>
		<td><label>Sujet :</label></td>
		<td><html:text property="topicSujet" styleId="topicSujet" /></td>
		</tr>
		<tr>
		<td><label>In which the topic appears</label></td>
		<td><html:select property="hub">
			<c:forEach var="hub" items="${listHubs}">
				<html:option value="${hub.id}">${hub.nomCommunaute}</html:option>	
			</c:forEach>
		</html:select>
		<html:submit/></td>
		</tr>
    </table>
</html:form>


