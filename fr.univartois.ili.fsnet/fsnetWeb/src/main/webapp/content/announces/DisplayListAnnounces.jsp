<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<logic:present name="listAnnounces">
	<table id="EventList">

		<logic:iterate id="announce" scope="request" name="listAnnounces">
			<tr class="header">
				<bean:define id="idAnnounce" name="announce" property="id" />
				<th colspan="2"><html:link action="/DisplayAnnounce.do"
					paramId="idAnnounce" paramName="idAnnounce">
					<bean:write name="announce" property="nom" />
				</html:link></th>
			</tr>
			<tr class="content">
				<td class="left"><bean:write name="announce"
					property="dateFinAnnonce" /></td>
				<td class="right"><bean:write name="announce"
					property="contenu" /></td>
			</tr>
		</logic:iterate>
	</table>
</logic:present>