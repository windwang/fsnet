<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<bean:message key="dashboard.leftMenu.interations" />
</h2>
<ul>
	<ili:interactionFilter user="${ socialEntity }"
		right="${ rightAddAnnounce }">
		<li><html:link action="/DisplayCreateAnnounce">
				<bean:message key="announce.leftMenu.create" />
			</html:link></li>
	</ili:interactionFilter>
	<ili:interactionFilter user="${ socialEntity }"
		right="${ rightAddEvent }">
		<li><html:link action="/DisplayCreateEvent">
				<bean:message key="events.leftMenu.create" />
			</html:link></li>
	</ili:interactionFilter>
	<li><html:link action="/DisplayCommunities">
			<bean:message key="communities.manageLeftMenu" />
		</html:link></li>
</ul>