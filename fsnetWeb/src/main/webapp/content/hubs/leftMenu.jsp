<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
	<bean:message key="hubs.leftMenu.title" />
</h2>
<ul>
	<li><s:a action="/DisplayCommunity">
			<html:param name="communityId" value="${param.communityId}" />
			<bean:message key="hubs.leftMenu.manageCommunity" />
		</s:a></li>
	<li><s:a action="/DisplayYourHubs">
			<html:param name="communityId" value="${param.communityId}" />
			<bean:message key="hubs.leftMenu.manage" />
		</s:a></li>
</ul>