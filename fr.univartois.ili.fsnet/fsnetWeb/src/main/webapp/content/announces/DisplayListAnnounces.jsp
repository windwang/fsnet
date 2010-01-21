<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<logic:present name="listAnnounces">
	<table id="listAnnounces">
		<tr>
			<td>Titre</td>
			<td>Date d'expiration</td>
		</tr>
		<logic:iterate id="announce" scope="request" name="listAnnounces">
			<tr>
			<bean:define id="idAnnounce" name="announce" property="id" />
				<td><html:link action="/DisplayAnnounce.do" paramId="idAnnounce"
					paramName="idAnnounce">
					<bean:write name="announce" property="nom" />
				</html:link></td>
				<td><bean:write name="announce" property="dateFinAnnonce" /></td>
			</tr>
		</logic:iterate>
	</table>
</logic:present>