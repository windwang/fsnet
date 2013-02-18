<%@ taglib prefix="s" uri="/struts-tags" %>
<h2>
	<s:text name="dashboard.leftMenu.interations" />
</h2>
<ul>
	<li><s:url action="/DisplayCreateAnnounce">
			<s:text name="announce.leftMenu.create" />
		</s:url></li>
	<li><s:url action="/DisplayCreateEvent">
			<s:text name="events.leftMenu.create" />
		</s:url></li>
	<li><s:url action="/Calendar">
			<s:text name="events.leftMenu.calendar" />
		</s:url></li>
</ul>