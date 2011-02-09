<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>



<h3><bean:message key="members.createMultiple" /></h3>

<html:form action="/CreateMultipleMember">
	<p><bean:message key="members.createMultipleIndications" /> <bean:message key="members.createMultipleFormat" /></p>
	<p class="errorMessage"><html:errors property="multipleMember" /></p>
	<html:textarea property="multipleMember" styleId="multipleMember" errorStyleClass="error" cols="80" rows="6"/>
	<p><bean:message key="members.message" /> :</p>
	<p><html:textarea property="message" styleId="message" errorStyleClass="error" rows="5" cols="50"/></p>
	<p class="errorMessage"><html:errors property="message" /></p>
	<html:submit styleClass="button">
		<bean:message key="members.validate" />
	</html:submit>
</html:form>
