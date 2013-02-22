<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="leftMenu.title.loggedUsers" />
</h2>

<div id="loggedUsers" class="littlePadding">
	<ili:loggedUsers var="user" userId="${sessionScope.currentUserId}"
		loggedUsers="${applicationScope.loggedUsers}">
		<div class="mini-avatar-container">
		<s:url var="display" action="DisplayProfile">
			<s:param name="id" value="%{user.id}" />
		</s:url>
		<a href="<s:property value='#display'/>" class="miniature">
			<img src="avatar/${user.id}.png"
				title="${user.name} ${user.firstName}" alt="Avatar"></img>
		</a>
		<a class="chat-link"
				onclick="javascript:chatWith('${user.name}_${user.id}','habib2@master11.com')" alt="chat ${user.name} ${user.firstName}">chat</a>	
		</div>
	</ili:loggedUsers>
</div>