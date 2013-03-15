<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="logic" uri="http://struts.apache.org/tags-logic"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<bean:message key="leftMenu.title.loggedUsers" />
</h2>

<div id="loggedUsers" class="littlePadding">
	<ili:loggedUsers var="user" userId="${sessionScope.currentUserId}"
		loggedUsers="${applicationScope.loggedUsers}">
		<div class="mini-avatar-container">
			<html:link action="/DisplayProfile" styleClass="miniature connected-user">
				<html:param name="id" value="${user.id}" />
				<img src="avatar/${user.id}.png"
					title="${user.name} ${user.firstName}"
					alt="Avatar ${user.name} ${user.firstName}"></img>
			</html:link>
			<a class="chat-link"
				onclick="javascript:chatWith('${user.name}_${user.id}','habib2@master11.com')" alt="chat ${user.name} ${user.firstName}">chat</a>
		</div>
	</ili:loggedUsers>
</div>