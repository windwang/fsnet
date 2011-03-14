<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="logo2">
	<img src="images/FSNET.png" />

</div>
<html:link action="/DisplayProfile" styleId="userPicture">
	<img src="avatar/${sessionScope.userId}.png" />
</html:link>

<div class="group"><bean:message key="avatar.groups" /> ${sessionScope.hisGroup.name}
<br>
<c:if test="${isMasterGroup}">
<html:link action="DisplayProfile.do" styleId="">
	<bean:message key="left.group.manager"/>
</html:link>
</c:if>
</div>

<style>
.group {
	float : left;
}
</style>

