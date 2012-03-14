<%--
 Author : SAID Mohamed
 Author : BOURAGBA Mohamed
 --%>

<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<ul>
	<li><html:link action="/Members">
		<bean:message key="leftMenu.button.createMember" />
	</html:link></li>



	<li><html:link action="/Groups">
		<bean:message key="leftMenu.button.createGroup" />
	</html:link></li>


</ul>