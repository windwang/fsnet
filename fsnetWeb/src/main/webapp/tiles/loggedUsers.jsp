<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<bean:message key="loggedUsers.title"/>
</h2>

<div id="loggedUsers">
	<ili:loggedUsers var="user" userId="${sessionScope.currentUserId}" loggedUsers="${applicationScope.loggedUsers}">
		<html:link action="/DisplayProfile" styleClass="miniature">
		    <html:param name="id" value="${user.id}"/>
			<img src="avatar/${user.id}.png" title="${user.name} ${user.firstName}"></img>
		</html:link>
	</ili:loggedUsers>
</div>