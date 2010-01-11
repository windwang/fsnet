<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>    
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<html:html>
    <head>
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh"
              content="300;AddUser.jsp?user=current&showHide=hide&deploy=[%2B]&titleDeploy=D%E9ployer la liste">
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
    <body onload="showMenu();${param.showHide}('listToDeploy');">

        <jsp:include page="header.jsp"></jsp:include>
        <div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>

            <div id="left">
                <h2><a href="AddUser.jsp?user=current&showHide=hide&deploy=[%2B]&titleDeploy=D%E9ployer la liste"
                       title="Ajout de membre">Ajout de membre </a>
                </h2>
                <jsp:include page="date.jsp"></jsp:include>
            </div>
            <html:javascript formName="/adduser"/>
            <div id="tableauprincipal">
                <table width="100%">
                    <tr>
                        <td>
                            <form id="RemoveUser" method="post" action="RemoveUser">
                                <input type="hidden" name="redirection" value="AddUser.jsp">
                                <table>
                                    <tr>
                                        <th class="entete" colspan="4" scope="col">
                                            <h2><img src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_223.png" />
                                                <span>
                                                    <a id="deployButton" href="#" title="${param.titleDeploy}"
                                                       onclick="deploy('deployButton','listToDeploy','userSelected','allUsers');">${param.deploy}</a>
                                                    Liste des membres
                                                </span>
                                            </h2>
                                        </th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table id="listToDeploy">
                                                <tr class="champ">
                                                    <th>Supprimer<input id="allUsers" type="checkbox"
                                                                        name="allUsers" title="Tout supprimer"
                                                                        onclick="selectAll('allUsers','userSelected');showHideButton('removeButton','userSelected');" /></th>
                                                    <th width="20%" scope="row">Nom</th>
                                                    <th width="20%" scope="row">Prénom</th>
                                                    <th width="20%" scope="row">Email</th>
                                                    <th width="20%" scope="row">Détails</th>
                                                    <th width="20%" scope="row">Etat</th>
                                                </tr>
                                                <admin:inscription var="inscription">
                                                    <tr>
                                                        <td><input type="checkbox" name="userSelected"
                                                                   value="${inscription.entite.id}" title="Supprimer"
                                                                   onclick="showHideButton('removeButton','userSelected');" /></td>
                                                        <td width="20%" title="${inscription.entite.nom}">${svarNom}</td>
                                                        <td width="20%" title="${inscription.entite.prenom}">${svarPrenom}</td>
                                                        <td width="20%" title="${inscription.entite.email}">${svarEmail}</td>
                                                        <td width="20%"><a href="#"
                                                                           onclick="recupPage('MemberDetails.jsp','ent','${inscription.entite.id}','side');show('side');"
                                                                           title="Cliquez pour afficher les détails de ${inscription.entite.nom} ${inscription.entite.prenom}">Détails</a></td>
                                                        <td width="20%" title="${inscription.etat}">${inscription.etat}</td>
                                                    </tr>
                                                </admin:inscription>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <label id="removeButton">
                                    <input onclick="if (!confirm('Etes-vous sûr de vouloir supprimer?')) return false;"
                                           type="submit" value="Supprimer" title="Supprimer" /></label>
                            </form>

                            <html:form styleId="AddUser" action="/adduser.do" method="post">
                                <!-- <form id="AddUser" method="post" action="AddUser">-->
                                <html:hidden property="redirection" value="AddUser.jsp" />
                                <table>
                                    <tr>
                                        <th class="entete"></th>
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
                                <label class="button">
                                    <html:submit title="Enregistrer" >Enregistrer</html:submit>
                                    <!-- <input type="submit" name="Submit"
			value="Enregistrer" title="Enregistrer" />-->
                                </label>
                            </html:form>
                        </td>

                    </tr>

                </table>
            </div>
            <div id="side"></div>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html:html>