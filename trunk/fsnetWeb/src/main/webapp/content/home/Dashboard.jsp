<%-- 
    Document   : dashboard
    Created on : 23 janv. 2010, 13:55:48
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<table id="dashboardMessages" class="inLineTableDashBoard homeFrame">
    <caption><bean:message key="DashBoard.lastMes"/></caption>
    <logic:empty name="messages">
        <tr>
            <td>
            <bean:message key="DashBoard.noMes"/>.
            </td>
        </tr>
    </logic:empty>
    <logic:notEmpty name="messages">
        <c:forEach items="${requestScope.messages}" var="message" begin="0" end="2">
            <c:if test="${not message.reed}">
                <tr class="notReed">
                    <td class="messagePhoto">
                        <img src="miniature/${message.from.id}.png" />
                    </td>
                    <td style="width: 0%">
                        <html:link action="/DisplayMessage">
                            <html:param name="messageId" value="${message.id}" />
                            <span>
                                <ili:substring beginIndex="0" endIndex="20"><ili:noxml>${message.subject}</ili:noxml></ili:substring> :
                            </span>
                            <span style="color: gray">
                                <ili:substring beginIndex="0" endIndex="20"><ili:noxml>${message.body}</ili:noxml></ili:substring>
                            </span>
                        </html:link>
                    </td>
                </tr>
            </c:if>
            <c:if test="${message.reed}">
                <tr>
                    <td class="messagePhoto">
                        <html:link action="/DisplayProfile">
                            <html:param name="id" value="${message.from.id}"/>
                            <img src="miniature/${message.from.id}"/>
                        </html:link>
                    </td>
                    <td>
                        <html:link action="/DisplayMessage">
                            <html:param name="messageId" value="${message.id}" />
                            <span>
                                <ili:substring beginIndex="0" endIndex="20"><ili:noxml>${message.subject}</ili:noxml></ili:substring> :
                            </span>
                            <span style="color: gray">
                                <ili:substring beginIndex="0" endIndex="20"><ili:noxml>${message.body}</ili:noxml></ili:substring>
                            </span>
                        </html:link>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </logic:notEmpty>
</table>
<table id="lastVisits" class="inLineTableDashBoard homeFrame">
    <caption><bean:message key="visite.last.title"/></caption>
    <logic:empty name="visitors">
        <tr>
            <td>
                <bean:message key="DashBoard.noVis"/>.
            </td>
        </tr>
    </logic:empty>
    <logic:notEmpty name="visitors">
        <c:forEach var="pv" items="${visitors}">
            <tr>
                <td class="messagePhoto">
                    <html:link action="/DisplayProfile">
                        <html:param name="id" value="${pv.visitor.id}"/>
                        <img src="miniature/${pv.visitor.id}.png"/>
                    </html:link>
                </td>
                <td>
                    <ili:getSocialEntityInfos socialEntity="${pv.visitor}"/>
                </td>
                <td>
                    <bean:write name="pv" property="lastVisite" formatKey="date.format" />
                </td>
            </tr>
        </c:forEach>

    </logic:notEmpty>
</table>

<div class="clear homeGap"></div>

<table id="contactProposals" class="inLineTableDashBoard homeFrame">
    <caption><bean:message key="DashBoard.contactProposals.title"/></caption>
    <logic:empty name="contacts">
        <tr>
            <td>
                <bean:message key="DashBoard.noContacts"/>.
            </td>
        </tr>
    </logic:empty>
    <logic:notEmpty name="contacts">
        <c:forEach var="contact" items="${contacts}">
            <tr>
                <td class="messagePhoto">
                    <html:link action="/DisplayProfile">
                        <html:param name="id" value="${contact.id}"/>
                        <img src="miniature/${contact.id}.png"/>
                    </html:link>
                </td>
                <td>
					<ili:getSocialEntityInfos socialEntity="${contact}"/>
                </td>
                <td class="tableButton"> 
                    <html:link action="/ContactDemand" styleClass="button">
                   	 	<html:param name="entitySelected" value="${contact.id}"/>
                    	<bean:message key="DashBoard.askContact"/>
                	</html:link>
                </td>
            </tr>
        </c:forEach>

    </logic:notEmpty>
</table>

<table id="interestProposals" class="inLineTableDashBoard homeFrame">
    <caption><bean:message key="DashBoard.interestProposals.title"/></caption>
    <logic:empty name="interests">
        <tr>
            <td>
                <bean:message key="DashBoard.noInterest"/>.
            </td>
        </tr>
    </logic:empty>
    <logic:notEmpty name="interests">
        <c:forEach var="interest" items="${interests}">
            <tr>
                <td>
                    <html:link action="/InterestInformations">
                        <html:param name="infoInterestId" value="${interest.id}"/>
                        ${interest.name}
                    </html:link>
                </td>
               <td class="tableButton"> 
                 	<html:link action="/AddInterest">
	                     <img src="images/add.png"/>
	                     <html:param name="addedInterestId" value="${interest.id}"/>
	                </html:link>    
                </td>
            </tr>
        </c:forEach>

    </logic:notEmpty>
</table>

<div class="clear homeGap"></div>

<table id="lastInteractions" class="inLineTableDashBoard homeFrame">
    <caption><bean:message key="lastInteractions.title"/></caption>
    <logic:empty name="lastInteractions">
        <tr>
            <td>
                <bean:message key="DashBoard.noNewInt"/>.
            </td>
        </tr>
    </logic:empty>

    <logic:notEmpty name="lastInteractions">
        <logic:iterate id="triple" collection="${requestScope.lastInteractions}">
            <tr>
                <td class="messagePhoto">
                    <html:link action="/DisplayProfile">
                        <html:param name="id" value="${triple.interaction.creator.id}"/>
                        <img src="miniature/${triple.interaction.creator.id}.png"/>
                    </html:link>
                </td>
                <td>
                    <bean:message key="events.16"/>
                    <ili:getSocialEntityInfos socialEntity="${triple.interaction.creator}"/>
                </td>
                <td>
                    <html:link action="${triple.path}">
                        <html:param name="${triple.id}" value="${triple.interaction.id}"/>
                        ${triple.interaction.title}
                    </html:link>
                </td>
                <td class="tableButton">
                    <bean:define id="interkey" name="triple" property="interaction"/>
                    <bean:write name="interkey" property="lastModified" format="dd/MM/yyyy" />
                </td>
            </tr>
        </logic:iterate>
    </logic:notEmpty>

</table>
