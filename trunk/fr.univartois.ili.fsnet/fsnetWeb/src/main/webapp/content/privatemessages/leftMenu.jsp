<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
	Mes messages
</h2>
<ul>
    <li>
        <html:link action="/DisplayCreatePrivateMessage">
			Nouveau message
        </html:link>
    </li>
    <li>
        <html:link action="/Inbox">
			Boite de réception
        </html:link>
    </li>
    <li>
			Messages envoyés
    </li>
</ul>