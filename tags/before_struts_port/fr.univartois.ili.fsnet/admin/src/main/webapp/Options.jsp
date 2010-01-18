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
            <html:javascript formName="/ModifyOptions"/>
            <div id="tableauprincipal" >
                <p id="informationsOptions">
                	Nb:Ce formulaire permet de configurer vos préférences pour 
                	l'envoie de mails. Par exemple: quand vous enregistrer un membre un 
                	mail lui ait automatiquement envoyé afin qu'il puisse finaliser son inscription.
                </p>

                <html:form action="/ModifyOptions.do">
                    <table>
                        <tr>
                            <td colspan="2">
                                <html:errors property="options.errors" />
                            </td>
                        </tr>
                        <tr>
                            <th class="entete" colspan="2" scope="col">
                                <h2>Configuration</h2>

                            </th>
                        </tr>

                        <tr class="champ">
                            <th scope="row"><label for="serveurSMTP">serveur SMTP : </label></th>
                            <td><html:text property="serveursmtp" errorStyleClass="error" styleId="serveurSMTP"/>
                                <html:errors property="serveursmtp" />
                            </td>
                        </tr>
                        <tr class="champ">
                            <th scope="row"> <label for="hote">Expéditeur : </label></th>
                            <td><html:text property="hote" errorStyleClass="error" styleId="hote" />
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
                    <div class="button">
                        <html:submit title="Enregistrer">Enregistrer</html:submit>
                    </div>
                </html:form>
            </div>
            <div id="side"></div>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html:html>