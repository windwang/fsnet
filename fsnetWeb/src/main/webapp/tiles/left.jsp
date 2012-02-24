<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
	<bean:message key="dashboard.leftMenu.interations" />
</h2>
<ul>
	<li><html:link action="/DisplayCreateAnnounce">
			<bean:message key="announce.leftMenu.create" />
		</html:link></li>
	<li><html:link action="/DisplayCreateEvent">
			<bean:message key="events.leftMenu.create" />
		</html:link></li>
	<li><html:link action="/Calendar">
			<bean:message key="events.leftMenu.calendar" />
		</html:link></li>
</ul>