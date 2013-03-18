<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="dashboard.leftMenu.interations" />
</h2>
<ul>
	<ili:interactionFilter user="${ socialEntity }"
		right="${ rightAddAnnounce }">
		<li><s:a action="/DisplayCreateAnnounce">
				<s:text name="announce.leftMenu.create" />
			</s:a></li>
	</ili:interactionFilter>
	<ili:interactionFilter user="${ socialEntity }"
		right="${ rightAddEvent }">
		<li><s:a action="/DisplayCreateEvent">
				<s:text name="events.leftMenu.create" />
			</s:a></li>
	</ili:interactionFilter>
	<li><s:a action="DisplayCommunities">
			<s:text name="communities.leftMenu.manage" />
		</s:a></li>
</ul>