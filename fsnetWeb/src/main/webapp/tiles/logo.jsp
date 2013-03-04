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
		<a href="<s:url action='DisplayProfile'/>">
			<img src="avatar/${sessionScope.userId}.png" alt="Avatar" />
		</a>
	</div>
	<div class="group">

		<c:choose>
			<c:when test="${sessionScope.hisGroup != null}">
				<s:url action='DisplayInformationGroup' var="disInfos">
					<s:param name='idGroup'>${sessionScope.hisGroup.id}</s:param>
				</s:url>
				<s:text name="avatar.groups" />
				<s:a href="%{disInfos}">					
					${sessionScope.hisGroup.name}
				</s:a>
			</c:when>
			<c:otherwise>
				<s:text name="avatar.member.no.group" />
			</c:otherwise>
		</c:choose>
		<br>
		<c:choose>
			<c:when test="${isMasterGroup}">
				<a href="<s:url action='DisplayProfile.do'/>">
					<s:text name="leftMenu.user.group.manager" />
				</a>
			</c:when>
			<c:otherwise>
				<c:if test="${isGroupResponsible}">
					<a href="<s:url action='DisplayProfile.do'/>">
						<s:text name="leftMenu.user.group.responsible" />
					</a>
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>
</div>
