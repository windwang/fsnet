<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>


<h3>Search Member</h3>
<html:form action="SearchMember">
    <div id="SearchMember">
        <html:text property="searchText" />
        <html:submit styleClass="button" />
    </div>
</html:form>

<h3>Members :</h3>

<c:if test="${! empty membersContactsResult}">
    <h4>Liste de vos contacts</h4>
    <table  class="inLineTable">
        <c:forEach var="member" items="${membersContactsResult}">
            <tr class="content">
                <td>${member.nom} ${member.prenom}</td>
                <td class="tableButton">
                    <html:link action="/DisplayProfile" styleClass="button">
                        Profil
                        <html:param name="id" value="${member.id}"/>
                    </html:link>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${! empty membersRequestedResult}">
    <h4>Liste de vos demandes effectuées</h4>
    <table  class="inLineTable">
        <c:forEach var="member" items="${membersRequestedResult}">
            <tr class="content">
                <td>${member.nom} ${member.prenom}</td>
                <td class="tableButton">
                    <html:link action="/DisplayProfile" styleClass="button">
                        Profil
                        <html:param name="id" value="${member.id}"/>
                    </html:link>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${! empty membersAskedResult}">
    <h4>Liste des demandes reçues</h4>
    <table  class="inLineTable">
        <c:forEach var="member" items="${membersAskedResult}">
            <tr class="content">
                <td>${member.nom} ${member.prenom}</td>
                <td class="tableButton">
                    <html:link action="/AcceptContact" styleClass="button">
                        Accepter
                        <html:param name="entityAccepted" value="${member.id}"/>
                    </html:link>
                    <html:link action="/RefuseContact" styleClass="button">
                        Refuser
                        <html:param name="entityRefused" value="${member.id}"/>
                    </html:link>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${! empty membersResult}">

    <h4>Autres membres</h4>
    <table  class="inLineTable">
        <c:forEach var="member" items="${membersResult}">
            <tr class="content">
                <td>${member.nom} ${member.prenom}</td>
                <td class="tableButton">
                    <html:link action="/ContactDemand" styleClass="button">
                        Ajouter
                        <html:param name="entitySelected" value="${member.id}"/>
                    </html:link>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>