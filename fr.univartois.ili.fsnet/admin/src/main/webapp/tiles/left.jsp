<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<ul>
    <li>
        <html:link action="/DisplayCreateMember">
			<bean:message key="left.2"/>
        </html:link>
    </li>
    <li>
        <html:link action="/DisplayCreateInterest">
			<bean:message key="left.3"/> 
        </html:link>
    </li>

</ul>