<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<bean:message key="dashboard.leftMenu.interations" />
</h2>
<ul>
	<ili:interactionFilter user="${ socialEntity }"
		right="${ rightAddAnnounce }">
		<li><s:a action="/DisplayCreateAnnounce">
				<bean:message key="announce.leftMenu.create" />
			</s:a></li>
	</ili:interactionFilter>
	<ili:interactionFilter user="${ socialEntity }"
		right="${ rightAddEvent }">
		<li><s:a action="/DisplayCreateEvent">
				<bean:message key="events.leftMenu.create" />
			</s:a></li>
	</ili:interactionFilter>
	<li><s:a action="/DisplayCommunities">
			<bean:message key="communities.leftMenu.manage" />
		</s:a></li>
</ul>