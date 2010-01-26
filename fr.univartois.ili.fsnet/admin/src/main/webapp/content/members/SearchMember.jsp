<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<h3>Search a member</h3>
<html:form action="SearchMember">
    <div id="SearchMember">
        <html:text property="searchText" />
        <html:submit styleClass="button" />
    </div>
</html:form>

<h3>List of Members</h3>

<c:if test="${! empty membersResult}">

    <table  class="inLineTable">
        <c:forEach var="member" items="${membersResult}">
            <tr class="content">
                <td><html:link action="">${member.name} ${member.firstName}</html:link></td>
                  <td class="tableButton">
                    <html:link action="DeleteMember" styleClass="button">
                       Delete
                        <html:param name="entitySelected" value="${member.id}"/>
                    </html:link>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>