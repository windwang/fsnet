<%-- 
    Document   : ModifyMember
    Created on : 28 janv. 2010, 18:06:12
    Author     : Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:javascript formName="/ModifyMember" />
<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"> </script>

<h3><bean:message key="members.modify" /></h3>

<html:form action="/ModifyMember">
	<table id="ModifyMember">
		<tr>
			<td COLSPAN="2"><html:errors /></td>

		</tr>
		<tr>
			<td><label for="name"> <bean:message key="members.name" />
			</label> <html:hidden property="id" /></td>
			<td><html:text property="name" styleId="name"
				errorStyleClass="error" /></td>
		</tr>

		<tr>
			<td><label for="firstName"> <bean:message
				key="members.firstName" /> </label></td>
			<td><html:text property="firstName" styleId="firstName"
				errorStyleClass="error" /></td>
		</tr>

		<tr>
			<td><label for="email"> <bean:message
				key="members.email" /> </label></td>
			<td><html:text property="email" styleId="email"
				errorStyleClass="error" /></td>
		</tr>

		<tr>
			<td><label for="address"> <bean:message
				key="members.address" /> </label></td>
			<td><html:textarea errorStyleClass="error" property="address"
				styleId="address" /></td>
		</tr>
		<c:set var="formatBirthDay">
			<bean:write name="ModifyMemberForm" property="birthDay"
				format="dd/MM/yyyy" />
		</c:set>
		<tr>
			<td><label for="birthDay"> <bean:message
				key="members.birthDay" /> </label></td>
			<td><html:text errorStyleClass="error" styleId="birthDay"
				property="formatBirthDay" value="${formatBirthDay}">
			</html:text></td>
		</tr>


		<tr>
			<td><label for="sexe"> <bean:message key="members.sexe" />
			</label></td>
			<td><html:select property="sexe">
				<html:option value="Man">Male</html:option>
				<html:option value="Woman">Female</html:option>
			</html:select></td>
		</tr>

		<tr>
			<td><label for="job"> <bean:message key="members.job" />
			</label></td>
			<td><html:text errorStyleClass="error" property="job"
				styleId="job" /></td>
		</tr>

		<tr>
			<td><label for="phone"> <bean:message
				key="members.phone" /> </label></td>
			<td><html:text errorStyleClass="error" property="phone" /></td>
		</tr>

		<tr>
			<td colspan="2"><html:submit styleClass="button">
				<bean:message key="members.modifyUpdate" />
			</html:submit></td>
		</tr>
	</table>
</html:form>
<hr />
<bean:message key="members.herInterests"/>
<br /><br />
<table  class="inLineTable">
       <c:forEach var="interest" items="${interests}">

            <tr class="content">
                <td>
                ${interest.name}
                </td>
                  <td class="tableButton">
                    <html:link action="DeleteInterestMember" styleClass="button">
                       <bean:message key="members.deleteInterest"/>
                       <html:param name="interestSelected" value="${interest.id}"/>
                       <html:param name="idMember" value="${id}"/>
                    </html:link>
                </td>
            </tr>
            
        </c:forEach>
    </table>
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript">
    $(function() {
        $.datepicker.setDefaults($.extend( {
        	 yearRange: '-100:+1',
             changeYear: true,
             maxDate: 0,
             dateFormat: 'dd/mm/yy',
             showOn: 'button',
             buttonImage: 'images/calendar.gif',
             buttonImageOnly: true,
             showMonthAfterYear: false
        }));
        $("#birthDay").datepicker($.datepicker.regional['fr']);
    });
</script>
