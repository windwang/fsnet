<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="ili" uri="../WEB-INF/ili.tld"%>

<bean:define id="searchMessage"><bean:message key="menu.placeholder"/></bean:define>

<div id="search">
    <html:form action="/SearchMember">
        <fieldset>
            <html:text styleClass="field" property="searchText" styleId="searchText" />
            <ili:placeHolder id="searchText" value="${searchMessage}" />
            <html:submit styleClass="searchButton" value=" "/>
        </fieldset>
    </html:form>
</div>

<ul id="menu">
    <li>
    	<html:link action="/Home" styleClass="${currentMenu == 'Home' ? 'current' : ''}">
    		<bean:message key="menu.welcome"/>
    	</html:link>
    </li>
    <li>
    	<html:link action="/Members" styleClass="${currentMenu == 'Members' ? 'current' : ''}">
    		<bean:message key="menu.creation"/>
    	</html:link>
    </li>
    <li>
    	<html:link action="/MemberList" styleClass="${currentMenu == 'MemberList' ? 'current' : ''}">
    		<bean:message key="menu.members"/>
    	</html:link>
    </li>
    <li>
    	<html:link action="/GroupList" styleClass="${currentMenu == 'Groups' ? 'current' : ''}">
    		<bean:message key="menu.groups"/>
    	</html:link>
    </li>
    <li>
        <html:link action="/Announces" styleClass="${currentMenu == 'Announces' ? 'current' : ''}">
            <bean:message key="menu.announces"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Events" styleClass="${currentMenu == 'Events' ? 'current' : ''}">
            <bean:message key="menu.events"/>
        </html:link>
    </li>
    <li>
    	<html:link action="/Communities" styleClass="${currentMenu == 'Communities' ? 'current' : ''}">
    		<bean:message key="menu.communities"/>
    	</html:link>
    </li>
    <li>
    	<html:link action="/Interests" styleClass="${currentMenu == 'Interests' ? 'current' : ''}">
    		<bean:message key="menu.interests"/>
    	</html:link>
    </li>
    
    <li>
    	<html:link action="/EditConfiguration" styleClass="${currentMenu == 'Configuration' ? 'current' : ''}">
    		<bean:message key="menu.configuration"/>
    	</html:link>
    </li>
    

</ul>

<div class="clear"></div>