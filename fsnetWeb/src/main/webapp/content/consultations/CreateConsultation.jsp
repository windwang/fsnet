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
	<c:forEach begin="1" end="3" var="i">
		<tr>
		<td><label for="consultationChoice"><bean:message key="consultation.choice" /> ${i} : </label></td>
		<td><html:text property="consultationChoice" styleId="consultationChoice"  value="" /></td>
	</tr>
	</c:forEach>
    </table>
    <html:submit styleClass="button"><bean:message key="consultation.create"/></html:submit>
    
</html:form>