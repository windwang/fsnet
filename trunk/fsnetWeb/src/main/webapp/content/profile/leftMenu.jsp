<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
    <bean:message key="profile.LeftMenu"/>
</h2>
<ul>
    <li>
    		<html:link action="Profile.do">
            	<bean:message key="showProfile.edit"/>
        	</html:link>
        
    </li>
    <li>
    	<html:link action="/Interests">
           	<bean:message key="showProfile.edit.interests"/>
        </html:link>
    </li>
</ul>

<c:if test="${requestScope.isMasterGroup }">
<h2>
    <bean:message key="profile.LeftMenuManage"/>
</h2>
	
	<ul>
		<li><html:link action="/SearchGroup.do">
			<bean:message key="profile.LeftMenuManageGroups" />
			<html:param name="id" value="${requestScope.groupId}" />
      </html:link></li>
		<li>
			<html:link action="/MemberList">
				<bean:message key="profile.LeftMenuManageMembers" />
			</html:link>
		</li>
	</ul>
</c:if>

