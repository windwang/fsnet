<%@ taglib prefix="s" uri="/struts-tags"%>

<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="consultations.leftMenu.my" />
</h2>
<ul>
	<ili:interactionFilter user="${socialEntity}"
		right="${rightAddConsultation}">
		<li><s:a href="/DisplayCreateConsultation">
				<s:text name="consultations.leftMenu.create" />
			</s:a></li>
	</ili:interactionFilter>
</ul>