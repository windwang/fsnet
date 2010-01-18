<%-- 
    Document   : DefaultTemplate
    Created on : 18 janv. 2010, 18:20:01
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<html>
    <head>
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <meta name="robots" content="index, follow" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <title><tiles:getAsString name="title"/></title>
        <script type="text/JavaScript" src="maquette.js"/>
    </head>
    <body>
        <div class="wrap background">
            <div id="header">
                <tiles:insert attribute="header"/>
            </div>
            <div id="menu">
                <tiles:insert attribute="menu"/>
            </div>
            <div id="left">
                <tiles:insert attribute="sidebarLeft"/>
            </div>
            <div id="right">
                <tiles:insert attribute="sidebarRight"/>
            </div>
            <div id="body-content">
                <tiles:insert attribute="body-content"/>
            </div>
        </div>
        <tiles:insert attribute="footer"/>
    </body>
</html>
