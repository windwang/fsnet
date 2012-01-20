<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
    <bean:message key="events.eventsLeftMenu"/>
</h2>
<ul>
    <li>
    	<ili:interactionFilter user="${ socialEntity }" right="${ rightAddEvent }">
        	<html:link action="/DisplayCreateEvent">
				<bean:message key="left.4"/> 
        	</html:link>
        </ili:interactionFilter>
    </li>
</ul>