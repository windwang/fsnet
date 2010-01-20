<%-- 
    Document   : CreateAnnounce
    Created on : 18 janv. 2010, 18:06:12
    Author     : Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html:form action="/Announces">
	<table id="SearchAnnounce">
		<tr>
			<td>
				<label for="announceSearch">recherche annonce  :</label>
			</td>
			<td>
    			<html:text property="textSearchAnnounce" styleId="textSearchAnnounce" />
    		</td>
    		<td>
    			<html:submit styleClass="button">Search Announce</html:submit>
    		</td>
    	</tr>
    	<tr>
    		<td colspan="2">
    			<html:messages id="message" />
    			<html:errors/>
    		</td>
    	</tr>
    </table>
</html:form>