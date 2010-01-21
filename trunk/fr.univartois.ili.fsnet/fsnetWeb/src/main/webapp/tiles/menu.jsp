<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul id="menu">
	<li><html:link action="/Home" styleClass="${currentMenu == 'Home' ? 'current' : ''}">HOME</html:link></li>
    <li><html:link action="/Contacts" styleClass="${currentMenu == 'Contacts' ? 'current' : ''}">CONTACTS</html:link></li>
	<li><html:link action="/Interests" styleClass="${currentMenu == 'Interests' ? 'current' : ''}">INTERESTS</html:link></li>
	<li><html:link action="/Profile" styleClass="${currentMenu == 'Profile' ? 'current' : ''}">PROFILE</html:link></li>
	<li><html:link action="/Announces" styleClass="${currentMenu == 'Announces' ? 'current' : ''}">ANNOUCEMENTS</html:link></li>
	<li><html:link action="/DisplayEvents" styleClass="${currentMenu == 'Events' ? 'current' : ''}">EVENTS</html:link></li>
	<li><html:link action="/Hubs" styleClass="${currentMenu == 'Hubs' ? 'current' : ''}">HUBS</html:link></li>
</ul>

<div id="logout">
	<a class="button" href="Logout">Logout</a>
</div>

<div class="clear"></div>