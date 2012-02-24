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

<fieldset class="fieldsetHome">
  <legend class="legendHome"><html:link action="/Inbox"><bean:message  key="dashBoard.messages.last"/></html:link></legend>
  <table id="dashboardMessages" class="inLineTableDashBoardFieldset fieldsetTable">
    <logic:empty name="messages">
        <tr>
            <td>
                <bean:message key="dashBoard.messages.empty"/>.
            </td>
        </tr>
    </logic:empty>
    <logic:notEmpty name="messages">
        <c:forEach items="${requestScope.messages}" var="message" begin="0" end="2">
            <c:if test="${not message.reed}">
                <tr class="notReed">
                    <td class="messagePhoto">
                        <ili:getMiniature socialEntity="${message.from}"/>
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
                        <ili:getMiniature socialEntity="${message.from}"/>
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
</fieldset>

<fieldset class="fieldsetHome2">
  <legend class="legendHome"><html:link action="/Visits"><bean:message key="visite.last.title"/></html:link></legend>
  <table id="lastVisits" class="inLineTableDashBoardFieldset homeFrame fieldsetTable">
    <logic:empty name="visitors">
        <tr>
            <td>
                <bean:message key="dashBoard.visites.empty"/>.
            </td>
        </tr>
    </logic:empty>
    <logic:notEmpty name="visitors">
        <c:forEach var="pv" items="${visitors}">
            <tr>
                <td class="messagePhoto">
                    <ili:getMiniature socialEntity="${pv.visitor}"/>
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
</fieldset>

<div class="clear homeGap"></div>
<c:choose>
    <c:when test="${sessionScope.numNewContactsRequests gt 0}">
        <fieldset class="fieldsetHome">
          <legend class="legendHome"><bean:message key="dashBoard.contacts.ask"/></legend>  
          <table id="contactsAsked" class="inLineTableDashBoardFieldset homeFrame fieldsetTable">
            <c:forEach var="contact" items="${contactsAsked}">
                <tr class="notReed">
                    <td class="miniatureContainer">
                        <ili:getMiniature socialEntity="${contact}"/>
                    </td>
                    <td>
                        <ili:getSocialEntityInfos socialEntity="${contact}"/>
                    </td>
                    <td class="tableButton">
                        <html:link action="/AcceptContact" styleClass="button">
                            <html:param name="entityAccepted" value="${contact.id}" />
                            <bean:message key="contact.button.accept" />
                        </html:link> <html:link action="/RefuseContact" styleClass="button">
                            <html:param name="entityRefused" value="${contact.id}" />
                            <bean:message key="contact.button.refuse" />
                        </html:link>
                    </td>
                </tr>
            </c:forEach>
        </table>
      </fieldset>
    </c:when>
    <c:otherwise>
        <fieldset class="fieldsetHome">
          <legend class="legendHome"><bean:message key="dashBoard.contacts.proposals"/></legend>
          <table id="contactProposals" class="inLineTableDashBoardFieldset homeFrame fieldsetTable">
            <logic:empty name="contacts">
                <tr>
                    <td>
                        <bean:message key="dashBoard.contacts.empty"/>.
                    </td>
                </tr>
            </logic:empty>
            <logic:notEmpty name="contacts">
                <c:forEach var="contact" items="${contacts}">
                    <tr>
                        <td class="messagePhoto">
                            <ili:getMiniature socialEntity="${contact}"/>
                        </td>
                        <td>
                            <ili:getSocialEntityInfos socialEntity="${contact}"/>
                        </td>
                        <td class="tableButton">
                            <html:link action="/ContactDemand">            
                            	<img src="images/add.png" alt="<bean:message key='dashBoard.contact.button.add.alt'/>" title="<bean:message key='dashBoard.contact.button.add'/> ${contact.firstName} ${contact.name}" />
                                <html:param name="entitySelected" value="${contact.id}"/>
                            </html:link>
                        </td>
                    </tr>
                </c:forEach>
            </logic:notEmpty>
        </table>
       </fieldset>
    </c:otherwise>
</c:choose>

<fieldset class="fieldsetHome2">
  <legend class="legendHome"><html:link action="/InterestInformations"><bean:message key="dashBoard.interests.proposals"/></html:link></legend>
    <table id="interestProposals" class="inLineTableDashBoardFieldset homeFrame fieldsetTable">
    <logic:empty name="interests">
        <tr>
            <td>
                <bean:message key="dashBoard.interests.empty"/>.
            </td>
        </tr>
    </logic:empty>
    <logic:notEmpty name="interests">
        <c:forEach var="interest" items="${interests}">
            <tr class="interestDashboardContainer">
                <td>
                    <html:link action="/InterestInformations">
                        <html:param name="infoInterestId" value="${interest.id}"/>
                        ${interest.name}
                    </html:link>
                </td>
                <td class="tableButton">
                    <html:link action="/AddInterest">
                        <img src="images/add.png" alt="<bean:message key='dashBoard.interest.button.add.alt'/>" title="<bean:message key='dashBoard.interest.button.add'/> ${interest.name}" />
                        <html:param name="addedInterestId" value="${interest.id}"/>
                    </html:link>
                </td>
            </tr>
        </c:forEach>

    </logic:notEmpty>
  </table>
</fieldset>

<div class="clear homeGap"></div>

<fieldset class="fieldsetAppli">
  <legend class="legendHome"><html:link action="/Consultations"><bean:message key="lastInteractions.title"/></html:link></legend>
   <table id="lastInteractions" class="inLineTableDashBoardFieldset homeFrame fieldsetTable">
    <logic:empty name="lastInteractions">
        <tr>
            <td>
                <bean:message key="dashBoard.interaction.empty"/>.
            </td>
        </tr>
    </logic:empty>

    <logic:notEmpty name="lastInteractions">
        <logic:iterate id="triple" collection="${requestScope.lastInteractions}">
            <tr>
                <td class="messagePhoto">
                    <ili:getMiniature socialEntity="${triple.interaction.creator}"/>
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
</fieldset>
