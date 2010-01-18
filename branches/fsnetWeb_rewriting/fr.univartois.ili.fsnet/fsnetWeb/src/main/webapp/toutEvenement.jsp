<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <meta http-equiv="content-type"
              content="application/xhtml+xml; charset=UTF-8" />
        <meta name="author" content="Luka Cvrk - www.solucija.com" />
        <meta name="description" content="Site Description" />
        <meta name="keywords" content="site, keywords" />
        <meta name="robots" content="index, follow" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="css/style.css" />
        <title>FSNet</title>
        <script type="text/JavaScript" src="maquette.js">
	
        </script>
    </head>
    <body onload="showMenu();">
        <div class="wrap background"><jsp:include page="haut.jsp" />

            <div id="left">
                <h2>
                    <a href="#">&#233;v&#233;nements</a>
                </h2>
                ${info}
                <table width="445">
                    <tr>
                        <th style="width: 181px" scope="col">listes des &#233;v&#233;nements</th>
                        <th style="width: 252px" scope="col">
                            <div style="text-align: center">
                                <a href="creerevenement.jsp">publier un  &#233;v&#233;nement</a>
                            </div>
                        </th>
                    </tr>
                </table>
                <p>&nbsp;</p>
                <table width="433">
                    <fsnet:manifestation var="maManif">
                        <table>
                            <tr>
                                <th width ="150">
                                    <a href="AddEvenement?id=${maManif.id}">${maManif.nom}  </a>
                                </th>
                                <th>rendez-vous le</th>
                                <th width ="20">${maManif.dateManif}</th>
                                <th>
                                    <c:if test="${maManif.createur.id == entite.id}">
                                        <a href="SuppEven?idEven=${maManif.id}&idEntite=${entite.id}">
                                            <img src="images/croix.jpg" width="15"/>
                                        </a>
                                        <a href="ModifEven1?idEven=${maManif.id}">
                                            <img src="images/crayon.jpeg" width="12"/>
                                        </a>
                                    </c:if>
                                </th>
                            </tr>
                        </table>
                    </fsnet:manifestation>
                </table>
            </div>
        </div>
        <jsp:include page="bas.jsp" />
    </body>
</html>