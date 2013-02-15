<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<fieldset class="fieldsetCadre">
	<legend >
		<bean:message key="cv.title.generate" />
	</legend>

	<table id="eventsTable"
		class="tablesorter inLineTable tableStyle">
		<thead>
			<tr>
				<th ><bean:message key="tableheader.cvname" /></th>
				<th >Générer</th>
				<th >Supprimer</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="cv" items="${requestScope.CVsList}">
				<tr>
					<td>${cv.titleCv }</td>
					<td><html:form action="/GenerateCv">
							<input type="hidden" name="idCv" value="${cv.id }" />
							<html:submit styleClass="btn btn-inverse">
								<bean:message key="cv.button.generate" />
							</html:submit>
						</html:form></td>
					<td><html:form action="/DeleteCv">
							<input type="hidden" name="idCv" value="${cv.id }" />
							<html:submit styleClass="btn btn-inverse">
								<bean:message key="cv.button.delete" />
							</html:submit>
						</html:form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</fieldset>
