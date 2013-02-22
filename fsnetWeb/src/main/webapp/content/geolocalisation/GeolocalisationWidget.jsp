<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id='geolocalisation'>
	<div id='mapCanvas'></div>
	<a name='linktogooglemap' href=''>
	<s:text name="geolocalisation.linkRoute" /></a> 
	<input id='address' type='hidden'
		value="%{event.address.address} %{event.address.city}">
	</input> 
	<input
		id='userAddress' type='hidden'
		value="%{member.address.address} %{member.address.city}">
	</input>
	<ul id="choices">
		<li id="errorGeo"><s:text name="geolocalisation.error" />
		<li>
		<li><button id="workRouteGeo" name="workRouteGeo" class="button"
				onclick="buildRouteFromGeolocalisation()">
				<s:text name="geolocalisation.workRouteGeo" />
			</button></li>
		<li><button id="workRouteHome" name="workRouteHome"
				class="button" onclick="buildRouteFromHome()">
				<s:text name="geolocalisation.workRouteHome" />
			</button></li>
	</ul>
</div>
<script type='text/javascript'>
	document.onload = initializeGeolocalisation();
</script>
