
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="ili" uri="../WEB-INF/ili.tld"%>

<bean:define id="searchMessage">
	<bean:message key="menu.search" />
</bean:define>



<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="${currentMenu == 'Home' ? 'active' : ''}"><html:link
							action="/Home"
							styleClass="${currentMenu == 'Home' ? 'active' : ''}">
							<bean:message key="menu.welcome" />
						</html:link></li>
					<li class="${currentMenu == 'Messages' ? 'active' : ''}"><html:link
							action="/Inbox"
							styleClass="${currentMenu == 'Messages' ? 'active' : ''}">
							<bean:message key="menu.inbox" />
							<c:if test="${sessionScope.numNonReedPrivateMessages gt 0}">
            	(${sessionScope.numNonReedPrivateMessages})
            </c:if>
						</html:link></li>
					<li Class="${currentMenu == 'Contacts' ? 'active' : ''}"><html:link
							action="/Contacts"
							styleClass="${currentMenu == 'Contacts' ? 'active' : ''}">
							<bean:message key="menu.contacts" />
							<c:if test="${sessionScope.numNewContactsRequests gt 0}">
            	(${sessionScope.numNewContactsRequests})
            </c:if>
						</html:link></li>
					<li Class="${currentMenu == 'Visits' ? 'active' : ''}"><html:link
							action="/Visits"
							styleClass="${currentMenu == 'Visits' ? 'active' : ''}">
							<bean:message key="menu.visits" />
							<c:if test="${sessionScope.numNewVisits gt 0}">
            	(${sessionScope.numNewVisits})
            </c:if>
						</html:link></li>
					<li Class="${currentMenu == 'Profile' ? 'active' : ''}"><html:link
							action="/DisplayProfile"
							styleClass="${currentMenu == 'Profile' ? 'active' : ''}">
							<bean:message key="menu.profile" />
						</html:link></li>
					<li Class="${currentMenu == 'Consultations' ? 'active' : ''}"><html:link
							action="/Consultations"
							styleClass="${currentMenu == 'Consultations' ? 'active' : ''}">
							<bean:message key="menu.consultations" />
							<c:if test="${sessionScope.numNonReedConsultations gt 0}">
            	(${sessionScope.numNonReedConsultations})
            </c:if>
						</html:link></li>
					<li Class="${currentMenu == 'Announces' ? 'active' : ''}"><html:link
							action="/Announces"
							styleClass="${currentMenu == 'Announces' ? 'active' : ''}">
							<bean:message key="menu.announces" />
							<c:if test="${sessionScope.numNonReedAnnounces gt 0}">
            	(${sessionScope.numNonReedAnnounces})
            </c:if>
						</html:link></li>
					<li Class="${currentMenu == 'Events' ? 'active' : ''}"><html:link
							action="/Events"
							styleClass="${currentMenu == 'Events' ? 'active' : ''}">
							<bean:message key="menu.events" />
							<c:if test="${sessionScope.numNonReedEvents gt 0}">
            	(${sessionScope.numNonReedEvents })
            </c:if>
						</html:link></li>
					<li Class="${currentMenu == 'Communities' ? 'active' : ''}"><html:link
							action="/Communities"
							styleClass="${currentMenu == 'Communities' ? 'active' : ''}">
							<bean:message key="menu.communities" />
						</html:link></li>
					<li class="item-204 divider-vertical divider"></li>
					<li><a href="Logout"><bean:message key="logout" /></a></li>
					<li class="item-204 divider-vertical divider"></li>
					<li><html:form action="/Research" method="post"
							styleClass="navbar-search pull-left noMargin littleTopPadding searchBar">
							<div>
							<i class="icon-search"></i> 
								<html:text styleClass="span2 search-query searchBarInput" styleId="searchText"
									property="searchText" />
								
								<input style="display: none" type="checkbox" checked
									name="selectedResearch" value="tous" />
							</div>
						</html:form></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>