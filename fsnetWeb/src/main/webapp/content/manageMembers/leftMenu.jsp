<%@ taglib prefix="s" uri="/struts-tags"%>
<h2>
	<s:text name="members.leftMenu" />
</h2>
<ul>
	<li><s:url action="/SearchGroup">
			<s:text name="group.listGroups" />
		</s:url></li>
	<li><s:url action="/Groups.do">
			<s:text name="groups.createGroup" />
		</s:url></li>
	<li><s:url action="/MemberList">
			<s:text name="members.listMembers" />
		</s:url></li>
	<li><s:url action="/Members.do">
			<s:text name="members.create" />
		</s:url></li>

</ul>