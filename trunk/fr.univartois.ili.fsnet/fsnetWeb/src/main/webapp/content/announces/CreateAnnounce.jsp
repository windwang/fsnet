<%-- 
    Document   : CreateAnnounce
    Created on : 18 janv. 2010, 18:06:12
    Author     : Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
--%>

<%@page contentType="text/html;charset=ISO-8859-1" language="java" %>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"/>


<html:javascript formName="/CreateAnnounce"/>
<h3>Create Announce </h3>
<html:form action="/CreateAnnounce">
    <table id="CreateAnnounce">
        <tr>
            <td>
                <label for="announceTitle">Titre :</label>
            </td>
            <td>
                <html:text property="announceTitle" styleId="announceTitle" />
            </td>
        </tr>
        <tr>
            <td>
                <label for="announceContent">Contenu: </label>
            </td>
            <td>
                <html:textarea cols="40" rows="8" property="announceContent" styleId="announceContent"  styleClass="mceTextArea" style="width: 100%;"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="announceExpiryDate">Date :</label>
            </td>
            <td>
                <html:text property="announceExpiryDate" styleId="announceExpiryDate" disabled="false"/>
                <html:submit styleClass="button">Create Announce</html:submit>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <html:messages id="message" />
                <html:errors/>
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
            minDate: 0,
            dateFormat: 'dd/mm/yy',
            showOn: 'button',
            buttonImage: 'images/calendar.gif',
            buttonImageOnly: true,
            showMonthAfterYear: false
        }));
        $("#announceExpiryDate").datepicker($.datepicker.regional['fr']);
    });
</script> 
