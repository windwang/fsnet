<%-- 
    Author     : Ayoub AICH  
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

            <h3>
			<bean:message key="Cv.0"/>
			</h3>
			
			<table id="eventsTable" class="tablesorter inLineTable">
			<thead>
				<tr>
					<th></th>
					<th><bean:message key="tableheader.cvname" /></th>
					<th><bean:message key="tableheader.cvaction" /></th>
				</tr>
			</thead>
			<tbody>
			
			<c:forEach var="cv" items="${requestScope.CVsList}">
			
					<tr><td></td><td> ${cv.titleCv }</td>
					
					   <td>
					  
					  
					  <html:form action="/Generate">
					  <input type="hidden" name="idCv" value="${cv.id }"/>  
					      <html:submit styleClass="button" >
					        <bean:message key="BoutonCv.1" />
					       </html:submit> 
					    </html:form>   
					   </td>
					</tr>
					
		
			</c:forEach>
		
			</tbody>
			</table>