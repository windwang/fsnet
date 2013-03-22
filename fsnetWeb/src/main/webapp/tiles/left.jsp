<%@ taglib prefix="s" uri="/struts-tags" %>
<h2>
	<s:text name="dashboard.leftMenu.interations" />
</h2>
<ul>
	<li><s:a action="/DisplayCreateAnnounce">
			<s:text name="announce.leftMenu.create" />
		</s:a></li>
	<li><s:a action="/DisplayCreateEvent">
			<s:text name="events.leftMenu.create" />
		</s:a></li>
	<li><s:a action="/Calendar">
			<s:text name="events.leftMenu.calendar" />
		</s:a></li>
</ul>