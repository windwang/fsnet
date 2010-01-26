<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h3>
    <bean:message key="announce"/>s
</h3>
<logic:empty name="listAnnounces">
    <bean:message key="announce.emptyList"/>
</logic:empty>
<logic:notEmpty name="listAnnounces">

    <table class="inLineTable">

        <logic:iterate id="announce" scope="request" name="listAnnounces">
            <tr>
                <bean:define id="idAnnounce" name="announce" property="id" />
                <th colspan="2">
                    <html:link action="/DisplayAnnounce.do" paramId="idAnnounce" paramName="idAnnounce">
                        <bean:write name="announce" property="title" />
                    </html:link>
                </th>
                <td>
                    <bean:message key="announce.by"/>
                    <html:link action="/DisplayProfile">
                        <html:param name="id" value="${announce.creator.id}"/>
                        ${announce.creator.firstName} ${announce.creator.name}
                    </html:link>
                </td>
                <td class="tableButton">
                    <bean:message key="announce.expiryDate"/>
                    <bean:write name="announce" property="endDate" format="dd/MM/yyyy"/>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>