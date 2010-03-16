<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
	<bean:message key="communities.communitiesLeftMenu"/>
</h2>
<ul>
    <li>
        <html:link action="/DisplayCommunities">
			 <bean:message key="communities.manageLeftMenu"/>
        </html:link>
    </li>
</ul>