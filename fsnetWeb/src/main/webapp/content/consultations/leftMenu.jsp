<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h2>
    <bean:message key="consultations.leftMenu"/>
</h2>
<ul>
    <li>
        <html:link action="/DisplayConsultations">
            <bean:message key="consultations.manageLeftMenu"/>
        </html:link>
    </li>
    <li>
        <html:link action="/CreateConsultationMenu">
            <bean:message key="consultations.createLeftMenu"/>
        </html:link>
    </li>
</ul>