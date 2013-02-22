<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<bean:message key="consultations.leftMenu.my" />
</h2>
<ul>
	<ili:interactionFilter user="${socialEntity}"
		right="${rightAddConsultation}">
		<li><s:a action="/DisplayCreateConsultation">
				<bean:message key="consultations.leftMenu.create" />
			</s:a></li>
	</ili:interactionFilter>
</ul>