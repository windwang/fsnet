
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="ili" uri="../WEB-INF/ili.tld"%>

<bean:define id="searchMessage"><bean:message key="menu.7"/></bean:define>

<div id="search">
    <html:form action="/Research" method="post">
        <fieldset>
            <html:text styleClass="field" styleId="searchText" property="searchText" />
			<ili:placeHolder id="searchText" value="${searchMessage}" />
            <html:submit styleClass="searchButton" value=" "/>
         <input style="display:none" type="checkbox" checked	name="selectedResearch" value="tous" />
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
        <html:link action="/Visits" styleClass="${currentMenu == 'Visits' ? 'current' : ''}">
            <bean:message key="menu.13"/>
      	     <c:if test="${sessionScope.numNewVisits gt 0}">
            	(${sessionScope.numNewVisits})
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
            <c:if test="${sessionScope.numNonReedConsultations gt 0}">
            	(${sessionScope.numNonReedConsultations})
            </c:if>
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
    
    <li>
        <html:link action="/Communities" styleClass="${currentMenu == 'Communities' ? 'current' : ''}">
            <bean:message key="menu.9"/>
        </html:link>
    </li>
    
</ul>
<a class="button" href="Logout" id="logout"><bean:message key="logout"/></a>
<div class="clear"></div>
