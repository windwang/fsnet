<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<h3><bean:message key="members.0"/></h3>
<table  class="inLineTable"><tr><td>
<html:form action="SearchMember" method="POST">
    <div id="SearchMember">
        <html:text property="searchText" />
        <html:submit styleClass="button" />
    </div>
</html:form>
</td></tr></table>

<h3><bean:message key="members.1"/></h3>

<c:if test="${empty membersContactsResult && empty membersRequestedResult 
	&& empty membersAskedResult && empty membersResult}">
	<table  class="inLineTable"><tr><td>
	<bean:message key="members.noResult" />
	</td></tr></table>
</c:if>


<c:if test="${! empty membersContactsResult}">
    <h4><bean:message key="members.2"/></h4>
    <table  class="inLineTable">
        <c:forEach var="member" items="${membersContactsResult}">
            <tr class="content">
            	<td class="miniatureContainer">
	               	<ili:getMiniature socialEntity="${member}"/>
               	</td>
               	<td>
               		<ili:getSocialEntityInfos socialEntity="${member}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${! empty membersRequestedResult}">
    <h4><bean:message key="members.9"/></h4>
    <table  class="inLineTable">
        <c:forEach var="member" items="${membersRequestedResult}">
            <tr class="content">
            	<td class="miniatureContainer">
            		<ili:getMiniature socialEntity="${member}"/>
               	</td>
                <td>
                	<ili:getSocialEntityInfos socialEntity="${member}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${! empty membersAskedResult}">
    <h4><bean:message key="members.4"/></h4>
    <table  class="inLineTable">
        <c:forEach var="member" items="${membersAskedResult}">
            <tr class="content">
            	<td class="miniatureContainer">
            		<ili:getMiniature socialEntity="${member}"/>
               	</td>
                <td>
                   <ili:getSocialEntityInfos socialEntity="${member}"/>
                </td>
                <td class="tableButton">
                    <html:link action="/AcceptContact" styleClass="button">
                        <bean:message key="members.5"/>
                        <html:param name="entityAccepted" value="${member.id}"/>
                    </html:link>
                    <html:link action="/RefuseContact" styleClass="button">
                        <bean:message key="members.6"/>
                        <html:param name="entityRefused" value="${member.id}"/>
                    </html:link>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${! empty membersResult}">

    <h4><bean:message key="members.7"/></h4>
    <table  class="inLineTable">
        <c:forEach var="member" items="${membersResult}">
            <tr class="content">
            	<td class="miniatureContainer">
            		<ili:getMiniature socialEntity="${member}"/>
               	</td>
                <td>
                	<ili:getSocialEntityInfos socialEntity="${member}"/>
                </td>
                <td class="tableButton">
                    <html:link action="/ContactDemand" styleClass="button">
                        <bean:message key="members.8"/>
                        <html:param name="entitySelected" value="${member.id}"/>
                    </html:link>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>