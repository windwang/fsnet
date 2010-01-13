<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
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
        <div class="wrap background">
            <jsp:include page="haut.jsp"></jsp:include>

            <div id="left">
                <h2><a href="#">Annonces</a></h2>
                <p>&nbsp;</p>
                <table width="433">
                    <fsnet:annonce var="monAnnonce" idChoisi="${idChoisi}">
                        <tr>
                            <th width="80"><u>titre : </u></th>
                            <td>${monAnnonce.nom}</td>
                        </tr>
                        <tr>
                            <th><u>contenu :</u></th><td> ${monAnnonce.contenu}</td>
                        </tr>
                        <tr>
                            <th><u>Date de fin :</u> </th><td>${monAnnonce.dateAnnonce}</td>
                        </tr>
                    </fsnet:annonce>
                </table>
                <table width="433">
                    <th  scope="col">
                        <div style="text-align: center">
                            <a href="annonces.jsp">retour aux annonces</a>
                        </div>
                    </th>
                </table>
                <p>&nbsp;</p>
            </div>
        </div>
        <jsp:include page="bas.jsp"></jsp:include>
    </body>
</html>