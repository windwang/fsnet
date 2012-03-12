<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
	<bean:message key="communities.left.my" />
</h2>
<ul>
	<li><html:link action="/DisplayCommunities">
			<bean:message key="communities.leftMenu.manage" />
		</html:link></li>
</ul>