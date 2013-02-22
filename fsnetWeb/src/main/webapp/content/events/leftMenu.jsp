<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="events.leftMenu.title" />
</h2>
<ul>
	<li><ili:interactionFilter user="${ socialEntity }"
			right="${ rightAddEvent }">
			<s:a href="/DisplayCreateEvent">
				<s:text name="events.leftMenu.create" />
			</s:a>
		</ili:interactionFilter></li>

	<li><s:a href="/Calendar">
			<s:text name="events.leftMenu.calendar" />
		</s:a></li>

	<li><s:a href="/DisplayImportEvents">
			<s:text name="events.leftMenu.importEvents" />
		</s:a></li>

</ul>