<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
	<bean:message key="members.leftMenu" />
</h2>
<ul>
	<li><html:link action="/SearchGroup">
			<bean:message key="group.listGroups" />
		</html:link></li>
	<li><html:link action="/Groups.do">
			<bean:message key="groups.createGroup" />
		</html:link></li>
	<li><html:link action="/MemberList">
			<bean:message key="members.listMembers" />
		</html:link></li>
	<li><html:link action="/Members.do">
			<bean:message key="members.create" />
		</html:link></li>

</ul>