<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        <script type="text/javascript" src="maquette.js">
        </script>
    </head>
    <body onload="showMenu();">
        <div class="wrap background"><jsp:include page="haut.jsp" />

            <div id="left">


                <h2><a href="#">Annonces</a></h2>
                ${info }
                <table width="445">
                    <tr>
                        <th style="width: 181px" scope="col">listes des annonces</th>
                        <th style="width: 252px" scope="col">
                            <div style="text-align: center">
                                <a href="publierannonce.jsp">publier une annonce</a>
                            </div>
                        </th>
                    </tr>
                </table>
                <p>&nbsp;</p>
                <table width="433">
                    <fsnet:annonce var="monAnnonce">
                        <table>
                            <tr>
                                <th width="30"></th>
                                <th></th>
                                <th></th>
                            </tr>
                            <tr>
                                <td width="150"><a href="AddAnnonce?idChoisi=${monAnnonce.id}">${monAnnonce.nom}</a></td>
                                <td>${monAnnonce.dateAnnonce}</td>
                                <td>
                                    <c:if test="${createur == entite.id}">
                                        <a href="SupprAnnonce?idChoisi=${monAnnonce.id}">
                                            <img src="images/croix.jpg" width="15" />
                                        </a>
                                        <a href="GotoModifAnnonce?idChoisi=${monAnnonce.id}">
                                            <img src="images/crayon.jpeg" width="12" />
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </table>
                    </fsnet:annonce>
                </table>
            </div>
        </div>
        <jsp:include page="bas.jsp" />
    </body>
</html>