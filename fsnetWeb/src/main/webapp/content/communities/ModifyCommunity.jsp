<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<c:if test="${not empty requestScope.myCommunities}">
	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="commmunities.title.modify" />
		</legend>

		<table id="ModifyCommunity" class="inLineTable tableStyle">
			<s:form action="/ModifyCommunity">
				<tr>
					<td>
						<label for="oldCommunityName"><s:text name="communities.form.oldName" /></label></td>
					<td>
						<s:select property="oldCommunityName" styleClass="select" styleId="oldCommunityName"
							list="%{community}" listKey="%{community.id}" listValue="%{community.title}">
						</s:select>
					</td>
				</tr>
				<tr>
					<td>
						<label for="newCommunityName"><s:text name="communities.form.newName" /></label>
					</td>
					<td>
						<s:textfield property="newCommunityName" styleId="newCommunityName" />
					</td>
				</tr>

				<tr>
					<td colspan="2" class="tableButton">
						<s:submit styleClass="btn btn-inverse">
							<s:text name="communities.button.modify" />
						</s:submit>
					</td>
				</tr>
			</s:form>
		</table>
	</fieldset>
</c:if>