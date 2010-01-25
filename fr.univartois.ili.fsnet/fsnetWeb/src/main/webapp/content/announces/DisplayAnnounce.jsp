<%-- 
    Document   : DisplayAnnounce
    Created on : 18 janv. 2010, 18:06:12
    Author     : Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<table id="DisplayAnnounce">
    <tr>
        <th>
            <h3><bean:write name="announce" property="nom" /></h3>
        </th>
    </tr>
    <tr class="authorDate">
        <td>
            <bean:message  key="announce.createdBy"/>
            <html:link action="/DisplayProfile">
                <html:param name="id" value="${announce.createur.id}"/>
                ${announce.createur.prenom} ${announce.createur.nom}
            </html:link><<bean:message key="announce.expiryDate"/>
            <bean:write name="announce" property="dateFinAnnonce" format="dd/MM/yyyy" />
        </td>
    </tr>
    <tr>
        <td >
            ${announce.contenu}
        </td>
    </tr>

    <tr>
        <td colspan="2">
            <html:messages id="message" />
            <html:errors/>
        </td>
    </tr>

    <tr>
        <td  class="alignRight">

            <logic:present name="owner">
                <bean:define id="idAnnounce" name="announce" property="id" />
                <html:link  action="/DisplayForModifyAnnounce"  paramId="idAnnounce" paramName="idAnnounce" styleClass="button"><bean:message key="announce.updateAnnounce"/> </html:link>
                <html:link  action="/DeleteAnnounce"  paramId="idAnnounce" paramName="idAnnounce" styleClass="button"><bean:message key="announce.deleteAnnounce"/> </html:link>
            </logic:present>

        </td>
    </tr>
</table>
