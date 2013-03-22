<%@ taglib prefix="s" uri="/struts-tags"%>
<<<<<<< HEAD
=======

>>>>>>> branch 'struts' of https://code.google.com/p/fsnet
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="profile.leftMenu.my" />
</h2>
<ul>
	<li><s:a href="Profile.do">
			<s:text name="showProfile.edit" />
		</s:a></li>
	<li><s:a href="/Interests">
			<s:text name="showProfile.edit.interests" />
		</s:a></li>
	<li><s:a href="/CreateCv1">
			<s:text name="profile.leftMenu.createCV" />
		</s:a></li>
	<li><s:a href="/DisplayCV">
			<s:text name="profile.leftMenu.myCV" />
		</s:a></li>

</ul>

<c:choose>
	<c:when test="${sessionScope.isMasterGroup || sessionScope.isGroupResponsible}">
		<h2>
			<s:text name="profile.LeftMenuManage" />
		</h2>

		<ul>
<<<<<<< HEAD
			<li><s:a href="/SearchGroup.do">
					<s:text name="profile.LeftMenuManageGroups" />
					<s:param name="id" value="%{requestScope.groupId}" />
				</s:a></li>
			<li><s:a href="/MemberList">
=======
			<li><s:a action="SearchGroup.do">
					<s:text name="profile.LeftMenuManageGroups" />
					<s:param name="id" value="%{requestScope.groupId}" />
				</s:a></li>
			<li><s:a action="MemberList">
>>>>>>> branch 'struts' of https://code.google.com/p/fsnet
					<s:text name="profile.LeftMenuManageMembers" />
				</s:a></li>
		</ul>
	</c:when>
	<c:otherwise>
		<h2>
			<s:text name="profile.LeftMenuMemberGroup" />
		</h2>
		<ul>
<<<<<<< HEAD
			<li><s:a href="/ListMembersOfGroup">
					<s:text name="profile.LeftMenuDisplayMembersGroup" />
			</s:a></li>
=======
			<li><s:a action="ListMembersOfGroup">
					<s:text name="profile.LeftMenuDisplayMembersGroup" />
				</s:a></li>
>>>>>>> branch 'struts' of https://code.google.com/p/fsnet
		</ul>

	</c:otherwise>
</c:choose>

