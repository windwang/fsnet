<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%> 
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html:html xhtml="true"> 
    <head>
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="refresh"
              content="300;AddInterest.jsp?interet=current&amp;showHide=hide&amp;deploy=[%2B]&amp;titleDeploy=D%E9ployer la liste"/>
        <meta name="author" content="Luka Cvrk - www.solucija.com" />
        <meta name="description" content="Site Description" />
        <meta name="keywords" content="site, keywords" />
        <meta name="robots" content="index, follow" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="css/style.css" />
        <title>FSNet</title>
        <script type="text/javaScript" src="admin.js">
        </script>
        <script type="text/javascript">
        </script>
    </head>
    <body onload="showMenu();${param.showHide}('listToDeploy');">

        <jsp:include page="header.jsp"></jsp:include>

        <div class="wrap background">
            <jsp:include page="subHeader.jsp"></jsp:include>

            <div id="left">
                <h2>
                    <a href="AddInterest.jsp?interet=current&amp;showHide=hide&amp;deploy=[%2B]&amp;titleDeploy=D%E9ployer la liste"
                       title="Ajout d'intérêts">Ajout d'int&eacute;r&ecirc;ts </a>
                </h2>
                <jsp:include page="date.jsp"></jsp:include>
            </div>

            <html:javascript formName="/addinterest"/>

            <div id="tableauprincipal">
                <table width="100%">
                    <tr>
                        <td>
                            <form id="RemoveInterest" method="post" action="RemoveInterest">
                                <table>
                                    <tr>
                                        <th class="entete" colspan="2" scope="col">
                                            <input type="hidden" name="redirection" value="AddInterest.jsp"/>
                                            <h2>
                                                <img src="icons/icon_from_jimmac_musichall_cz_073.png" alt="logo"/>
                                                <span>
                                                    <a id="deployButton" href="#" title="${param.titleDeploy}" onclick="deploy('deployButton','listToDeploy','interestSelected','allInterests');">${param.deploy}</a>
                                                    Liste des interêts
                                                </span>
                                            </h2>
                                        </th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table id="listToDeploy">
                                                <tr class="champ">
                                                    <th>Supprimer<input id="allInterests" type="checkbox"
                                                                        name="allInterests" title="Tout supprimer"
                                                                        onclick="selectAll('allInterests','interestSelected');showHideButton('removeButton','interestSelected');" /></th>
                                                    <th scope="row">Intitulé</th>
                                                </tr>
                                                <admin:interet var="interet">
                                                    <tr>
                                                        <td><input type="checkbox" name="interestSelected"
                                                                   value="${interet.id}" title="Supprimer"
                                                                   onclick="showHideButton('removeButton','interestSelected');" /></td>
                                                        <td align="center" title="${interet.nomInteret}">${svarInteret}</td>
                                                    </tr>
                                                </admin:interet>
                                            </table>
                                        </td>
                                    </tr>

                                </table>
                                <div id="removeButton">
                                    <input onclick="if (!confirm('Etes-vous sûr de vouloir supprimer?')) return false;"
                                           type="submit" value="Supprimer" title="Supprimer" />
                                </div>
                            </form>

							<div id="AddInterest">
	                            <html:form action="/addinterest.do" method="post">                                
	                                <table>
	                                    <tr>
	                                        <th class="entete">
	                                        	<html:hidden property="redirection" value="AddInterest.jsp" />
	                                        </th>
	                                        <th class="entete" colspan="2" scope="col">
	                                            <h2>Ajouter un intérêt</h2>
	                                        </th>
	                                    </tr>
	                                    <tr class="champ">
	                                        <th scope="row"><label for="intitule">Intitulé</label></th>
	                                        <td colspan="2">
	                                            <html:text errorStyleClass="error" property="nomInteret" styleId="intitule"/>
	                                            <html:errors property="nomInteret" />
	                                        </td>
	                                    </tr>
	
	                                    <tr>
	                                        <td></td>
	                                        <td colspan="2" id="interests"></td>
	                                    </tr>
	
	                                    <tr>
	                                        <td></td>
	                                        <td><input class="addButton" name="inputAddInterest"
	                                                   id="inputAddInterest" type="button" onclick="addInterest()"
	                                                   value="Ajouter" /></td>
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
    </body>
</html:html>