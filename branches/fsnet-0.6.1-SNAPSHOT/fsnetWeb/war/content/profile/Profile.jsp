<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3>
    <bean:message key="updateProfile.title"/>
</h3>


<html:form action="/ModifyProfile">
    <table id="ModifyProfile">
        <tr>
            <td>
                <label for="name">
                    <bean:message key="updateProfile.name"/> :
                </label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="name"  styleId="name"/>
            </td>
        </tr>
        <logic:messagesPresent property="name">
            <tr>
                <td colspan="2">
                    <html:errors property="name"/>
                </td>
            </tr>
        </logic:messagesPresent>
        <tr>
            <td>
                <label for="firstName">
                    <bean:message key="updateProfile.firstname"/> :
                </label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="firstName"   styleId="firstName"/>
            </td>
        </tr>
        <logic:messagesPresent property="firstName">
            <tr>
                <td colspan="2">
                    <html:errors property="firstName"/>
                </td>
            </tr>
        </logic:messagesPresent>
        <tr>
            <td>
                <label for="adress">
                    <bean:message key="updateProfile.adress"/> :
                </label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="adress"  styleId="adress"/>
            </td>
        </tr>
        <logic:messagesPresent property="adress">
            <tr>
                <td colspan="2">
                    <html:errors property="adress"/>
                </td>
            </tr>
        </logic:messagesPresent>
        <tr>
            <td>
                <label for="city">
                    <bean:message key="updateProfile.city"/> :
                </label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="city" styleId="adress"/>
            </td>
        </tr>
        <logic:messagesPresent property="city">
            <tr>
                <td colspan="2">
                    <html:errors property="city"/>
                </td>
            </tr>
        </logic:messagesPresent>
        <tr>
            <td>
                <label for="dateOfBirth">
                    <bean:message key="updateProfile.dateOfBirth"/> :
                </label>
            </td>
            <td>
                <html:text errorStyleClass="error" styleId="dateOfBirth" property="dateOfBirth"  />
            </td>
        </tr>
        <logic:messagesPresent property="dateOfBirth">
            <tr>
                <td colspan="2">
                    <html:errors property="dateOfBirth"/>
                </td>
            </tr>
        </logic:messagesPresent>
        <tr>
            <td>
                <label for="sexe">
                    <bean:message key="updateProfile.sexe"/> :
                </label>
            </td>
            <td>
                <html:select property="sexe" >
                	<html:option value=""/>
                    <html:option value="male">
                    	<bean:message key="updateProfile.sexe.male"/>
                    </html:option>
                    <html:option value="female">
                    	<bean:message key="updateProfile.sexe.female"/>
                    </html:option>
                </html:select>
            </td>
        </tr>
        <logic:messagesPresent property="sexe">
            <tr>
                <td colspan="2">
                    <html:errors property="sexe"/>
                </td>
            </tr>
        </logic:messagesPresent>

        <tr>
            <td>
                <label for="job">
                    <bean:message key="updateProfile.job"/> :
                </label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="job"  styleId="job"/>
            </td>
        </tr>
        <logic:messagesPresent property="job"> 
            <tr>
                <td colspan="2">
                    <html:errors property="job"/>
                </td>
            </tr>
        </logic:messagesPresent>
        <tr>
            <td>
                <label for="mail">
                    <bean:message key="updateProfile.email"/> :
                </label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="mail"  />
            </td>
        </tr>
        <logic:messagesPresent property="mail">
            <tr>
                <td colspan="2">
                    <html:errors property="mail"/>
                </td>
            </tr>
        </logic:messagesPresent>      
        <tr>
            <td>
                <label for="phone">
                    <bean:message key="updateProfile.phone"/> :
                </label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="phone" />
            </td>
        </tr>
        <logic:messagesPresent property="phone">
            <tr>
                <td colspan="2">
                    <html:errors property="phone"/>
                </td>
            </tr>
        </logic:messagesPresent>
        <tr>        	
            <td colspan="2">
                <html:submit styleClass="button">
                    <bean:message key="updateProfile.validate"/>
                </html:submit>
            </td>
        </tr>
    </table>
</html:form>

<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript">
    $(function() {
        $.datepicker.setDefaults($.extend(
        {
            yearRange: '-100:+1',
            changeYear: true,
            maxDate: 0,
            dateFormat: 'dd/mm/yy',
            showOn: 'button',
            buttonImage: 'images/calendar.gif',
            buttonImageOnly: true,
            showMonthAfterYear: false
        }));
        $("#dateOfBirth").datepicker($.datepicker.regional['fr']);
    });
</script> 

<h3>
    <bean:message key="updateProfile.changePassword.title"/>
</h3>

<html:form action="/ChangePassword">
	<c:if test="${PasswordChange != null}">
		<p>
			<bean:message key="updateProfile.passwd.change"/>
		</p>
	</c:if>
    <table align="center">
        <c:forTokens var="typePwd" items="oldPassword:newPassword:confirmNewPassword" delims=":">
            <tr>
                <td>
                    <label for="${typePwd}">
                        <bean:message key="updateProfile.changePassword.${typePwd}"/>
                    </label>
                </td>
                <td>
                    <html:password property="${typePwd}" styleId="${typePwd}"/>
                </td>
            </tr>
            <logic:messagesPresent property="${typePwd}">
                <tr>
                    <td colspan="2">
                        <html:errors property="${typePwd}"/>
                    </td>
                </tr>
            </logic:messagesPresent>
        </c:forTokens>
        <tr>
            <td colspan="2" align="right">
                <html:submit styleClass="button">
                    <bean:message key="updateProfile.validate"/>
                </html:submit>
            </td>
        </tr>
    </table>
</html:form>

<h3>
    <bean:message key="updateProfile.changePhoto.title"/>
</h3>
<html:form action="/ChangePhoto" enctype="multipart/form-data" style="float:left">
	<div>
		<html:file property="photo"></html:file>
		<html:submit styleClass="button"/>
	</div>	
	<logic:messagesPresent property="photo">
		<div class="error">
			<html:errors property="photo"/>
		</div>
	</logic:messagesPresent>
</html:form>

<img src="avatar/${userId}.png" style="float:right;"/>
<div class="clear"></div>
<html:link action="/DeletePhoto" style="float:right;">
	<bean:message key="updateProfile.deletePhoto"/>
</html:link>
