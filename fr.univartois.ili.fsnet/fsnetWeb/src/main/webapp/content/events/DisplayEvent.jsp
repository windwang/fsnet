
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table id="DisplayEvent">
    <tr>
        <th>
            <h3>${event.nom}</h3>
        </th>
    </tr>
    <tr class="authorDate">
        <td>
            Created by
            <html:link action="/DisplayProfile">
                <html:param name="id" value="${event.createur.id}"/>
                ${event.createur.prenom} ${event.createur.nom} 
            </html:link>, date : 
            <bean:write name="event" property="dateManifestation" format="dd/MM/yyyy" />
        </td>
    </tr>
    <tr>
        <td>
            ${event.contenu}
        </td>
    </tr>
    <tr>
        <td  class="alignRight">
        	<c:if test="${user.id eq event.createur.id}">
                <html:link  action="/DeleteEvent" styleClass="button">
                	<html:param name="eventId" value="${event.id}"/>
                	delete
                </html:link>
			</c:if>
        </td>
    </tr>
</table>