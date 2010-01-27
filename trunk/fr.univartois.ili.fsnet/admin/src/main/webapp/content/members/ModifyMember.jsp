<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>


<html:javascript formName="/ModifyMember"/>

<h3>
Modify a member
</h3>

<html:form action="/ModifyMember">
    <table id="ModifyMember">
    <tr   >
            <td COLSPAN="2">
                <html:errors /> 
            </td>
           
        </tr>
        <tr>
            <td>
                <label for="name">
                   Name :
                </label>
                <html:hidden property="id"/>
            </td>
            <td>
                <html:text property="name" styleId="name" errorStyleClass="error"/>
            </td>
        </tr>
       
        <tr>
            <td>
                <label for="firstName">
                   First name :
                </label>
            </td>
            <td>
                <html:text property="firstName"  styleId="firstName" errorStyleClass="error"/>
            </td>
        </tr>

        <tr>
            <td>
                <label for="email">
                    Email :
                </label>
            </td>
            <td>
                <html:text property="email" styleId="email" errorStyleClass="error"/>
            </td>
        </tr>
       
        <tr>        	
            <td colspan="2">
                <html:submit styleClass="button">
                    update
                </html:submit>
            </td>
        </tr>
    </table>
</html:form>

