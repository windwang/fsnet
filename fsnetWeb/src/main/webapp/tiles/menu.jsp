<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ili" uri="../WEB-INF/ili.tld"%>

<s:set name="searchMessage">
	<s:text name="menu.search" />
</s:set>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="${currentMenu == 'Home' ? 'active' : ''}"><a
						href='<s:url action="Home"/>'
						class="%{currentMenu == 'Home' ? 'active' : ''}"> <s:text
								name="menu.welcome" />
					</a></li>

					<li class="${currentMenu == 'Messages' ? 'active' : ''}"><a
						href="<s:url action='Inbox'/>"
						class="%{currentMenu == 'Messages' ? 'active' : ''}"> <s:text
								name="menu.inbox" /> <c:if
								test="${sessionScope.numNonReedPrivateMessages gt 0}">
            	(${sessionScope.numNonReedPrivateMessages})
            </c:if>
					</a></li>
					<li Class="%{currentMenu == 'Contacts' ? 'active' : ''}"><a
						href="<s:url action='Contact'/>"
						class="%{currentMenu == 'Contacts' ? 'active' : ''}"> <s:text
								name="menu.contacts" /> <c:if
								test="${sessionScope.numNewContactsRequests gt 0}">
            	(${sessionScope.numNewContactsRequests})
            </c:if>
					</a></li>
					<li Class="%{currentMenu == 'Visits' ? 'active' : ''}"><a
						href="<s:url action='Visits' />"
						class="%{currentMenu == 'Visits' ? 'active' : ''}"> <s:text
								name="menu.visits" /> <c:if
								test="${sessionScope.numNewVisits gt 0}">
            	(${sessionScope.numNewVisits})
            </c:if>
					</a></li>
					<li Class="%{currentMenu == 'Profile' ? 'active' : ''}"><a
						href="<s:url action='DisplayProfile' />"
						class="%{currentMenu == 'Profile' ? 'active' : ''}"> <s:text
								name="menu.profile" />
					</a></li>
					<li Class="%{currentMenu == 'Consultations' ? 'active' : ''}"><a
						href="<s:url action='Consultations' />"
						class="%{currentMenu == 'Consultations' ? 'active' : ''}"> <s:text
								name="menu.consultations" /> <c:if
								test="${sessionScope.numNonReedConsultations gt 0}">
            	(${sessionScope.numNonReedConsultations})
            </c:if>
					</a></li>
					<li Class="%{currentMenu == 'Announces' ? 'active' : ''}"><a
							href="<s:url action='Announces' />"
							class="%{currentMenu == 'Announces' ? 'active' : ''}">
							<s:text name="menu.announces" />
							<c:if test="${sessionScope.numNonReedAnnounces gt 0}">
            	(${sessionScope.numNonReedAnnounces})
            </c:if>
						</a></li>
					<li Class="%{currentMenu == 'Events' ? 'active' : ''}"><a
							href="<s:url action='Events' />"
							class="%{currentMenu == 'Events' ? 'active' : ''}">
							<s:text name="menu.events" />
							<c:if test="${sessionScope.numNonReedEvents gt 0}">
            	(${sessionScope.numNonReedEvents })
            </c:if>
						</a></li>
					<li Class="%{currentMenu == 'Communities' ? 'active' : ''}"><a
							href="<s:url action='Communities' />"
							class="%{currentMenu == 'Communities' ? 'active' : ''}">
							<s:text name="menu.communities" />
						</a></li>
					<li class="item-204 divider-vertical divider"></li>
					<li><a href="Logout"><s:text name="logout" /></a></li>
					<li class="item-204 divider-vertical divider"></li>
					<li><s:form action="/Research" method="post"
							cssClass="navbar-search pull-left noMargin littleTopPadding searchBar">
							<div>
								<i class="icon-search"></i>
								<s:textfield cssClass="span2" var="searchText" name="searchText" />

								<input style="display: none" type="checkbox" checked
									name="selectedResearch" value="tous" />
							</div>
						</s:form></li>
				</ul>
			</div>
		</div>
	</div>
</div>