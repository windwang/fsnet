<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:if
    test="${empty requestScope.paginatorContacts.resultList && empty requestScope.paginatorAsked.resultList && empty requestScope.paginatorRequested.resultList}">
    <h3><bean:message key="contact.conts" /></h3>
    <bean:message key="contact.noContact" />
</c:if>

<c:if test="${not empty requestScope.paginatorAsked.resultList}">
    <h3><bean:message key="contact.re" /></h3>
    <table class="inLineTable">

        <c:forEach var="contact" items="${requestScope.paginatorAsked.resultList}">
            <tr>
                <td class="miniatureContainer">
            		<html:link action="/DisplayProfile">
               	    	<html:param name="id" value="${contact.id}"/>
               			<img src="miniature/${contact.id}.png"/>
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
    <c:set var="paginatorInstance" value="${requestScope.paginatorAsked}" scope="request"/>
	<c:set var="paginatorAction" value="/Contacts" scope="request"/>
	<c:set var="paginatorTile" value="asked" scope="request"/>
	<c:import url="/content/pagination/Pagination.jsp"/>
</c:if>

<c:if test="${not empty requestScope.paginatorContacts.resultList}">
    <h3><bean:message key="contact.listContact" /></h3>
    <table class="inLineTable">

        <c:forEach var="contact" items="${requestScope.paginatorContacts.resultList}">
            <tr>
               	<td class="miniatureContainer">
               	<html:link action="/DisplayProfile">
               	    <html:param name="id" value="${contact.id}"/>
               		<img src="miniature/${contact.id}.png"/>
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
    <c:set var="paginatorInstance" value="${requestScope.paginatorContacts}" scope="request"/>
	<c:set var="paginatorAction" value="/Contacts" scope="request"/>
	<c:set var="paginatorTile" value="contacts" scope="request"/>
	<c:import url="/content/pagination/Pagination.jsp"/>
</c:if>

<c:if test="${not empty requestScope.paginatorRequested.resultList}">
 <h3><bean:message key="contact.eff" /></h3>
    <table class="inLineTable">
        <c:forEach var="contact" items="${requestScope.paginatorRequested.resultList}">
            <tr>
            	<td class="miniatureContainer">
            		<html:link action="/DisplayProfile">
               	    	<html:param name="id" value="${contact.id}"/>
               			<img src="miniature/${contact.id}.png"/>
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
					<html:link action="/CancelAskContact" styleClass="button">
						<html:param name="id" value="${contact.id}"/>
						<bean:message key="contacts.cancel"/>
					</html:link>
				</td>
            </tr>
        </c:forEach>
    </table>
    <c:set var="paginatorInstance" value="${requestScope.paginatorRequested}" scope="request"/>
	<c:set var="paginatorAction" value="/Contacts" scope="request"/>
	<c:set var="paginatorTile" value="requested" scope="request"/>
	<c:import url="/content/pagination/Pagination.jsp"/>
</c:if>