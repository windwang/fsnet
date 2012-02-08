<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle">
	<tiles:getAsString name="title" />
</c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html xhtml="true" lang="true">
	<head>
		
		<meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
		<meta http-equiv="Cache-Control" content="no-cache"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="0"/> 
		<link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="css/fsnet-custom.css" />
		<link type="text/css" href="css/jquery-tablesorter-custom.css" rel="stylesheet" />
		<link type="text/css" rel="stylesheet" media="all" href="css/chat.css" />
		<link type="text/css" rel="stylesheet" media="all" href="css/screen.css" />
				
		<title><bean:message key="${pageTitle}" /></title>
		<link type="text/css" href="css/cupertino/jquery-ui-1.7.2.custom.css" rel="stylesheet" />
		<script type="text/javascript" src="js/functions.js"></script>
		<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
		<script type="text/javascript" src="js/geolocalisation.js"></script>
		<script type="text/javascript" src="js/jquery.simplemodal-1.4.2.js"></script>
		
		<script class="jsbin" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="js/talkUtils.js"></script>
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/chat.js"></script>
		
	</head>
	<body>
		<div class="wrap background">
			<tiles:useAttribute name="currentMenu" scope="request" ignore="true" /> 
			<tiles:insert attribute="menu" />

			<div style="float: left; width: 25%">
				<div id="logo">
					<tiles:insert attribute="logo" />
				</div>
				<div id="left" style="margin-top: 30px; width: 100%">
					<h2>
						<a>
							<bean:message key="${pageTitle}" />
						</a>
					</h2>
					<tiles:insert attribute="left" /> 
					<tiles:insert attribute="loggedUsers" />
				</div>
            </div>
            <div id="body-content" style="width:75%;">
                <tiles:insert attribute="body-content"/>
            </div>
        </div>
        <tiles:insert attribute="footer"/>
        <script type="text/javascript">
		    function confirmDelete(action){
		        if(confirm("<bean:message key='confirmation.delete' />")){
		        	  document.location = action; 
		        }
		    }
		    function confirmDelete2(formid) {
				if(confirm("<bean:message key='confirmation.delete' />")) {
					document.getElementById(formid).submit();
				}
		    }
		    		</script> 
    </body>
</html:html>
