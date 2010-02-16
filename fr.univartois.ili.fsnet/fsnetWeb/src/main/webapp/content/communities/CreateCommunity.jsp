<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<h3>
  <bean:message key="communities.create"/>
</h3>

<html:form action="/CreateCommunity">
    <table id="CreateCommunity">
        <tr>
            <td>
                <label for="name">
                    <bean:message key="communities.name"/> :
                </label>
            </td>
            <td>
                <html:text property="name" styleId="name" errorStyleClass="error"/>
                <c:import url="/InterestCheckBoxes.do"/>
            </td>
        </tr>
       <tr class="errorMessage">
            <td>
                <html:errors property="name"/>
            </td>
        </tr>
        <tr>        	
            <td colspan="2">
                <html:submit styleClass="button">
                   <bean:message key="communities.validate"/>
                </html:submit>
            </td>
        </tr>
    </table>
</html:form>

