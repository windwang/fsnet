<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
	<bean:message key="topics.leftMenu.title" />
</h2>
<ul>
	<li><html:link action="/DisplayCreateTopic">
			<html:param name="hubId" value="${param.hubId}" />
			<bean:message key="topics.leftMenu.create" />
		</html:link></li>
	<li><html:link action="/DisplayYourTopics">
			<html:param name="hubId" value="${param.hubId}" />
			<bean:message key="topics.leftMenu.manage" />
		</html:link></li>
</ul>