<%@ taglib prefix="s" uri="/struts-tags"%>
<h2>
	<s:text name="hubs.leftMenu.title" />
</h2>
<ul>
<<<<<<< HEAD
	<li><s:a href="/DisplayCommunity">
			<s:param name="communityId" value="%{param.communityId}" />
			<s:text name="hubs.leftMenu.manageCommunity" />
		</s:a></li>
	<li><s:a href="/DisplayYourHubs">
			<s:param name="communityId" value="%{param.communityId}" />
			<s:text name="hubs.leftMenu.manage" />
=======
	<li><s:a action="/DisplayCommunity">
			<html:param name="communityId" value="${param.communityId}" />
			<bean:message key="hubs.leftMenu.manageCommunity" />
		</s:a></li>
	<li><s:a action="/DisplayYourHubs">
			<html:param name="communityId" value="${param.communityId}" />
			<bean:message key="hubs.leftMenu.manage" />
>>>>>>> 75d3d39903713564d348b5ead824e237db25c0ee
		</s:a></li>
</ul>