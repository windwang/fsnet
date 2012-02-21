<%-- 
    Document   : InteractionInfo
    Created on : 15 févr. 2010, 14:27:55
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<table  class="inLineTableDashBoardFieldset fieldsetTable"><tr><td>
    <bean:message key="interactions.owner"/> :
    <ili:getSocialEntityInfos socialEntity="${theInteraction.creator}"/>
    <br/>
    <div style="color: #808080">
		<c:if test="${not empty subscribers}">		
        	${fn:length(subscribers)} <bean:message key="interactions.subscribers"/>
        	<br/>
        </c:if>
        <bean:message key="interactions.created"/> 
        <bean:write name="theInteraction" property="creationDate" format="dd/MM/yyyy" />
        <br/>
        ${fn:length(theInteraction.followingEntitys)} <bean:message key="interactions.followers"/>
        <br/>
        <bean:message key="interactions.visibility"/>
        <br/>

        <logic:notEmpty name="theInteraction" property="interests" >
            <bean:message key="interactions.interest"/> :
            <div class="cloud">
                <c:forEach var="interest" items="${theInteraction.interests}">
                    <span class="otag">
                        <html:link action="/InterestInformations">
                            <html:param name="infoInterestId" value="${interest.id}"/>
                            ${interest.name}
                        </html:link>
                        <c:if test="${sessionScope.userId eq theInteraction.creator.id}">
	                        <html:link action="/RemoveInterestOfInteraction">
								<html:param name="interactionId" value="${theInteraction.id}" />
								<html:param name="interestId" value="${interest.id}" />
								<img src="images/mini-delete.png" alt="delete"/>
							</html:link>
						</c:if>
                    </span>
                </c:forEach>
            </div>
        </logic:notEmpty>
    </div>
  </td></tr>
</table>
