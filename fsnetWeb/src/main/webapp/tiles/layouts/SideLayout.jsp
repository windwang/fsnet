<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />

<title><tiles:getAsString name="title" /></title>

<link rel="shortcut icon" href="images/Favicon.ico"
	type="image/vnd.microsoft.icon" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/fsnet-custom.css" />

<link type="text/css" href="css/cupertino/jquery-ui-1.8.18.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.9.0.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.0.0.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.0.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<link type="text/css" rel="stylesheet" media="all"
	href="css/jquery-ui-timepicker-addon.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/bootstrapStyle.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="bootstrap/css/bootstrap-responsive.min.css" />
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<!-- DO NOT GET THIS LIBRARY HERE !!! -->
<!--<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>-->

</head>

<body>
	<div class="wrap background">
		<tiles:useAttribute name="currentMenu" scope="request" ignore="true" />
		<%-- 		<tiles:insert attribute="menu" /> --%>
		<tiles:insertAttribute name="menu" />
		<div id="logo">
			<tiles:insertAttribute name="logo" />
		</div>
		<tiles:insertAttribute name="high" />


		<div id="left">
			<c:set var="pageTitle">
				<tiles:getAsString name="title" />
			</c:set>
			<h2>
				<a><s:text name="%{pageTitle}" /></a>
			</h2>

			<tiles:insertAttribute name="left" />
		</div>

		<div id="body-content">
			<tiles:insertAttribute name="body-content" />

		</div>

	</div>
	<tiles:insertAttribute name="footer" />
</body>
</html>