<%-- 
    Document   : Members
    Author     : Audrey Ruellan and Cerelia Besnainou
    modified by : Alexandre Thery
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>


<label for="simpleMember"> 
	<bean:message key="members.simpleMember" />  
</label>
<input type="radio" name="chooseSimpleOrMultiple" id="simpleMember" onchange="changeSimpleOrMultiple()"/>
<label for="multipleMember"> 
	<bean:message key="members.multipleMember" />  
</label>

<input type="radio" name="chooseSimpleOrMultiple" id="multipleMember" onchange="changeSimpleOrMultiple()"/>
	<html:submit styleClass="button">
		<bean:message key="members.validate" />
	</html:submit>

<jsp:include page="/content/members/CreateMember.jsp"/>
<jsp:include page="/content/members/CreateMultipleMember.jsp"/>

<script>
	function changeSimpleOrMultiple()
	{
		alert("submit (doesn't work,), i am working on it");
	}
</script>