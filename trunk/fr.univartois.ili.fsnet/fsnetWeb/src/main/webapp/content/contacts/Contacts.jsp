<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<a href="AskContact.do">Ajouter un contact</a>
<h3>Liste de vos contacts</h3>
<table class="inLineTable">

    <c:forEach var="contact" items="${listContacts}">
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

<h3>Liste des demandes reçues</h3>
<table class="inLineTable">

    <c:forEach var="contact" items="${listContacts}">
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

<table class="inLineTable">
    <h3>Liste de vos demandes effectuées</h3>
    <c:forEach var="contact" items="${listRequests}">
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

