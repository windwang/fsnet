<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<logic:present name="listAnnounces">
    <h3>
        Announces : 
    </h3>
    <table class="inLineTable">

        <logic:iterate id="announce" scope="request" name="listAnnounces">
            <tr>
                <bean:define id="idAnnounce" name="announce" property="id" />
                <th colspan="2">
                    <html:link action="/DisplayAnnounce.do" paramId="idAnnounce" paramName="idAnnounce">
                        <bean:write name="announce" property="nom" />
                    </html:link>
                </th>
                <td>
                    Par
                    <html:link action="/DisplayProfile">
                        <html:param name="id" value="${announce.createur.id}"/>
                        ${announce.createur.prenom} ${announce.createur.nom}
                    </html:link>
                </td>
                <td class="tableButton">
                    Valide jusqu'au
                    <bean:write name="announce" property="dateFinAnnonce" format="dd/MM/yyyy"/>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:present>