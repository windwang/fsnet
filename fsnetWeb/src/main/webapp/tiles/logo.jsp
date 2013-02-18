
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="logo2">
	<c:choose>
		<c:when test="${sessionScope.hisGroup != null}">
			<img src="groups/${sessionScope.parentGroupId}.png" alt="GroupLogo" />
		</c:when>
		<c:otherwise>
			<img src="/images/FSNET.png" alt="Logo" />
		</c:otherwise>
	</c:choose>

</div>
<div class="cadreDivMenuTop">
	<h3>${sessionScope.userFirstName} ${sessionScope.userName}</h3>
	<div>
		<s:url action="/DisplayProfile">
			<img src="avatar/${sessionScope.userId}.png" alt="Avatar" />
		</s:url>
	</div>
	<div class="group">

		<c:choose>
			<c:when test="${sessionScope.hisGroup != null}">
				<s:text name="avatar.groups" />
				<s:url action="/DisplayInformationGroup">
					<s:param name="idGroup" value="%{sessionScope.hisGroup.id}" />
		${sessionScope.hisGroup.name}
	</s:url>
			</c:when>
			<c:otherwise>
				<s:text name="avatar.member.no.group" />
			</c:otherwise>
		</c:choose>
		<br>
		<c:choose>
			<c:when test="${isMasterGroup}">
				<s:url action="DisplayProfile.do">
					<s:text name="leftMenu.user.group.manager" />
				</s:url>
			</c:when>
			<c:otherwise>
				<c:if test="${isGroupResponsible}">
					<s:url action="DisplayProfile.do">
						<s:text name="leftMenu.user.group.responsible" />
					</s:url>
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>
</div>
