<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>    
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<html>
    <head>
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="300;options.jsp?option=current">
        <meta name="author" content="Luka Cvrk - www.solucija.com" />
        <meta name="description" content="Site Description" />
        <meta name="keywords" content="site, keywords" />
        <meta name="robots" content="index, follow" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="css/style.css" />
        <title>FSNet : Options</title>
        <script type="text/javascript" src="admin.js">
        </script>
        <script type="text/javascript">
        </script>
    </head>

    <body onload="showMenu();${param.showHide}('listToDeploy');${param.recherche}('rechercheVide')">
        <div class="wrap background">
            <div style="margin-top: 10%"id="logo">
                <h1><a href="#">FSNet<br /></a></h1>
                <h2 class="slogan">Réseau social</h2>
                <h2 class="slogan">Administration</h2>
            </div>
        </div>

        <div class="clear"></div>
        <div id="center">
            <h2><a href="optionsrequired.jsp?option=current"
                   title="configuration des options d'envoie de mail">Options</a>
            </h2>
            <jsp:include page="date.jsp"></jsp:include>
        </div>

        <html:javascript formName="/lesoptionsrequired"/>
        <div id="tableauprincipal" style="padding-left:10%">
            <p id="informationsOptions">Nb:Ce formulaire permet de configurer vos préférences pour l'envoie de mails. Par exemple: quand vous enregistrer un membre un mail lui ait automatiquement envoyé afin qu'il puisse finaliser son inscription.</p>

            <html:form action="/lesoptionsrequired.do" method="post">
                <table>
                    <tr>
                        <th class="entete"></th>
                        <th class="entete" colspan="2" scope="col">
                            <h2>Configuration</h2>
                        </th>
                    </tr>

                    <tr class="champ">
                        <th scope="row"><label for="serveurSMTP">serveur SMTP : </label></th>
                        <td><html:text property="serveursmtp" errorStyleClass="error" styleId="serveurSMTP" />
                            <html:errors property="serveursmtp" />
                        </td>
                    </tr>
                    <tr class="champ">
                        <th scope="row"> <label for="hote">Expéditeur : </label></th>
                        <td><html:text property="hote" errorStyleClass="error" styleId="hote"  />
                            <html:errors property="hote" />
                        </td>
                    </tr>
                    <tr class="champ">
                        <th scope="row"><label for="pass">Mot de passe : </label></th>
                        <td><html:password property="motdepasse" errorStyleClass="error" styleId="pass" />
                            <html:errors property="motdepasse" />
                        </td>
                    </tr>
                    <tr class="champ">
                        <th scope="row"><label for="adresseFSNet">Adresse site FSNet : </label></th>
                        <td><html:text property="adressefsnet" errorStyleClass="error" styleId="adresseFSNet" />
                            <html:errors property="adressefsnet" />
                        </td>
                    </tr>
                    <tr class="champ">
                        <th scope="row"><label for="port">Port : </label></th>
                        <td><html:text property="port" errorStyleClass="error" styleId="port" />
                            <html:errors property="port" />
                        </td>
                    </tr>
                </table>
                <label class="button">
                    <html:submit title="Enregistrer" >Enregistrer</html:submit>
                    <!-- <input type="submit" name="Submit"
			value="Enregistrer" title="Enregistrer" />-->
                </label>
            </html:form>
        </div>

        <jsp:include page="footer.jsp"></jsp:include>

    </body>
</html>