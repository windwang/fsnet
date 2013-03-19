<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<s:debug/>
<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="groups.titleInformation" />
	</legend>

	<c:choose>
		<c:when test="${ socialGroup != null }">
			<table class="inLineTable tableStyle">
				<tr>
					<td>
						<fieldset class="inLinefieldset">
							<legend>
								<s:text name="groups.title" />
							</legend>

							<div>
								<c:forEach var="group" items="${antecedantsOfGroup}">

									<s:url action="DisplayInformationGroup" var="displayInfoGroup1">
										<s:param name="idGroup"> ${group.id}</s:param>
									</s:url>
									<a href="<s:property  value="#displayInfoGroup"/>">
										${group.name}</a>

									<s:text name="groups.addGroups" />
								</c:forEach>

								<s:url action="DisplayInformationGroup" var="displayInfoGroup">
									<s:param name="idGroup"> ${socialGroup.id}</s:param>
								</s:url>
								<a href="<s:property  value="#displayInfoGroup"/>">
									${socialGroup.name}</a>
							</div>
						</fieldset>
					</td>
				</tr>


				<tr>
					<td>
						<fieldset class="inLinefieldset">
							<legend>
								<s:text name="groups.description.message" />
							</legend>

							<p>${ socialGroup.description }</p>
						</fieldset>
					</td>
				</tr>

				<tr>
					<td>
						<fieldset class="inLinefieldset">
							<legend>
								<s:text name="groups.listGroup" />
							</legend>

							<c:forEach items="${childsOfGroup}" var="cGroup"
								varStatus="status">

								<s:url action="DisplayInformationGroup" var="displayInfoGroup2">
									<s:param name="idGroup"> ${cGroup.id}</s:param>
								</s:url>
								<a href="<s:property  value="#displayInfoGroup2"/>">
									${cGroup.name}</a>
								<c:if test="${status.count < fn:length(childsOfGroup)}"> | </c:if>
							</c:forEach>
						</fieldset>
					</td>
				</tr>

				<tr>
					<td>
						<fieldset class="inLinefieldset">
							<legend>
								<s:text name="groups.listMember" />
							</legend>

							<c:forEach var="member" items="${allMembers}">
								<a href="/fsnetWeb/DisplayProfile.do?id=${member.id}"
									class="miniature"> <img
									title="${member.name} ${member.firstName}"
									src="miniature/${member.id}.png" alt="miniature" />
								</a>
							</c:forEach>
						</fieldset>
					</td>
				</tr>

				<c:if test="${ sessionScope.isMasterGroup }">
					<tr>
						<td>
							<fieldset class="inLinefieldset">
								<legend>
									<s:text name="groups.rights.title" />
								</legend>
								<c:choose>
									<c:when test="${ fn:length(socialGroup.rights) != 0 }">

										<c:forEach var="right" items="${ socialGroup.rights }">

											<p>
												${ right }
												<s:text name="%{right}" />
											</p>
										</c:forEach>

									</c:when>
									<c:otherwise>
										<p>
											<s:text name="groups.noRights.title" />
										</p>
									</c:otherwise>
								</c:choose>
							</fieldset>
						</td>
					</tr>
				</c:if>
			</table>
		</c:when>
		<c:otherwise>
			<p>
				<s:text name="groups.noGroup" />
			</p>
		</c:otherwise>

	</c:choose>

</fieldset>