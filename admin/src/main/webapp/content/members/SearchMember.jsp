<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<link rel=stylesheet type="text/css" href="css/osx.css" />
<!-- <script type="text/javascript" src="js/jquery.js"></script> -->
<script type="text/javascript" src="js/jquery.simplemodal.js"></script>
<script type="text/javascript" src="js/osx.js"></script>

<fieldset class="fieldsetAdmin">
  <legend class="legendAdmin"><bean:message key="members.search" /></legend>
  <html:form action="SearchMember">
	<div >
	  <table class="fieldsetTableAdmin"><tr><td>
	    <html:text property="searchText" /> 
	    <html:submit styleClass="button">
		  <bean:message key="members.searchButton" />
	     </html:submit>
	   </td></tr></table>
	</div>
  </html:form>
</fieldset>

<h3></h3>
<fieldset class="fieldsetAdmin">
  <legend class="legendAdmin"><bean:message key="members.listMembers" /></legend>
  <c:choose>
	<c:when
		test="${not empty requestScope.membersList}">
			<script type="text/javascript">
				$(document).ready(
						function pagination() {
							var nomTable = "memberTable";
							var idColonneATrier = 0;
							var sensDeTri = "asc";
							var aoColumns = [ null, null, {
								"bSortable" : false
							} ];
							miseEnPageTable(nomTable, idColonneATrier,
									sensDeTri, aoColumns, false);
						});
			</script>
			<table id="memberTable"
				class="inLineTable fieldsetTableAdmin tablesorter">
				<thead>
					<tr>
						<th><bean:message key="tableheader.name" /></th>
						<th><bean:message key="tableheader.firstname" /></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
			<c:forEach var="member"
				items="${requestScope.membersList}">
				<tr class="content">
					<td><html:link action="/DisplayMember">${member.name}
                		<html:param name="idMember" value="${member.id}" />
					</html:link></td>
					<td><html:link action="/DisplayMember">${member.firstName}
                		<html:param name="idMember" value="${member.id}" />
					</html:link></td>
					<td class="tableButton"><c:choose>
						<c:when test="${member.group.isEnabled}">
							<html:link action="/SwitchState" styleClass="button">
								<html:param name="entitySelected" value="${member.id}" />
								<c:choose>
									<c:when test="${member.isEnabled}">
										<bean:message key="members.searchDisable" />
									</c:when>
									<c:otherwise>
										<bean:message key="members.searchEnable" />
									</c:otherwise>
								</c:choose>
							</html:link>
						</c:when>
						<c:when test="${ member.group.isEnabled == false }">
							<bean:message key="members.groupDisable" />
						</c:when>
						<c:otherwise>
							<bean:message key="members.groupNull" />
						</c:otherwise>
					</c:choose></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
	    <table class="inLineTable fieldsetTableAdmin"><tr><td>
		  <bean:message key="members.noResult" />
		</td></tr></table>
	</c:otherwise>
  </c:choose>
</fieldset>

<c:if test="${!empty success}">
	<script type="text/javascript">
		jQuery(function () { popup(); });
		success = null;
	</script>
	<div id="osx-modal-content" class="simplemodal-data">
		<div id="osx-modal-title">Message</div>
		<div class="close">
			<a class="simplemodal-close" href="#">X</a>
		</div>
		<div id="osx-modal-data">
			<p><c:out value="${success}"/></p>
		</div>
	</div>
</c:if>
