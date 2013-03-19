<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="announce.leftMenu.my" />

</h2>

<ul>
	<li><ili:interactionFilter user="${ socialEntity }"
			right="${ rightAddAnnounce }">
		</ili:interactionFilter> 
		<a href="<s:url action='DisplayCreateAnnounce'/> "> <s:text
				name="announce.leftMenu.create" /></a></li>
</ul>