<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle">
	<tiles:getAsString name="title" />
</c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html xhtml="true">
<head>
<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />

<title><bean:message key="${pageTitle}" /></title>

<link rel="shortcut icon" href="images/Favicon.ico"
	type="image/vnd.microsoft.icon" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/fsnet-custom.css" />
<link type="text/css" href="css/jquery-tablesorter-custom.css"
	rel="stylesheet" />

<link type="text/css" href="css/cupertino/jquery-ui-1.8.18.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.9.0.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.0.0.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.0.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<link rel=stylesheet type="text/css" href="css/osx.css" />
<script type="text/javascript" src="js/jquery.simplemodal.1.4.4.min.js"></script>
<script type="text/javascript" src="js/osx.js"></script>
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="js/updateInterestParent.js"></script>
<script class="jsbin" type="text/javascript"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/functions.js"></script>

<link rel="stylesheet" type="text/css" media="screen"
	href="css/skeleton.css" />
</head>
<body>

	<div id="wrapHeader">
		<div id="header">
			<tiles:useAttribute name="currentMenu" scope="request" ignore="true" />
			<tiles:insert attribute="menu" />
		</div>
	</div>
	<div id="wrapBody">
		<div id="placeHeader"></div>
		<div id="leftMenu">
			<div id="logo">
				<tiles:insert attribute="logo" />
			</div>
			<div id="left">
				<h2>
					<a> <bean:message key="${pageTitle}" />
					</a>
				</h2>
				<tiles:insert attribute="left" />
			</div>
		</div>

		<div id="body-content">
			<tiles:insert attribute="body-content" />
		</div>
		<div style="clear: both"></div>

		<div id="placeFooterStyle"></div>
	</div>
	<div id="wrapFooterStyle">
		<div id="footerStyle">
			<tiles:insert attribute="footer" />
		</div>
	</div>
</body>
</html:html>
