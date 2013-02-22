<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<fieldset class="fieldsetCadre">
	<legend >
		<s:text name="cv.title.generate" />
	</legend>

	<table id="eventsTable"
		class="tablesorter inLineTable tableStyle">
		<thead>
			<tr>
				<th ><s:text name="tableheader.cvname" /></th>
				<th >Générer</th>
				<th >Supprimer</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="cv" items="${requestScope.CVsList}">
				<tr>
					<td>${cv.titleCv }</td>
					<td><s:form action="/GenerateCv">
							<input type="hidden" name="idCv" value="${cv.id }" />
							<s:submit type="button" styleClass="btn btn-inverse">
								<s:text name="cv.button.generate" />
							</s:submit>
						</s:form></td>
					<td><s:form action="/DeleteCv">
							<input type="hidden" name="idCv" value="${cv.id }" />
							<s:submit type="button" styleClass="btn btn-inverse">
								<s:text name="cv.button.delete" />
							</s:submit>
						</s:form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</fieldset>
