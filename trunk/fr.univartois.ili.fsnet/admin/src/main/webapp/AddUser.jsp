<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>    
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html xhtml="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="author" content="Luka Cvrk - www.solucija.com" />
        <meta name="description" content="Site Description" />
        <meta name="keywords" content="site, keywords" />
        <meta name="robots" content="index, follow" />
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <title>FSNet</title>
        <script type="text/javascript" src="admin.js">
        </script>        
    </head>
    <body onload="showMenu();${param.showHide}('listToDeploy');">
        <jsp:include page="header.jsp"></jsp:include>
        <div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>

            <div id="left">
                <h2><a href="AddUser.jsp?user=current&amp;showHide=hide&amp;deploy=[%2B]&amp;titleDeploy=D%E9ployer la liste"
                       title="Ajout de membre">Ajout de membre </a>
                </h2>
                <jsp:include page="date.jsp"></jsp:include>
            </div>
            <div id="tableauprincipal">
                <table width="100%">
                    <tr>
                        <td>
                            <html:form  method="post" action="/DeleteUserFromAddUser">
                                <table>
                                    <tr>
                                        <th class="entete" colspan="4" scope="col">
                                            <input type="hidden" name="redirection" value="AddUser.jsp"/>
                                            <h2><img src="icons/icon_from_jimmac_musichall_cz_223.png" alt="logo"/>
                                                <span>
                                                    <a id="deployButton" href="#" title="${param.titleDeploy}"
                                                       onclick="deploy('deployButton','listToDeploy','selectedUsers','allUsers');">${param.deploy}</a>
                                                    Liste des membres
                                                </span>
                                            </h2>
                                        </th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table id="listToDeploy">
                                                <tr class="champ">
                                                    <th>Supprimer
                                                        <input id="allUsers" type="checkbox"
                                                               name="allUsers" title="Tout supprimer"
                                                               onclick="selectAll('allUsers','userSelected');showHideButton('removeButton','userSelected');" /></th>
                                                    <th scope="row">Nom</th>
                                                    <th scope="row">Prénom</th>
                                                    <th scope="row">Email</th>
                                                    <th scope="row">Détails</th>
                                                    <th scope="row">Etat</th>
                                                </tr>
                                                <admin:inscription var="inscription">
                                                    <tr>
                                                        <td>
                                                        	<html:multibox 
                                                        		property="selectedUsers"
                                                                value="${inscription.entite.id}"
                                                                onclick="showHideButton('removeButton','selectedUsers');" />
                                                        </td>
                                                        <td title="${inscription.entite.nom}">${svarNom}</td>
                                                        <td title="${inscription.entite.prenom}">${svarPrenom}</td>
                                                        <td title="${inscription.entite.email}">${svarEmail}</td>
                                                        <td>
                                                        	<a href="#"
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
                                <div>
                                    <label for="removeButton">
                                        <input id="removeButton"
                                               onclick="if (!confirm('Etes-vous sûr de vouloir supprimer?')) return false;"
                                               type="submit"
                                               value="Supprimer"
                                               title="Supprimer" />
                                    </label>
                                </div>
                            </html:form>
                            <div id="AddUser">
                                <html:form  action="/adduser.do" method="post">
                                    <table>
                                        <tr>
                                            <td colspan="2">
                                                <html:errors name="entitie.alreadyExists" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="entete" colspan="2" scope="col">
                                                <h2>Ajouter un membre</h2>
                                            </th>
                                        </tr>

                                        <tr class="champ">
                                            <th scope="row"><label for="nom">Nom</label></th>
                                            <td><html:text errorStyleClass="error" property="nom" styleId="nom"/>
                                                <html:errors property="nom" />
                                            </td>
                                        </tr>
                                        <tr class="champ">
                                            <th scope="row"> <label for="prenom">Prénom</label></th>
                                            <td><html:text errorStyleClass="error" property="prenom" styleId="prenom" />
                                                <html:errors property="prenom" />  </td>
                                        </tr>
                                        <tr class="champ">
                                            <th scope="row"><label for="email">Email</label></th>
                                            <td><html:text errorStyleClass="error" property="email"  styleId="email"/>
                                                <html:errors property="email" />   </td>
                                        </tr>
                                    </table>
                                    <div class="button">
                                        <html:submit title="Enregistrer" >Enregistrer</html:submit>
                                    </div>
                                </html:form>
                            </div>
                        </td>

                    </tr>

                </table>
            </div>
            <div id="side"></div>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
        <html:javascript formName="/adduser"/>
    </body>
</html:html>