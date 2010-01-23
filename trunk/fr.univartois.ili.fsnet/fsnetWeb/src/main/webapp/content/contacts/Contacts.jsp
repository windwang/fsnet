<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<c:if test="${empty theUser.contacts && empty theUser.asked && empty theUser.requested}">
    Vous n'avez pas encore de contacts.
</c:if>
<logic:notEmpty name="theUser" property="contacts" >
    <h3>Liste de vos contacts</h3>
    <table class="inLineTable">

        <c:forEach var="contact" items="${theUser.contacts}">
            <tr>
                <td>
                    ${contact.prenom} ${contact.nom}
                </td>
                <td class="tableButton">
                    <html:link action="/DisplayProfile" styleClass="button">
                        Profile
                        <html:param name="id" value="${contact.id}"/>
                    </html:link>
                    <html:link action="/DeleteContact" styleClass="button">
                        Delete
                        <html:param name="entityDeleted" value="${contact.id}" />
                    </html:link>
                </td>
            </tr>
        </c:forEach>

    </table>
</logic:notEmpty>

<logic:notEmpty  name="theUser" property="asked" >
    <h3>Liste des demandes reçues</h3>
    <table class="inLineTable">

        <c:forEach var="contact" items="${theUser.asked}">
            <tr>
                <td>
                    ${contact.prenom} ${contact.nom}
                </td>
                <td class="tableButton">
                    <html:link action="/AcceptContact" styleClass="button">
                        <html:param name="entityAccepted" value="${contact.id}" />
    		Accept
                    </html:link>
                    <html:link action="/RefuseContact" styleClass="button">
                        <html:param name="entityRefused" value="${contact.id}" />
    		Refuse
                    </html:link>
                </td>
            </tr>
        </c:forEach>

    </table>
</logic:notEmpty>

<logic:notEmpty  name="theUser" property="requested" >
    <table class="inLineTable">
        <h3>Liste de vos demandes effectuées</h3>
        <c:forEach var="contact" items="${theUser.requested}">
            <tr>
                <td>
                    ${contact.prenom} ${contact.nom}
                </td>
            </tr>
        </c:forEach>
    </table>
</logic:notEmpty>