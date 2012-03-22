<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<h2>
	<bean:message key="cv.leftMenu.my" />
</h2>

<ul>
	<li><html:link action="/CreateCv1">
			<bean:message key="profile.leftMenu.createCV" />
		</html:link></li>
	<li><html:link action="/DisplayCV">
			<bean:message key="profile.leftMenu.myCV" />
		</html:link></li>
</ul>