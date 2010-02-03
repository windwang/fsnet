
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

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
        <html:link action="/Inbox" styleClass="${currentMenu == 'Messages' ? 'current' : ''}">
            <bean:message key="menu.8"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Contacts" styleClass="${currentMenu == 'Contacts' ? 'current' : ''}">
            <bean:message key="menu.1"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Interests" styleClass="${currentMenu == 'Interests' ? 'current' : ''}">
            <bean:message key="menu.2"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Profile" styleClass="${currentMenu == 'Profile' ? 'current' : ''}">
            <bean:message key="menu.3"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Announces" styleClass="${currentMenu == 'Announces' ? 'current' : ''}">
            <bean:message key="menu.4"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Events" styleClass="${currentMenu == 'Events' ? 'current' : ''}">
            <bean:message key="menu.5"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Hubs" styleClass="${currentMenu == 'Hubs' ? 'current' : ''}">
            <bean:message key="menu.6"/>
        </html:link>
    </li>
</ul>

<div class="clear"></div>