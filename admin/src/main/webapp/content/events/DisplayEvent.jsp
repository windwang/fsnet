<%--
	author : Bouragba Mohamed
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html"     prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"     prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic"    prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3>
    ${event.title}
</h3>

<div class="interactionDisplay">
    <table style="width: 100%">
        <tr class="authorDate">
            <td>
                <bean:message key="events.5"/>
                <html:link action="/DisplayMember">
	                    	<html:param name="idMember" value="${event.creator.id}"/>
	                    	${event.creator.firstName} ${event.creator.name}
	              </html:link>,
                <bean:message key="events.willoccur"/>
                <bean:write name="event" property="startDate" format="dd/MM/yyyy" />
                <bean:message key="events.to"/>
                <bean:write name="event" property="endDate" format="dd/MM/yyyy" />
                
                <c:if test="${not empty event.address.address or not empty event.address.city}">
                	<bean:message key="events.in"/>
                	${event.address.address} ${event.address.city}
                </c:if>
                
                <c:if test="${subscriber}">,&nbsp;&nbsp;"<bean:message key="events.19"/>"</c:if>
            </td>
        </tr>
        <tr>
            <td>
                ${event.content}
            </td>
        </tr>
        <tr>
            <td  class="alignRight">
              <html:link  action="/DeleteEvent" styleClass="button">
              	<html:param name="eventId" value="${event.id}"/>
                <bean:message key="events.7"/>
              </html:link>
            </td>
        </tr>
    </table>
</div>
<div class="clear"></div>


<c:if test="${fn:length(subscribers) gt 0}">
<h3>
	<bean:message key="events.22"/> : 
</h3>
<logic:iterate id="subscriber" collection="${subscribers}"> 
	<span class="tagSE"> 
		<ili:getMiniature socialEntity="${subscriber}"/>
	    <ili:getSocialEntityInfos socialEntity="${subscriber}"/>
	</span> 
</logic:iterate>
</c:if>
