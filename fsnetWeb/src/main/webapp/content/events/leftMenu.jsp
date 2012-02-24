<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
    <bean:message key="events.leftMenu.title"/>
</h2>
<ul>
    <li>
    	<ili:interactionFilter user="${ socialEntity }" right="${ rightAddEvent }">
        	<html:link action="/DisplayCreateEvent">
				<bean:message key="events.leftMenu.create"/> 
        	</html:link>
        </ili:interactionFilter>
    </li>
    
   <li>
        <html:link action="/Calendar">
			<bean:message key="events.leftMenu.calendar"/> 
        </html:link>
   </li>
    
</ul>