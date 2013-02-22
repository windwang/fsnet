<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<ul>

	<li><s:a href="/DisplayCreateTopicMessage">
			<s:param name="topicId" value="%{param.topicId}" />
			<s:text name="topics.leftMenu.createMessage" />

		</s:a></li>
</ul>