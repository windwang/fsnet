<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
    <bean:message key="announce.leftMenu"/>
</h2>

<ul>
    <li>
    	<ili:interactionFilter user="${ socialEntity }" right="${ rightAddAnnounce }">
        <html:link action="/DisplayCreateAnnounce">
			<bean:message key="left.3"/> 
        </html:link>
        </ili:interactionFilter>
    </li>
</ul>