<%-- 
    Document   : Topic
    Created on : 20 janv. 2010 20:59
    Author     : Zhu rui <zrhurey@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/CreateTopic">
	<h3>Create a topic :</h3>
	<table>
		<tr>
		<td><label>Sujet of the topic :</label></td>
		<td><html:text property="topicSujet" styleId="topicSujet" /></td>
		<td><html:submit value="Add"/></td>
		</tr>
    </table>
</html:form>


