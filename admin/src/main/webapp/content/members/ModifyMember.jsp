<%-- 
    Document   : ModifyMember
    Created on : 28 janv. 2010, 18:06:12
    Author     : Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
    Modified by Morad Lyamen
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:javascript formName="/ModifyMember" />
<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js">
	
</script>

<h3>
	<bean:message key="members.modify" />
</h3>

<html:form action="/ModifyMember">
	<table id="ModifyMember">
		<tr class="errorMessage">
			<td colspan="2"><html:errors />
			</td>

		</tr>
		<tr>
			<td><label for="name"> <bean:message key="members.name" />
					: </label> <html:hidden property="id" /></td>
			<td><html:text property="name" styleId="name"
					errorStyleClass="error" /></td>
		</tr>

		<tr>
			<td><label for="firstName"> <bean:message
						key="members.firstName" /> :</label></td>
			<td><html:text property="firstName" styleId="firstName"
					errorStyleClass="error" /></td>
		</tr>

		<tr>
			<td><label for="email"> <bean:message
						key="members.email" /> :</label></td>
			<td><html:text property="email" styleId="email"
					errorStyleClass="error" /></td>
		</tr>

		<tr>
			<td><label for="parentId"> <bean:message
						key="groups.parent" /> : </label></td>
			<c:choose>
				<c:when test="${ master2 == false}">
					<td colspan="3"><html:select property="parentId"
							styleClass="select" value="${sessionScope.group2.id}">
							<html:option value="">
								<bean:message key="members.listGroups" />
							</html:option>
							<c:forEach var="socialGroup" items="${sessionScope.allGroups2}">
								<html:option value="${socialGroup.id}">${socialGroup.name}</html:option>
							</c:forEach>
						</html:select></td>
				</c:when>
				<c:otherwise>
					<td colspan="3"><html:hidden property="parentId"
							value="${sessionScope.group2.id}" /> ${ group2.name } <bean:message
							key="members.masterGroup" /></td>
				</c:otherwise>
			</c:choose>
		</tr>

		<tr>
			<td><label for="address"> <bean:message
						key="members.address" /> :</label></td>
			<td><html:textarea errorStyleClass="error" property="address"
					styleId="address" /></td>
		</tr>
		<tr>
			<td><label for="city"> <bean:message key="members.city" />
					:</label></td>
			<td><html:text errorStyleClass="error" property="city"
					styleId="city" /></td>
		</tr>
		<c:set var="formatBirthDay">
			<bean:write name="ModifyMemberForm" property="birthDay"
				format="dd/MM/yyyy" />
		</c:set>
		<tr>
			<td><label for="birthDay"> <bean:message
						key="members.birthDay" /> :</label></td>
			<td><html:text errorStyleClass="error" styleId="birthDay"
					property="formatBirthDay" value="${formatBirthDay}">
				</html:text></td>
		</tr>


		<tr>
			<td><label for="sexe"> <bean:message key="members.sexe" />
					: </label></td>
			<td><html:select property="sexe">
					<html:option value=""/>
					<html:option value="male">
						<bean:message key="members.sexe.Male" />
					</html:option>
					<html:option value="female">
						<bean:message key="members.sexe.Female" />
					</html:option>
				</html:select>
			</td>
		</tr>

		<tr>
			<td><label for="job"> <bean:message key="members.job" />
					: </label></td>
			<td><html:text errorStyleClass="error" property="job"
					styleId="job" /></td>
		</tr>

		<tr>
			<td><label for="phone"> <bean:message
						key="members.phone" /> :</label></td>
			<td><html:text errorStyleClass="error" property="phone" /></td>
		</tr>

		<tr>
			<td colspan="2"><html:submit styleClass="button">
					<bean:message key="members.modifyUpdate" />
				</html:submit></td>
		</tr>
	</table>
</html:form>

<h3>
	<bean:message key="members.herInterests" />
</h3>
<c:choose>
	<c:when
		test="${not empty requestScope.interestsMemberPaginator.resultList}">
		<div class="cloud">
			<c:forEach var="interest"
				items="${requestScope.interestsMemberPaginator.resultList}">
				<span class="tag"> <html:link action="DeleteInterestMember">
						<html:param name="interestSelected" value="${interest.id}" />
						<html:param name="idMember" value="${id}" />
						<img src="images/mini-delete.png" />
					</html:link> <html:link action="/InterestInformations">
						<html:param name="infoInterestId" value="${interest.id}" />
                            ${interest.name}
                        </html:link> </span>
			</c:forEach>
		</div>
		<div style="clear: both;"></div>
	</c:when>
	<c:otherwise>
		<bean:message key="interests.17" />
	</c:otherwise>
</c:choose>
<c:set var="paginatorInstance"
	value="${requestScope.interestsMemberPaginator}" scope="request" />
<c:set var="paginatorAction" value="/DisplayMember" scope="request" />
<c:set var="paginatorTile" value="interestsMember" scope="request" />
<c:import url="/content/pagination/Pagination.jsp" />






<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.datepicker.setDefaults($.extend(
		        {
		        	yearRange : '-100:+100',
		        	minDate:"-100Y+1D",
		        	maxDate:0,
		            changeYear: true,
		            dateFormat: 'dd/mm/yy',
		            showOn: 'button',
		            buttonImage: 'images/calendar.gif',
		            buttonImageOnly: true,
		            showMonthAfterYear: false
		        }));
		$("#birthDay").datepicker($.datepicker.regional['fr']);
	});
</script>
