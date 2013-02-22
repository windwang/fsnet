<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<h2>
	<s:text name="communities.left.my" />
</h2>
<ul>
	<li>
		<s:a href="/DisplayCommunities">
			<s:text name="communities.leftMenu.manage" />
		</s:a>
	</li>
</ul>