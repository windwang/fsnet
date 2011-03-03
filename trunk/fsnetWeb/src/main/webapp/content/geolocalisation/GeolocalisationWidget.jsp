<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<div id='geolocalisation' >
	<div id='mapCanvas'></div>
	<a name='linktogooglemap' href=''><bean:message key="geolocalisation.linkRoute"/></a>
	<input id='address' type='hidden' value="${event.address.address} ${event.address.city}"></input>
	<input id='userAddress' type='hidden' value="${member.address.address} ${member.address.city}"></input>
	<ul id="choices">
		<li id="errorGeo"><bean:message key="geolocalisation.error" /><li>
		<li><button id="workRouteGeo" name="workRouteGeo" onclick="buildRouteFromGeolocalisation()"><bean:message key="geolocalisation.workRouteGeo" /></button></li>
		<li><button id="workRouteHome" name="workRouteHome" onclick="buildRouteFromHome()"><bean:message key="geolocalisation.workRouteHome" /></button></li>
	</ul>
</div>
<script type='text/javascript'> document.onload = initializeGeolocalisation(); </script>
	