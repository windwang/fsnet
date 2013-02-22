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
					<li class="${currentMenu == 'Home' ? 'active' : ''}"><s:a
							href="/Home" cssClass="%{currentMenu == 'Home' ? 'active' : ''}">
							<s:text name="menu.welcome" />
						</s:a></li>

					<li class="${currentMenu == 'Messages' ? 'active' : ''}"><s:a
							href="/Inbox"
							cssClass="%{currentMenu == 'Messages' ? 'active' : ''}">
							<s:text name="menu.inbox" />
							<c:if test="${sessionScope.numNonReedPrivateMessages gt 0}">
            	(${sessionScope.numNonReedPrivateMessages})
            </c:if>
						</s:a></li>
					<li Class="%{currentMenu == 'Contacts' ? 'active' : ''}"><s:a
							href="/Contacts"
							cssClass="%{currentMenu == 'Contacts' ? 'active' : ''}">
							<s:text name="menu.contacts" />
							<c:if test="${sessionScope.numNewContactsRequests gt 0}">
            	(${sessionScope.numNewContactsRequests})
            </c:if>
						</s:a></li>
					<li Class="%{currentMenu == 'Visits' ? 'active' : ''}"><s:a
							href="/Visits"
							cssClass="%{currentMenu == 'Visits' ? 'active' : ''}">
							<s:text name="menu.visits" />
							<c:if test="${sessionScope.numNewVisits gt 0}">
            	(${sessionScope.numNewVisits})
            </c:if>

						</s:a></li>
					<li Class="%{currentMenu == 'Profile' ? 'active' : ''}"><s:a
							action="/DisplayProfile"
							cssClass="%{currentMenu == 'Profile' ? 'active' : ''}">
							<s:text name="menu.profile" />
						</s:a></li>
					<li Class="%{currentMenu == 'Consultations' ? 'active' : ''}"><s:a
							action="/Consultations"
							cssClass="%{currentMenu == 'Consultations' ? 'active' : ''}">
							<s:text name="menu.consultations" />
							<c:if test="${sessionScope.numNonReedConsultations gt 0}">
            	(${sessionScope.numNonReedConsultations})
            </c:if>
						</s:a></li>
					<li Class="%{currentMenu == 'Announces' ? 'active' : ''}"><s:a
							action="/Announces"
							cssClass="%{currentMenu == 'Announces' ? 'active' : ''}">
							<s:text name="menu.announces" />
							<c:if test="${sessionScope.numNonReedAnnounces gt 0}">
            	(${sessionScope.numNonReedAnnounces})
            </c:if>
						</s:a></li>
					<li Class="%{currentMenu == 'Events' ? 'active' : ''}"><s:a
							action="/Events"
							cssClass="%{currentMenu == 'Events' ? 'active' : ''}">
							<s:text name="menu.events" />
							<c:if test="${sessionScope.numNonReedEvents gt 0}">
            	(${sessionScope.numNonReedEvents })
            </c:if>
						</s:a></li>
					<li Class="%{currentMenu == 'Communities' ? 'active' : ''}"><s:a
							action="/Communities"
							cssClass="%{currentMenu == 'Communities' ? 'active' : ''}">
							<s:text name="menu.communities" />
						</s:a></li>
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
						</s:form>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>