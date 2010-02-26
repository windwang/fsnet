<%-- 
    Document   : InteractionInfo
    Created on : 15 févr. 2010, 14:27:55
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div class="interactionInfo" >
    Owner :
    <html:link action="/DisplayProfile">
        <html:param name="id" value="${theInteraction.creator.id}"/>
        ${theInteraction.creator.firstName} ${theInteraction.creator.name}
    </html:link>
    <br/>
    <div style="color: #aaaaaa">


        X Subscribers
        <br/>
        Created on <bean:write name="theInteraction" property="creationDate" format="dd/MM/yyyy" />
        <br/>
        X Followers
        <br/>
        Visibility : public
        <br/>

        <logic:notEmpty name="theInteraction" property="interests" >
            Interets :
            <div class="cloud">
                <c:forEach var="interest" items="${theInteraction.interests}">
                    <span class="otag">
                        <html:link action="/InterestInformations">
                            <html:param name="infoInterestId" value="${interest.id}"/>
                            ${interest.name}
                        </html:link>
                    </span>
                </c:forEach>
            </div>
        </logic:notEmpty>
    </div>
</div>