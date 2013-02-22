<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<script type="text/javascript" src="js/support.js"></script>

<!-- Page styles -->
<link type='text/css' href='css/support.css' rel='stylesheet' />

<div class='support-top'></div>
<div class='support-content'>
	<h3 class='support-title'>
		<s:text name="support.title.title" />
	</h3>
	<br />
	<div class='support-loading' style='display: none'></div>
	<div class='support-message' style='display: none'></div>
	<s:form action="/ContacterSupport">
		<table id="ContacterSupport">
			<tr>
				<td><label for="supportTitle"> <s:text
							name="support.title.subject" /> :
				</label></td>
				<td><s:textfield property="supportTitle" styleId="supportTitle"
						errorStyleClass="error" onkeypress="refuserToucheEntree(event);"
						style="width: 300px;" /> </td>

			</tr>
			<tr>
				<td><label for="supportContent"> <s:text
							name="support.title.message" /> :
				</label></td>
				<td><s:textarea property="supportContent"
						styleId="supportContent" errorStyleClass="error"
						styleClass="mceTextArea" style="width: 100%;" />
					</td>
			</tr>
			<tr>
				<td></td>
				<td><s:submit styleClass="support-cancel"
						style="background:#52A9BA; border:0; color:#fff; cursor:pointer; font-size:16px; font-weight:bold; height:26px; margin:4px 0 0 4px; text-align:center; vertical-align:middle; -webkit-border-radius:8px; -moz-border-radius:8px; border-radius:8px;float:right">

						<s:text name="support.button.cancel" />
					</s:submit> <s:submit onclick="return validate()" styleClass="support-send"
						style="background:#52A9BA; border:0; color:#fff; cursor:pointer; font-size:16px; font-weight:bold; height:26px; margin:4px 0 0 4px; text-align:center; vertical-align:middle; -webkit-border-radius:8px; -moz-border-radius:8px; border-radius:8px;float:right">
						<s:text name="support.button.send" />
					</s:submit></td>
			</tr>
		</table>
	</s:form>
</div>

<div class='support-bottom'></div>