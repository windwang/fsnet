<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
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