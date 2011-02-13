<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:set var="inputTextContent">
	<bean:message key="menu.7"/>
</c:set>

<div id="search">
    <html:form action="/SearchMember">
        <fieldset>
            <html:text styleClass="field" property="searchText" onclick="this.value=''" value="${inputTextContent}"/>
            <html:submit styleClass="searchButton" value=" "/>
        </fieldset>
    </html:form>
</div>

<ul id="menu">
    <li>
    	<html:link action="/Home" styleClass="${currentMenu == 'Home' ? 'current' : ''}">
    		<bean:message key="menu.0"/>
    	</html:link>
    </li>
    <li>
    	<html:link action="/Members" styleClass="${currentMenu == 'Members' ? 'current' : ''}">
    		<bean:message key="menu.9"/>
    	</html:link>
    </li>
    <li>
    	<html:link action="/MemberList" styleClass="${currentMenu == 'MemberList' ? 'current' : ''}">
    		<bean:message key="menu.1"/>
    	</html:link>
    </li>
    <li>
    	<html:link action="/Interests" styleClass="${currentMenu == 'Interests' ? 'current' : ''}">
    		<bean:message key="menu.2"/>
    	</html:link>
    </li>
    <li>
    	<html:link action="/Communities" styleClass="${currentMenu == 'Communities' ? 'current' : ''}">
    		<bean:message key="menu.8"/>
    	</html:link>
    </li>
    <li>
    	<html:link action="/EditConfiguration" styleClass="${currentMenu == 'Configuration' ? 'current' : ''}">
    		<bean:message key="menu.3"/>
    	</html:link>
    </li>

</ul>

<div class="clear"></div>