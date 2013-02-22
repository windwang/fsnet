<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="ili" uri="../WEB-INF/ili.tld"%>

<bean:define id="searchMessage">
	<bean:message key="menu.placeholder" />
</bean:define>

<div id="search">
	<html:form action="/SearchMember">
		<fieldset>
			<html:text styleClass="field" property="searchText"
				styleId="searchText" />
			<ili:placeHolder id="searchText" value="${searchMessage}" />
			<html:submit styleClass="searchButton" value=" " />
		</fieldset>
	</html:form>
</div>

<ul id="menu">
	<li><s:a action="/Home"
			styleClass="${currentMenu == 'Home' ? 'current' : ''}">
			<bean:message key="menu.welcome" />
		</s:a></li>
	<li><s:a action="/Members"
			styleClass="${currentMenu == 'Members' ? 'current' : ''}">
			<bean:message key="menu.creation" />
		</s:a></li>
	<li><s:a action="/MemberList"
			styleClass="${currentMenu == 'MemberList' ? 'current' : ''}">
			<bean:message key="menu.members" />
		</s:a></li>
	<li><s:a action="/GroupList"
			styleClass="${currentMenu == 'Groups' ? 'current' : ''}">
			<bean:message key="menu.groups" />
		</s:a></li>
	<li><s:a action="/Announces"
			styleClass="${currentMenu == 'Announces' ? 'current' : ''}">
			<bean:message key="menu.announces" />
		</s:a></li>
	<li><s:a action="/Events"
			styleClass="${currentMenu == 'Events' ? 'current' : ''}">
			<bean:message key="menu.events" />
		</s:a></li>
	<li><s:a action="/Communities"
			styleClass="${currentMenu == 'Communities' ? 'current' : ''}">
			<bean:message key="menu.communities" />
		</s:a></li>
	<li><s:a action="/Interests"
			styleClass="${currentMenu == 'Interests' ? 'current' : ''}">
			<bean:message key="menu.interests" />
		</s:a></li>

	<li><s:a action="/EditConfiguration"
			styleClass="${currentMenu == 'Configuration' ? 'current' : ''}">
			<bean:message key="menu.configuration" />
		</s:a></li>
</ul>

<div class="clear"></div>