<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="announce.leftMenu.my" />
</h2>

<ul>
	<li><ili:interactionFilter user="${ socialEntity }"
			right="${ rightAddAnnounce }">
			<s:a action="/DisplayCreateAnnounce">
<<<<<<< HEAD
				<s:text name="announce.leftMenu.create" />
=======
				<bean:message key="announce.leftMenu.create" />
>>>>>>> b51606823970ae78ca4476d53fe647d5ace62683
			</s:a>
		</ili:interactionFilter></li>
</ul>