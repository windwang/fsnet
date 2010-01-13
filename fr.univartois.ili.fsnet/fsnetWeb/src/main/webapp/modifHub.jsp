<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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

        <script type="text/JavaScript">
            function MM_jumpMenu(targ, selObj, restore) { //v3.0
                eval(targ + ".location='" + selObj.options[selObj.selectedIndex].value
                    + "'");
                if (restore)
                    selObj.selectedIndex = 0;
            }
	
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/JavaScript" src="maquette.js">
        </script>
    </head>
    <body onload="showMenu();MM_jumpMenu();">
        <div class="wrap background"><jsp:include page="haut.jsp"></jsp:include>

            <table width="100%">
                <thead>
                    <tr style="text-align:center;background-color: #CCCCCC">
                        <td class="thead">
                            <div style="text-align: center">Hub</div>
                        </td>
                        <td class="thead">
                            Createur
                        </td>
                        <td class="thead">Dernier message</td>
                        <td class="thead">Topics</td>
                        <td class="thead">Messages</td>
                    </tr>
                </thead>
                <fsnet:hub var="hubdto">
                    <tbody>
                        <tr>
                            <td class="alt1active" id="h${hubdto.hub.id}"
                                style="text-align: left">
                                <table style="padding: 0; border: 0">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <img src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_272.png"
                                                     alt="" style="width: 48px; height: 48px; border: 0"
                                                     id="forum_statusicon_${hubdto.hub.id}" />
                                            </td>
                                            <td>
                                                <img alt="" src="Général Java - Forum des développeurs_fichiers/clear.gif"
                                                     style="width: 9; border: 0; height: 1" />
                                            </td>
                                            <td>
                                                <c:if test="${hubdto.hub.id == monHub.id}">
                                                    <form action="ModifHub">
                                                        <input type="text" name="titreHub" value="${hubdto.hub.nomCommunaute}" />
                                                        <input type=submit name="modifier" value="modifier" />
                                                    </form>
                                                </c:if>
                                                <c:if test="${hubdto.hub.id != monHub.id}">
                                                    <div>
                                                        <a href="GotoTopic?idHub=${hubdto.hub.id}">
                                                            <strong>${hubdto.hub.nomCommunaute}</strong>
                                                        </a>
                                                    </div>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td class="alt2">
                                ${hubdto.hub.createur.nom} ${hubdto.hub.createur.prenom}
                            </td>
                            <td class="alt2">
                                <a href="GotoMessage?idTopic=${hubdto.lastMessage.topic.id }">
                                    ${hubdto.lastMessage.dateMessage} - ${hubdto.lastMessage.propMsg.nom} ${hubdto.lastMessage.propMsg.prenom }
                                </a>
                            </td>
                            <td class="alt1">${hubdto.nbTopic}</td>
                            <td class="alt2">${hubdto.nbMessage}</td>
                        </tr>
                    </tbody>
                </fsnet:hub>
            </table>
             </div>
            <jsp:include page="bas.jsp"></jsp:include>
    </body>
</html>