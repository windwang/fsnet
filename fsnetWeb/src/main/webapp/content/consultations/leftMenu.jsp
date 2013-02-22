<%@ taglib prefix="s" uri="/struts-tags"%>

<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="consultations.leftMenu.my" />
</h2>
<ul>
	<ili:interactionFilter user="${socialEntity}"
		right="${rightAddConsultation}">
<<<<<<< HEAD
		<li><s:a href="/DisplayCreateConsultation">
				<s:text name="consultations.leftMenu.create" />
=======
		<li><s:a action="/DisplayCreateConsultation">
				<bean:message key="consultations.leftMenu.create" />
>>>>>>> migrating events to struts2 architecture (warn : s:property (name => ???    property => ???   format => ???))
			</s:a></li>
	</ili:interactionFilter>
</ul>