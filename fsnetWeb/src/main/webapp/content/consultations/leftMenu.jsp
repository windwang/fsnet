<%@ taglib prefix="s" uri="/struts-tags"%>

<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<s:text name="consultations.leftMenu.my" />
</h2>
<ul>
	<ili:interactionFilter user="${socialEntity}"
		right="${rightAddConsultation}">
		<li><a href="<s:url action='DisplayCreateConsultation'/>">
				<s:text name="consultations.leftMenu.create" />
			</a></li>
	</ili:interactionFilter>
</ul>