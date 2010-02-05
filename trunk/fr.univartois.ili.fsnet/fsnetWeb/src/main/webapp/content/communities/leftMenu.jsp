<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
	My Communities
</h2>
<ul>
    <li>
        <html:link action="/DisplayCreateCommunity">
       Create a Community
        </html:link>
    </li>
    <li>
        <html:link action="/DisplayCommunities">
			Manage your Communities
        </html:link>
    </li>
</ul>