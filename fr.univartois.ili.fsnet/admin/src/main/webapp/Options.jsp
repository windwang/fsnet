<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>    
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html xhtml="true">
    <head>
        <title>FSNet : Options</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="refresh" content="300;Options.jsp?option=current"/>
        <meta name="robots" content="index, follow" />
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <script type="text/JavaScript" src="admin.js"></script>
    </head>
    <body onload="showMenu();${param.showHide}('listToDeploy');${param.recherche}('rechercheVide')">
        <jsp:include page="header.jsp"></jsp:include>
        <div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>
            <div id="left">
                <h2><a href="Options.jsp?option=current"
                       title="configuration des options d'envoie de mail">Options</a>
                </h2>
                <jsp:include page="date.jsp"></jsp:include>
            </div>
            
            <jsp:include page="/WEB-INF/jspf/option.jsp"></jsp:include>
            
            <div id="side"></div>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html:html>