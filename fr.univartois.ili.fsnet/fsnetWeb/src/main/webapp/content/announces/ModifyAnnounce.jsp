<%-- 
    Document   : CreateAnnounce
    Created on : 18 janv. 2010, 18:06:12
    Author     : Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"> </script>


<h3><bean:message key="announce.modifyAnnounce"/> </h3>
<html:form action="/ModifyAnnounce">

    <html:hidden property="idAnnounce" value="${announce.id}" />
    <table id="CreateAnnounce">
        <tr>
            <td><label for="announceTitle"><bean:message key="announce.title" /></label></td>
            <td><html:text property="announceTitle" value="${announce.title}"
                       styleId="announceTitle" /></td>
        </tr>
        <tr>
            <td><label for="announceContent"><bean:message key="announce.content" /> </label></td>
            <td><html:textarea cols="40" rows="8"
                           value="${announce.content}" property="announceContent"
                           styleId="announceContent" styleClass="mceTextArea"
                           style="width: 100%;" /></td>
        </tr>
        <tr>
            <td><label for="announceExpiryDate">Date :</label></td>
            <td><c:set var="formattedDate">
                    <bean:write name="announce" property="endDate"
                                format="dd/MM/yyyy" />
                </c:set>
                <html:text property="announceExpiryDate" styleId="announceExpiryDate" value="${formattedDate}"/>
                <html:submit styleClass="button"><bean:message key="announce.modifyAnnounce" /></html:submit></td>
        </tr>
        <tr>
            <td colspan="2"><html:messages id="message" /> <html:errors />
            </td>
        </tr>
    </table>
</html:form>
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript">
    $(function() {
        $.datepicker.setDefaults($.extend( {
            minDate : 0,
            dateFormat : 'dd/mm/yy',
            showOn : 'button',
            buttonImage : 'images/calendar.gif',
            buttonImageOnly : true,
            showMonthAfterYear : false
        }));
        $("#announceExpiryDate").datepicker($.datepicker.regional['fr']);
    });
</script>
