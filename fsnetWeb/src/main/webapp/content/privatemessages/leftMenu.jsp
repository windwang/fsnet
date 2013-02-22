<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h2>
	<bean:message key="privatemessages.mymessages" />
</h2>
<ul>
	<ili:interactionFilter user="${ socialEntity }"
		right="${ rightSendMessage }">
		<li><s:a action="/DisplayCreatePrivateMessage">
				<bean:message key="privatemessages.newmessage" />
			</s:a></li>
	</ili:interactionFilter>
	<li><s:a action="/Inbox">
			<bean:message key="privatemessages.inbox" />
		</s:a></li>
	<li><s:a action="/Outbox">
			<bean:message key="privatemessages.Messagessent" />
		</s:a></li>
</ul>