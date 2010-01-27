<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<html:form action="/SaveMailConfiguration">
<table>
	<tr>
		<td>
			<label for="SMTPHost">
				<bean:message key="configure.0"/>
			</label>
		</td>
		<td>
			<html:text styleId="SMTPHost" property="SMTPHost"/>
		</td>
	</tr>
	<tr>
		<td>
			<label for="SMTPPort">
				<bean:message key="configure.1"/>
			</label>
		</td>
		<td>
			<html:text styleId="SMTPPort" property="SMTPPort"/>
		</td>	
	</tr>
	<tr>
		<td>
			<label for="enableAuthentication">
				<bean:message key="configure.2"/>
			</label>
		</td>
		<td>
			<html:checkbox styleId="enableAuthentication" property="enableAuthentication"></html:checkbox>
		</td>
	</tr>
	<tr>
		<td>
			<label for="SMTPUsername">
				<bean:message key="configure.3"/>
			</label>
		</td>
		<td>
			<html:text styleId="SMTPUsername" property="SMTPUsername"/>
		</td>
	</tr>
	<tr>
		<td>
			<label for="SMTPPassword">
				<bean:message key="configure.4"/>
			</label>
		</td>
		<td>
 			<html:text styleId="SMTPPassword" property="SMTPPassword"/> 
		</td>
	</tr>
	<tr>
		<td>
			<label for="MailFrom">
				<bean:message key="configure.5"/>
			</label>
		</td>
		<td>
			<html:text styleId="MailFrom" property="MailFrom"/>
		</td>
	</tr>
	<tr>
		<td>
			<label for="enableTLS">
				<bean:message key="configure.6"/>
			</label>
		</td>
		<td>
			<html:checkbox styleId="enableTLS" property="enableTLS"/>
		</td>
	</tr>
	<tr>
		<td>
			<label for="enableSSL">
				<bean:message key="configure.7"/>
			</label>
		</td>
		<td>
			<html:checkbox styleId="enableSSL" property="enableSSL"/>
		</td> 
	</tr>
	<tr>
		<td>
			<label for="FSNetWebURL">
				<bean:message key="configure.8"/>
			</label>
		</td>
		<td>
			<html:text styleId="FSNetWebURL" property="FSNetWebURL"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<html:submit>
				<bean:message key="configure.9"/>
			</html:submit>
		</td>
	</tr>
	<html:errors/>
</table>
	
</html:form>
