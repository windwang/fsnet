<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:javascript formName="/ModifyMember" />
<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js">
	
</script>

<fieldset class="fieldsetCadre">
	<legend >
		<s:text name="members.modify" />
	</legend>

	<s:form action="/ModifyMember">
		<table id="ModifyMember" class="inLineTable tableStyle">
			<tr>
				<td><label for="name"> <s:text name="members.name" />
				</label> <s:hidden property="id" /></td>
				<td><s:textfield property="name" styleId="name"
						csstyleClass="error" />
					<div class="errorMessage">
						<s:fielderror name="name" />
					</div></td>
			</tr>

			<tr>
				<td><label for="firstName"> <s:text name="members.firstName" />
				</label></td>
				<td><s:textfield property="firstName" styleId="firstName"
						csstyleClass="error" />
					<div class="errorMessage">
						<s:fielderror name="firstName" />
					</div></td>
			</tr>

			<tr>
				<td><label for="email"> <s:text name="members.email" />
				</label></td>
				<td><s:textfield property="email" styleId="email"
						csstyleClass="error" />
					</td>
			</tr>

			<tr>
				<td><label for="parentId"> <s:text name="groups.parent" />
				</label></td>
				<c:choose>
					<c:when test="${ master == false }">
						<td colspan="3"><s:select property="parentId"
								styleClass="select" value="%{sessionScope.group.id }"
								styleId="parentId" list="%{sessionScope.allGroups}" listKey="%{sessionScope.allGroups.id}" listValue="%{sessionScope.allGroups.name}">
<!-- 								<html:option value="" disabled="true"/> -->
<%-- 								<c:forEach var="socialGroup" items="${sessionScope.allGroups}"> --%>
<%-- 									<html:option value="${socialGroup.id}">${socialGroup.name}</html:option> --%>
<%-- 								</c:forEach> --%>
							</s:select>
<!-- 							<html:select property="parentId" -->
<%-- 								styleClass="select" value="${ sessionScope.group.id }" --%>
<!-- 								styleId="parentId"> -->
<!-- 								<html:option value="" disabled="true"/> -->
<%-- 								<c:forEach var="socialGroup" items="${sessionScope.allGroups}"> --%>
<%-- 									<html:option value="${socialGroup.id}">${socialGroup.name}</html:option> --%>
<%-- 								</c:forEach> --%>
<!-- 							</html:select> -->
							</td>
					</c:when>
					<c:otherwise>
						<td colspan="3"><s:hidden property="parentId"
								styleClass="select" value="%{ sessionScope.group.id }" /> ${
							sessionScope.group.name } <s:text name="members.masterGroup" /></td>
					</c:otherwise>
				</c:choose>
			</tr>

			<tr>
				<td><label for="address"> <s:text name="members.address" />
				</label></td>
				<td><s:textarea csstyleClass="error" property="address"
						styleId="address" /></td>
			</tr>

			<tr>
				<td><label for="city"> <s:text name="members.city" />
				</label></td>
				<td><s:textfield csstyleClass="error" property="city"
						styleId="city" />
					<div class="errorMessage">
						<s:fielderror name="city" />
					</div></td>
			</tr>

			<c:set var="formatBirthDay">
				<s:property value="birthDay" />
<!-- 				<bean:write  value="birthDay" format="dd/MM/yyyy" /> -->
			</c:set>
			<tr>
				<td><label for="birthDay"> <s:text name="members.birthDay" />
				</label></td>
				<td><s:textfield csstyleClass="error" styleId="birthDay"
						property="formatBirthDay" value="%{formatBirthDay}">
					</s:textfield></td>
			</tr>

			<tr>
				<td><label for="sexe"> <s:text name="members.sexe" />
				</label></td>
				<td><s:select property="sexe" styleId="sexe" list="%{members.sexe}" listKey="%{members.sexe.id}" listValue="%{members.sexe.name}" >
<!-- 						<html:option value="" /> -->
<!-- 						<html:option value="male"> -->
<%-- 							<s:text name="members.sexe.Male" /> --%>
<!-- 						</html:option> -->
<!-- 						<html:option value="female"> -->
<%-- 							<s:text name="members.sexe.Female" /> --%>
<!-- 						</html:option> -->
					</s:select>
<!-- 					<html:select property="sexe" styleId="sexe"> -->
<!-- 						<html:option value="" /> -->
<!-- 						<html:option value="male"> -->
<%-- 							<s:text name="members.sexe.Male" /> --%>
<!-- 						</html:option> -->
<!-- 						<html:option value="female"> -->
<%-- 							<s:text name="members.sexe.Female" /> --%>
<!-- 						</html:option> -->
<!-- 					</html:select> -->
					<div class="errorMessage">
						<s:fielderror name="sexe" />
					</div></td>
			</tr>

			<tr>
				<td><label for="job"> <s:text name="members.job" />
				</label></td>
				<td><s:textfield csstyleClass="error" property="job"
						styleId="job" />
					<div class="errorMessage">
						<s:fielderror name="job" />
					</div></td>
			</tr>

			<tr>
				<td><label for="phone"> <s:text name="members.phone" />
				</label></td>
				<td><s:textfield csstyleClass="error" property="phone"
						styleId="phone" />
					<div class="errorMessage">
						<s:fielderror name="phone" />
					</div></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><s:submit type="button" styleClass="button">
						<s:text name="members.modifyUpdate" />
					</s:submit></td>
			</tr>
		</table>
	</s:form>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend >
		<s:text name="members.herInterests" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td><c:choose>
					<c:when
						test="${not empty requestScope.interestsMemberPaginator.resultList}">
						<div class="cloud">
							<c:forEach var="interest"
								items="${requestScope.interestsMemberPaginator.resultList}">
								<span class="tag"> <s:url
										action="DeleteInterestMember">
										<s:param name="interestSelected" value="%{interest.id}" />
										<s:param name="idMember" value="%{id}" />
										<img src="images/mini-delete.png" alt="delete" />
									</s:url> <s:url action="/InterestInformations">
										<s:param name="infoInterestId" value="%{interest.id}" />
                            ${interest.name}
                        </s:url>
								</span>
							</c:forEach>
						</div>
						<div style="clear: both;"></div>
					</c:when>
					<c:otherwise>
						<s:text name="interests.none" />
					</c:otherwise>
				</c:choose></td>
		</tr>
	</table>
</fieldset>
<c:set var="paginatorInstance"
	value="${requestScope.interestsMemberPaginator}" scope="request" />
<c:set var="paginatorAction" value="/DisplayMember" scope="request" />
<c:set var="paginatorTile" value="interestsMember" scope="request" />
<c:import url="/content/pagination/Pagination.jsp" />

<script type="text/javascript">
	$(function() {
		$.datepicker.setDefaults($.extend({
			yearRange : '-100:+100',
			minDate : '-100y',
			changeYear : true,
			maxDate : '+0D',
			dateFormat : 'dd/mm/yy',
			showOn : 'both',
			buttonImage : 'images/calendar.gif',
			buttonImageOnly : true,
			showMonthAfterYear : false
		}));
		
		$.datepicker.setDefaults($.datepicker.regional['fr']);
		$("#birthDay").datepicker();
	});
</script>
