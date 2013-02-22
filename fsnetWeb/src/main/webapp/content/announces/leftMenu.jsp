<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="announce.leftMenu.my" />
</h2>

<ul>
	<li><ili:interactionFilter user="${ socialEntity }"
			right="${ rightAddAnnounce }">
			<s:a action="/DisplayCreateAnnounce">
				<s:text name="announce.leftMenu.create" />
			</s:a>
		</ili:interactionFilter></li>
</ul>