<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="events.leftMenu.title" />
</h2>
<ul>
	<li><ili:interactionFilter user="${ socialEntity }"
			right="${ rightAddEvent }">
			<a href="<s:url action='DisplayCreateEvent'/>">
				<s:text name="events.leftMenu.create" />
			</a>
		</ili:interactionFilter></li>

	<li><a href="<s:url action='Calendar'/> ">
			<s:text name="events.leftMenu.calendar" />
		</a></li>

	<li><a href="<s:url action='DisplayImportEvents'/>">
			<s:text name="events.leftMenu.importEvents" />
		</a></li>

</ul>