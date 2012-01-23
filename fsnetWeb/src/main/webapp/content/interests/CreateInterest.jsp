<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3><bean:message key="interests.3"/></h3>
<html:javascript formName="/CreateInterest"/>
<html:form action="/CreateInterest">
	<table id="CreateInterest">
		<tr>
			<bean:message key="interests.18"/>
		</tr>
		<tr>
			<td>
				<bean:message key="interests.15"/>
			</td>
			<td>
				<html:select property="parentInterestId" styleClass="select">
					<html:option value=""><bean:message key="interests.8"/></html:option>
					<c:forEach var="interest" items="${requestScope.allInterests}">
						<html:option value="${interest.id}">${interest.name}</html:option>
					</c:forEach>
				</html:select>
			</td>
	    	<td>
	    		<html:text property="createdInterestName" styleId="createdInterestName"/>
	    	</td>
	    	<td>	    
			    <html:submit styleClass="button">
			    	<bean:message key="interests.create"/>
			    </html:submit>
			</td>
	    </tr>
	    <tr class="errorMessage">
	    	<td></td>
	    	<td colspan="2"><html:errors property="createdInterestName"/></td>
	    </tr>
    </table>
</html:form>