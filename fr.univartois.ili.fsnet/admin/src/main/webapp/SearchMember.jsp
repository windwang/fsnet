<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>    
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html:html xhtml="true">
    <head>
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <meta http-equiv="content-type"
              content="application/xhtml+xml; charset=UTF-8" />
        <meta http-equiv="refresh" content="300;SearchMember.jsp?user=current&amp;recherche=hide"/>
        <meta name="author" content="Luka Cvrk - www.solucija.com" />
        <meta name="description" content="Site Description" />
        <meta name="keywords" content="site, keywords" />
        <meta name="robots" content="index, follow" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="css/style.css" />
        <title>FSNet</title>
        <script type="text/javascript" src="admin.js">
        </script>
    </head>
    <body onload="showMenu();${param.recherche}('rechercheVide');hide('selectEtat');">
        <jsp:include page="header.jsp"></jsp:include>
        <div class="wrap background">
            <jsp:include page="subHeader.jsp"></jsp:include>

            <div id="left">
                <h2>
                    <a href="SearchMember.jsp?user=current&amp;recherche=hide" title="Rechercher un membre">Rechercher un membre</a>
                </h2>
                <jsp:include page="date.jsp"></jsp:include>
            </div>
            <html:javascript formName="/searchmember"/>

            <div id="tableauprincipal">
                <table style="width: 90%">
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td style="height: 38px"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="height: 2px"></td>
                    </tr>

                    <tr>
                        <td valign="top">
                            <html:form action="/searchmember.do" method="post">
                                <!-- <form id="form1" method="post" action="SearchMember">-->

                                <div>
                                    <label for="searchText">Recherche par :</label>
                                    <select name="selectRecherche" id="selectRecherche">
                                        <option selected="selected" value="nom" >Nom</option>
                                        <option value="prenom">Prénom</option>
                                    </select>
                                    <!-- <label for="searchText">Champs :</label>-->
                                    <html:text errorStyleClass="error" property="searchtext" styleId="searchText"/>
                                    <html:hidden property="redirection" value="SearchMember.jsp"/>
                                    <!-- <label class="button">-->
                                    <html:submit>Rechercher</html:submit>
                                    <br/>
                                    <html:errors property="searchtext" />
                                    <!-- <input type="submit" name="Submit" value="Rechercher" />-->
                                    <!-- </label>-->
                                </div>

                            </html:form>
                            <form id="RemoveUser" method="post" action="RemoveUser">
                            	<div><input type="hidden" name="redirection" value="SearchMember.jsp"/></div>
                                <table id="listToDeploy">
                                    <tr class="champ" id="enteteRecherche">
                                        <th>Supprimer<input id="allUsers" type="checkbox"
                                                            name="allUsers" title="Tout supprimer"
                                                            onclick="selectAll('allUsers','userSelected');showHideButton('removeButton','userSelected');" /></th>
                                        <th scope="row">Nom</th>
                                        <th scope="row">Prénom</th>
                                        <th scope="row">Email</th>
                                        <th scope="row">Détails</th>
                                        <th scope="row">Etat</th>
                                    </tr>
                                    <admin:inscription filtre="${filtre}" var="inscription" parametre="${parametre}"  >
                                        <tr>
                                            <td><input type="checkbox" name="userSelected"
                                                       value="${inscription.entite.id}" title="supprimer"
                                                       onclick="showHideButton('removeButton','userSelected');" /></td>
                                            <td title="${inscription.entite.nom}">${svarNom}</td>
                                            <td title="${inscription.entite.prenom}">${svarPrenom}</td>
                                            <td title="${inscription.entite.email}">${svarEmail}</td>
                                            <td><a href="#"
                                                               onclick="recupPage('MemberDetails.jsp','ent','${inscription.entite.id}','side');"
                                                               title="Cliquez pour afficher les détails de ce membre">Détails</a></td>
                                            <td>${inscription.etat}</td>
                                        </tr>
                                    </admin:inscription>
                                    <c:if test="${vide ne 'nonVide'}">
                                        <tr id="rechercheVide">
                                        	<td colspan="5">Aucun résultat ne correspond à votre recherche !</td>
                                        </tr>
                                    </c:if>

                                </table>
                                <div>
                                	<label for="removeButton">
                                    	<input id="removeButton" onclick="if (!confirm('Etes-vous sûr de vouloir supprimer?')) return false;"
                                        	   type="submit" value="Supprimer" title="Supprimer" />
                                	</label>
                                </div>
                            </form>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="side"></div>
        </div>

        <jsp:include page="footer.jsp"></jsp:include>
    </body>   
</html:html>