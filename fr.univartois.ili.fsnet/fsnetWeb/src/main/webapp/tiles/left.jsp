<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<h2>My Network</h2>
<ul>
	<li>
		<html:link action="/CreateHub">
			Create new hub
		</html:link>
	</li>
</ul>
<h2>Interactions</h2>
<ul>
	<li>
		<html:link action="/CreateAnnounce">
			Create an annoucement 
		</html:link>
	</li>
	<li>
		<html:link action="/DisplayCreateEvent"> 
			Create an event
		</html:link>
	</li>
</ul>