<!-- SAID Mohamed <simo.said09 at gmail.com> -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3><bean:message key="group.listGroups" /></h3>
<c:choose>
	<c:when test="${! empty requestScope.groupsListPaginator.resultList}">
		<table class="inLineTable">
			<c:forEach var="group"
				items="${requestScope.groupsListPaginator.resultList}">
				<tr>
					<td>
					<html:link action="/DisplayGroup">${group.name} 
                			<html:param name="idGroup" value="${group.id}" />
					</html:link>
						<!--${group.name}-->
					</td>
					<td class="tableButton"><html:link action="/SwitchStateGroup"
						styleClass="button">
						<html:param name="groupSelected" value="${group.id}" />
						<c:choose>
							<c:when test="${group.isEnabled}">
								<bean:message key="members.searchDisable" />
							</c:when>
							<c:otherwise>
								<bean:message key="members.searchEnable" />
							</c:otherwise>
						</c:choose>
					</html:link></td>
					<td><bean:message key="groups.by" /> <ili:getSocialEntityInfos
						socialEntity="${group.masterGroup}" /></td>
				</tr>
			</c:forEach>
		</table>

		
		<c:set var="paginatorInstance"
			value="${requestScope.groupsListPaginator}" scope="request" />
		
		<c:set var="paginatorAction" value="/SearchGroup" scope="request" />
		<c:set var="paginatorTile" value="groupsList" scope="request" />
		<c:import url="/content/pagination/Pagination.jsp" />






	</c:when>
	<c:otherwise>
		<p><bean:message key="group.noResult" />.</p>
	</c:otherwise>
</c:choose>