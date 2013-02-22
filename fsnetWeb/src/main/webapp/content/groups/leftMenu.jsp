<%@ taglib prefix ="s" uri="/struts-tags" %>
<h2>
	<s:text name="groups.leftMenu" />
</h2>
<ul>
	<li><s:a href="/SearchGroup">
			<s:text name="group.listGroups" />
		</s:a></li>
	<li><s:a href="/Groups.do">
			<s:text name="groups.createGroup" />
		</s:a></li>
	<li><s:a href="/MemberList">
			<s:text name="members.listMembers" />
		</s:a></li>
	<li><s:a href="/Members.do">
			<s:text name="members.create" />
		</s:a></li>

</ul>