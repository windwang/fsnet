<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<h3><bean:message key="consultation.create"/></h3>
<html:form action="CreateConsultation" method="POST">
	<table>
	<tr>
		<td><label for="consultationTitle"><bean:message key="consultation.title" /> : </label></td>
		<td>
			<html:text property="consultationTitle" styleId="consultationTitle" />
			<logic:messagesPresent property="consultationTitle">
                <span class="errorMessage">
                    <html:errors property="consultationTitle"/>
                </span>
            </logic:messagesPresent></td>
		
	</tr>
	<tr>
		<td><label for="consultationDescription"><bean:message key="consultation.description" /> : </label></td>
		<td><html:text property="consultationDescription" styleId="consultationDescription" /></td>
	</tr>
	<tr>
		<td><label for="radioButtonText"><bean:message key="consultation.textAlternative"></bean:message></label> <input type="radio"  class="alternativeRadio" name="alternativeRadio" id="radioButtonText" checked="checked"/></td><td><label for="radioButtonDate"><bean:message key="consultation.dateAlternative"></bean:message></label><input type="radio" class="alternativeRadio" name="alternativeRadio" id="radioButtonDate" /></td>
	</tr>
	</table>
	<c:if test="${errorChoice }"><p><bean:message key="consultation.errorChoice"/></p></c:if>
	<table id="choicesTab">
	<c:forEach begin="1" end="3" var="i">
		<tr>
		<td><label for="consultationChoice${i}"><span class="i18nChoice"><bean:message key="consultation.choice" /></span> ${i} : </label></td>
		<td><html:text property="consultationChoice" styleClass="consultationChoice" styleId="consultationChoice${i}"  value="" /></td>
	</tr>
	</c:forEach>
    </table>
    <input type="button" value=" - " onclick="removeChoice()"/><input type="button" value=" + " onclick="addChoice()"/><br />
    <html:submit styleClass="button"><bean:message key="consultation.create"/></html:submit>
    
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
	    
	    $('.alternativeRadio').click( function(e) {
			displayChoices(false);	
		});

	     
	});
	var i = 4;
	function addChoice(){
		i++;
		displayChoices(true);
	}
	function removeChoice(){
		if(i>2){
			i--;
		}
		displayChoices(true);
	}
	
	function displayChoices(displayValue){
		var res='';
		for(j=1;j<i;j++){
			val=$("#consultationChoice"+j).val();
			if(val == undefined)
				val = "";
			res+='<tr><td><label for="consultationChoice'+j+'"> <span class="i18nChoice">'+$(".i18nChoice").html()+ '</span> ' +j+' : </label></td><td><input type="text" name="consultationChoice" class="consultationChoice" value="'+(displayValue?val:"")+'" id="consultationChoice'+j+'" />';
			res+='</td></tr>';
		}
		$("#choicesTab").html(res);
		if($("#radioButtonDate").attr('checked')){
			$(".consultationChoice").datepicker($.datepicker.regional['fr']);
		}
	}
</script>
