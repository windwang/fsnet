<%--
 Author : SAID Mohamed
 Author : BOURAGBA Mohamed
 --%>

<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<ul>
	<li><s:a action="/Members">
		<bean:message key="leftMenu.button.createMember" />
	</s:a></li>



	<li><s:a action="/Groups">
		<bean:message key="leftMenu.button.createGroup" />
	</s:a></li>


</ul>