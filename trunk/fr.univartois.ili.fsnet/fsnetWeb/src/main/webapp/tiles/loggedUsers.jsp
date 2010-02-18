<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>
	<bean:message key="loggedUsers.title"/>
</h2>

<div id="loggedUsers">
	<logic:iterate collection="${applicationScope.loggedUsers.users}" id="user">		
		<html:link action="/DisplayProfile" styleClass="miniature">
		    <html:param name="id" value="${user.key}"/>
			<img src="GetMiniature.do?memberId=${user.key}" title="${user.value}"></img>
		</html:link>			
	</logic:iterate>
</div>