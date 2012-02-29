<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
	<bean:message key="hubs.leftMenu.title" />
</h2>
<ul>
	<li><html:link action="/DisplayCreateHub">
			<html:param name="communityId" value="${param.communityId}" />
			<bean:message key="hubs.leftMenu.create" />
		</html:link></li>
	<li><html:link action="/DisplayYourHubs">
			<html:param name="communityId" value="${param.communityId}" />
			<bean:message key="hubs.leftMenu.manage" />
		</html:link></li>
</ul>