<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html xhtml="true">
    <head>
        <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" ></meta>
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/fsnet-custom.css" />
        <title><tiles:getAsString name="title"/></title>
        <link type="text/css" href="css/cupertino/jquery-ui-1.7.2.custom.css" rel="stylesheet" />
    </head>
    <body>
        <div class="wrap background">
            <tiles:useAttribute name="currentMenu" scope="request" ignore="true"/>
            <tiles:insert attribute="menu"/>
            <div id="logo" >
                <tiles:insert attribute="logo"/>
            </div>
            <tiles:insert attribute="high"/>


            <div id="left">
                <c:set var="pageTitle">
                    <tiles:getAsString name="title"/>
                </c:set>
                <h2>
                    <a><bean:message key="${pageTitle}"/></a>
                </h2>
                <tiles:insert attribute="left"/>
            </div>

            <div id="body-content">
                <tiles:insert attribute="body-content"/>
            </div>

        </div>
        <tiles:insert attribute="footer"/>
    </body>
</html:html>
