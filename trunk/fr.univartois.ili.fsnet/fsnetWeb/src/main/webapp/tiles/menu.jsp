<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<ul id="menu">
	<li><a class="current" href="#">HOME</a></li>
	<li><html:link action="/Contacts">CONTACTS</html:link></li>
	<li><html:link action="/Interests">INTERESTS</html:link></li>
	<li><html:link action="/Profile">PROFILE</html:link></li>
	<li><html:link action="/Announces">ANNOUCEMENTS</html:link></li>
	<li><html:link action="/Events">EVENTS</html:link></li>
	<li><html:link action="/Hubs">HUBS</html:link></li>
</ul>

<div id="logout">
	<a class="button" href="Logout">Logout</a>
</div>

<div class="clear"></div>