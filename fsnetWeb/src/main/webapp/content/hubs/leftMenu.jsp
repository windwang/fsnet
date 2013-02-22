<%@ taglib prefix="s" uri="/struts-tags"%>
<h2>
	<s:text name="hubs.leftMenu.title" />
</h2>
<ul>
	<li><s:a href="/DisplayCommunity">
			<s:param name="communityId" value="%{param.communityId}" />
			<s:text name="hubs.leftMenu.manageCommunity" />
		</s:a></li>
	<li><s:a href="/DisplayYourHubs">
			<s:param name="communityId" value="%{param.communityId}" />
			<s:text name="hubs.leftMenu.manage" />
		</s:a></li>
</ul>