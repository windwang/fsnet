<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>

<div id="logo2">
	<img src="images/FSNET.png" />

	<ul>
		 <li class="group"> <bean:message key="avatar.groups" /> ${sessionScope.hisGroup.name}</li>
	</ul>
</div>
<html:link action="/DisplayProfile" styleId="userPicture">
	<img src="avatar/${sessionScope.userId}.png" />
</html:link>

<style>

.group {
	color: red;
}
</style>

