<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<script type="text/javascript" src="js/support.js"></script>

<!-- Page styles -->
<link type='text/css' href='css/support.css' rel='stylesheet' />

<div class='support-top'></div>
<div class='support-content'>
	<h3 class='support-title'><bean:message key="support.0"/></h3>
	<br/>
	<div class='support-loading' style='display:none'></div>
	<div class='support-message' style='display:none'></div>
	<html:form action="/ContacterSupport">
		<table id="ContacterSupport">
	        <tr>
	            <td>
	                <label for="supportTitle">
	                    <bean:message key="support.1"/> :
	                </label>
	            </td>
	            <td>
	             <html:text  property="supportTitle"
	                            styleId="supportTitle"
	                            errorStyleClass="error"
	                            onkeypress="refuserToucheEntree(event);"
	                            style="width: 300px;"/>
	                <logic:messagesPresent property="supportTitle">
	                    <div class="errorMessage">
	                        <html:errors property="supportTitle"/>
	                    </div>
	                </logic:messagesPresent>
	                
	            </td>
	           
	        </tr>
	        <tr>
            <td>
                <label for="supportContent" >
                    <bean:message key="support.2"/> :
                </label>
            </td>
            <td>
                <html:textarea  property="supportContent"
                				styleId="supportContent"
                                errorStyleClass="error"
                                styleClass="mceTextArea"
                                style="width: 100%;"
                                />
                <logic:messagesPresent property="supportContent">
                    <div class="errorMessage">
                        <html:errors property="supportContent"/>
                    </div>
                </logic:messagesPresent>
            </td>
        </tr>
         <tr>
	           		<td></td><td><html:submit 
				styleClass="support-cancel"
				style="background:#52A9BA; border:0; color:#fff; cursor:pointer; font-size:16px; font-weight:bold; height:26px; margin:4px 0 0 4px; text-align:center; vertical-align:middle; -webkit-border-radius:8px; -moz-border-radius:8px; border-radius:8px;float:right" >
				
				<bean:message key="support.AnnulerSupport" />
			</html:submit>
	           		<html:submit
	           	onclick="return validate()"
				styleClass="support-send" 
				style="background:#52A9BA; border:0; color:#fff; cursor:pointer; font-size:16px; font-weight:bold; height:26px; margin:4px 0 0 4px; text-align:center; vertical-align:middle; -webkit-border-radius:8px; -moz-border-radius:8px; border-radius:8px;float:right">
				<bean:message key="support.ContacterSupport" />
				</html:submit></td>
	           </tr>
	    </table>
	</html:form>
</div>

<div class='support-bottom'></div>