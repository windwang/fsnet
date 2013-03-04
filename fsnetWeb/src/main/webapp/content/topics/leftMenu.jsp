<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h2>
	<s:text name="topics.leftMenu.title" />
</h2>
<ul>
	<li><s:a href="/DisplayCreateTopic">
			<s:param name="hubId" value="%{param.hubId}" />
			<s:text name="topics.leftMenu.create" />
		</s:a></li>
	<li><s:a href="/DisplayYourTopics">
			<s:param name="hubId" value="%{param.hubId}" />
			<s:text name="topics.leftMenu.manage" />

		</s:a></li>
</ul>