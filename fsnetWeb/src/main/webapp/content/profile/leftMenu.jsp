<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<bean:message key="profile.leftMenu.my" />
</h2>

<ul>
	<li><s:a action="Profile.do">
			<bean:message key="showProfile.edit" />
		</s:a></li>
	<li><s:a action="/Interests">
			<bean:message key="showProfile.edit.interests" />
		</s:a></li>
	<li><s:a action="/CreateCv1">
			<bean:message key="profile.leftMenu.createCV" />
		</s:a></li>
	<li><s:a action="/DisplayCV">
			<bean:message key="profile.leftMenu.myCV" />
		</s:a></li>

</ul>

<c:choose>
	<c:when test="${sessionScope.isMasterGroup || sessionScope.isGroupResponsible}">
		<h2>
			<bean:message key="profile.LeftMenuManage" />
		</h2>

		<ul>
			<li><s:a action="/SearchGroup.do">
					<bean:message key="profile.LeftMenuManageGroups" />
					<html:param name="id" value="${requestScope.groupId}" />
				</s:a></li>
			<li><s:a action="/MemberList">
					<bean:message key="profile.LeftMenuManageMembers" />
				</s:a></li>
		</ul>
	</c:when>
	<c:otherwise>
		<h2>
			<bean:message key="profile.LeftMenuMemberGroup" />
		</h2>
		<ul>
			<li><s:a action="/ListMembersOfGroup">
					<bean:message key="profile.LeftMenuDisplayMembersGroup" />
				</s:a></li>
		</ul>

	</c:otherwise>
</c:choose>

