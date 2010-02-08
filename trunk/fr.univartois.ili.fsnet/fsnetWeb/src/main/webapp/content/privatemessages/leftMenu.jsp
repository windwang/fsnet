<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
    <bean:message key="privatemessages.mymessages"/>
</h2>
<ul>
    <li>
        <html:link action="/DisplayCreatePrivateMessage">
            <bean:message key="privatemessages.newmessage"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Inbox">
            <bean:message key="privatemessages.inbox"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Outbox">
        <bean:message key="privatemessages.Messagessent"/>
        </html:link>
    </li>
</ul>