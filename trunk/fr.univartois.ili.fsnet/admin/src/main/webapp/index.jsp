<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type"	content="application/xhtml+xml; charset=UTF-8"/>
        <meta http-equiv="refresh" content="300;index.jsp?accueil=current"/>
        <meta name="author" content="Luka Cvrk - www.solucija.com" />
        <meta name="description" content="Site Description" />
        <meta name="keywords" content="site, keywords" />
        <meta name="robots" content="index, follow" />
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <title>FSNet</title>
        <script type="text/javascript" src="admin.js"></script>
    </head>
    <body onload="showMenu();">

        <jsp:include page="header.jsp"/>

        <div class="wrap background">

            <jsp:include page="subHeader.jsp"/>

            <div id="left">
                <h2>
                    <a href="index.jsp?accueil=current" title="Accueil">Accueil</a>
                </h2>
                <jsp:include page="date.jsp"/>
            </div>

            <div id="tableauprincipal">
                <table>
                    <tr>
                        <th class="entete" colspan="4" scope="col">
                            <h2 title="Liste des membres en attente d'inscription">
                                <img src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_223.png" alt="fsnet" />
                                <span>En attente d'inscription</span>
                            </h2>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <table id="listToDeploy">
                                <tr class="champ">
                                    <th scope="row">Nom</th>
                                    <th scope="row">Prénom</th>
                                    <th scope="row">Email</th>
                                    <th scope="row">Détails</th>
                                    <th scope="row">Etat</th>
                                </tr>

                                <admin:inscription var="inscription" etat="En attente d'inscription">

                                    <tr class="details">
                                        <td title="${inscription.entite.nom}">${svarNom}</td>
                                        <td title="${inscription.entite.prenom}">${svarPrenom}</td>
                                        <td title="${inscription.entite.email}">${svarEmail}</td>
                                        <td>
                                            <a  href="#"
                                                onclick="recupPage('MemberDetails.jsp','ent','${inscription.entite.id}','side');show('side');"
                                                title="Cliquez pour afficher les détails de ${inscription.entite.nom} ${inscription.entite.prenom}">
                                                Détails
                                            </a>
                                        </td>
                                        <td title="${inscription.etat}">${inscription.etat}</td>
                                    </tr>

                                </admin:inscription>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="side"></div>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>