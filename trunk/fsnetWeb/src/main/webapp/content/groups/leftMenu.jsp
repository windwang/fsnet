<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
    <bean:message key="groups.leftMenu"/>
</h2>
<ul>
    <li>
        <html:link action="">
            <bean:message key="groups.manageRights"/>
        </html:link>
    </li>
    <li>
        <html:link action="/SearchGroup">
            <bean:message key="groups.manageGroups"/>
        </html:link>
    </li>
</ul>