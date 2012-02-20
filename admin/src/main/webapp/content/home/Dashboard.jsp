<%-- 
    Document   : dashboard
    Created on : 23 janv. 2010, 13:55:48
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<fieldset class="fieldsetAdmin">
  <legend class="legendAdmin"><bean:message key="pageTitle.0" /></legend>
  <table class="fieldsetTableAdmin">
    <tr><td>
      <bean:message key="welcome.welcome" />  <br/><br/>
	  <bean:message key="welcome.message1" />  <br/><br/>
	  <bean:message key="welcome.message2" />  
    </td></tr>
  </table>
</fieldset>