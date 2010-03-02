<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if
    test="${empty theUser.contacts && empty theUser.asked && empty theUser.requested}">
    <h3><bean:message key="contact.conts" /></h3>
    <bean:message key="contact.noContact" />
</c:if>
<logic:notEmpty name="theUser" property="contacts">
    <h3><bean:message key="contact.listContact" /></h3>
    <table class="inLineTable">

        <c:forEach var="contact" items="${theUser.contacts}">
            <tr>
               	<td class="miniatureContainer">
               	<html:link action="/DisplayProfile">
               	    <html:param name="id" value="${contact.id}"/>
               		<img src="GetMiniature.do?memberId=${contact.id}"/>
               	</html:link>	
               	</td>
                <td>
                    <html:link action="/DisplayProfile">
                    	${contact.firstName} ${contact.name}
                    	<html:param name="id" value="${contact.id}" />
                    </html:link> 
                </td>
                <td class="tableButton">
                	<html:link action="/DisplayCreatePrivateMessage" styleClass="button">
    					<bean:message key="showProfile.send"/>
    					<html:param name="receiver" value="${contact.email}"/>
					</html:link>
                    <html:link action="/DeleteContact" styleClass="button">
                        <bean:message key="contact.delete" />
                        <html:param name="entityDeleted" value="${contact.id}" />
                    </html:link></td>
            </tr>
        </c:forEach>

    </table>
</logic:notEmpty>

<logic:notEmpty name="theUser" property="asked">
    <h3><bean:message key="contact.re" /></h3>
    <table class="inLineTable">

        <c:forEach var="contact" items="${theUser.asked}">
            <tr>
                <td class="miniatureContainer">
            		<html:link action="/DisplayProfile">
               	    	<html:param name="id" value="${contact.id}"/>
               			<img src="GetMiniature.do?memberId=${contact.id}"/>
               		</html:link>
            	</td>
                <td>
                	<html:link action="/DisplayProfile">
                    	${contact.firstName} ${contact.name}
                    	<html:param name="id" value="${contact.id}" />
                    </html:link>
                </td>
                <td class="tableButton">
                    <html:link action="/DisplayCreatePrivateMessage" styleClass="button">
    					<bean:message key="showProfile.send"/>
    					<html:param name="receiver" value="${contact.email}"/>
					</html:link>
                	<html:link action="/AcceptContact" styleClass="button">
                        <html:param name="entityAccepted" value="${contact.id}" />
                        <bean:message key="contact.accept" />
                    </html:link> <html:link action="/RefuseContact" styleClass="button">
                        <html:param name="entityRefused" value="${contact.id}" />
                        <bean:message key="contact.refuse" />
                    </html:link>
				</td>
            </tr>
        </c:forEach>

    </table>
</logic:notEmpty>

<logic:notEmpty name="theUser" property="requested">
 <h3><bean:message key="contact.eff" /></h3>
    <table class="inLineTable">
        <c:forEach var="contact" items="${theUser.requested}">
            <tr>
            	<td class="miniatureContainer">
            		<html:link action="/DisplayProfile">
               	    	<html:param name="id" value="${contact.id}"/>
               			<img src="GetMiniature.do?memberId=${contact.id}"/>
               		</html:link>
            	</td>
                <td>
                	<html:link action="/DisplayProfile">
                    	${contact.firstName} ${contact.name}
                    	<html:param name="id" value="${contact.id}" />
                    </html:link>
                </td>
                <td class="tableButton">
                  	<html:link action="/DisplayCreatePrivateMessage" styleClass="button">
    					<bean:message key="showProfile.send"/>
    					<html:param name="receiver" value="${contact.email}"/>
					</html:link>
				</td>
				<td>
					<html:link action="/CancelAskContact" styleClass="button">
						<html:param name="id" value="${contact.id}"/>
						<bean:message key="contacts.cancel"/>
					</html:link>
				</td>
            </tr>
        </c:forEach>
    </table>
</logic:notEmpty>