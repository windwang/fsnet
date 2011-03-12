
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:set var="inputTextContent">
    <bean:message key="menu.7"/>
</c:set>

<div id="search">
    <html:form action="/SearchMember" method="POST">
        <fieldset>
            <html:text styleClass="field" property="searchText" onclick="this.value=''" value="${inputTextContent}"/>
            <html:submit styleClass="searchButton" value=" "/>
        </fieldset>
    </html:form>
</div>

<ul id="menu">
    <li>
        <html:link action="/Home" styleClass="${currentMenu == 'Home' ? 'current' : ''}">
            <bean:message key="menu.0"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Inbox" styleClass="${currentMenu == 'Messages' ? 'current' : ''}">
            <bean:message key="menu.8"/>
            <c:if test="${sessionScope.numNonReedPrivateMessages gt 0}">
            	(${sessionScope.numNonReedPrivateMessages})
            </c:if>
        </html:link>
    </li>
    <li>
        <html:link action="/Contacts" styleClass="${currentMenu == 'Contacts' ? 'current' : ''}">
            <bean:message key="menu.1"/>
      	     <c:if test="${sessionScope.numNewContactsRequests gt 0}">
            	(${sessionScope.numNewContactsRequests})
            </c:if>
        </html:link>
    </li>
    <li>
        <html:link action="/DisplayProfile" styleClass="${currentMenu == 'Profile' ? 'current' : ''}">
            <bean:message key="menu.3"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Consultations" styleClass="${currentMenu == 'Consultations' ? 'current' : ''}">
            <bean:message key="menu.10"/>
        </html:link>
    </li>
    <li>
        <html:link action="/Announces" styleClass="${currentMenu == 'Announces' ? 'current' : ''}">
            <bean:message key="menu.4"/>
            <c:if test="${sessionScope.numNonReedAnnounces gt 0}">
            	(${sessionScope.numNonReedAnnounces})
            </c:if>
        </html:link>
    </li>
    <li>
        <html:link action="/Events" styleClass="${currentMenu == 'Events' ? 'current' : ''}">
            <bean:message key="menu.5"/>
             <c:if test="${sessionScope.numNonReedEvents gt 0}">
            	(${sessionScope.numNonReedEvents })
            </c:if>
        </html:link>
    </li>
    <c:if test="${sessionScope.isMasterGroup }">
    <li>
    	<html:link action="/MemberList" styleClass="${currentMenu == 'MemberList' ? 'current' : ''}">
    		<bean:message key="menu.12"/>
    	</html:link>
    </li>
    <li>
    	<html:link action="/SearchGroup.do" styleClass="${currentMenu == 'Groups' ? 'current' : ''}">
    		<bean:message key="menu.11"/>
    	</html:link>
    </li>
    </c:if>
    <li>
        <html:link action="/Communities" styleClass="${currentMenu == 'Communities' ? 'current' : ''}">
            <bean:message key="menu.9"/>
        </html:link>
    </li>

    
</ul>
<a class="button" href="Logout" id="logout"><bean:message key="logout"/></a>
<div class="clear"></div>
