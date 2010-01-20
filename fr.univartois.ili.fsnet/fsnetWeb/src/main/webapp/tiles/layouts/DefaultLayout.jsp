<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html xhtml="true">
<head>
	<meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" media="screen" href="css/fsnet-custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
	<title><tiles:getAsString name="title"/></title>
</head>
<body>
	<div class="wrap background">

            <tiles:insert attribute="menu"/>
            
            <tiles:insert attribute="logo"/>
            
			<tiles:insert attribute="high"/>
			
            <div id="left">
                <tiles:insert attribute="left"/>
            </div>
            
            <div id="body-content">
                <tiles:insert attribute="body-content"/>
            </div>
            
    </div>
    
    <tiles:insert attribute="footer"/>
    
    </body>
</html:html>
