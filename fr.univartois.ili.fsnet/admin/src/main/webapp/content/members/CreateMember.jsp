<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>



<h3>
  Create a member
</h3>

<html:form action="/CreateMember">
    <table id="CreateMember">
        <tr>
            <td>
                <label for="name">
                   Name :
                </label>
            </td>
            <td>
                <html:text property="name" styleId="name" errorStyleClass="error"/>
            </td>
        </tr>
       <tr class="errorMessage">
            <td>
                <html:errors property="name"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="firstName">
                   First name :
                </label>
            </td>
            <td>
                <html:text property="firstName" styleId="firstName" errorStyleClass="error"/>
            </td>
        </tr>
          <tr class="errorMessage">
            <td>
                <html:errors property="firstName"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="mail">
                    Email :
                </label>
            </td>
            <td>
                <html:text property="mail" styleId="mail" errorStyleClass="error"/>
            </td>
        </tr>
        <tr class="errorMessage">
            <td>
                <html:errors property="mail"/>
            </td>
        </tr>
        <tr>        	
            <td colspan="2">
                <html:submit styleClass="button">
                    Validate
                </html:submit>
            </td>
        </tr>
    </table>
</html:form>

