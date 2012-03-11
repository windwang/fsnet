<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<ul>
	<li><html:link action="/DisplayCreateTopicMessage">
			<html:param name="topicId" value="${param.topicId}" />
			<bean:message key="topics.leftMenu.createMessage" />
		</html:link></li>
</ul>