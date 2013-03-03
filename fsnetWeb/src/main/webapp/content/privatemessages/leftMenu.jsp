<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h2>
	<s:text name="privatemessages.mymessages" />
</h2>
<ul>
	<ili:interactionFilter user="${ socialEntity }"
		right="${ rightSendMessage }">
		<li><s:a action="/DisplayCreatePrivateMessage">
				<s:text name="privatemessages.newmessage" />
			</s:a></li>
	</ili:interactionFilter>
	<li><s:a action="/Inbox">
			<s:text name="privatemessages.inbox" />
		</s:a></li>
	<li><s:a action="/Outbox">
			<s:text name="privatemessages.Messagessent" />
		</s:a></li>
</ul>