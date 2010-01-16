<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>    
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html:html xhtml="true">
    <head>
        <title>FSNet</title>
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="refresh" content="300;SearchInterest.jsp?interet=current&amp;recherche=hide"/>
        <meta name="author" content="Luka Cvrk - www.solucija.com" />
        <meta name="description" content="Site Description" />
        <meta name="keywords" content="site, keywords" />
        <meta name="robots" content="index, follow" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <script type="text/javascript" src="admin.js"></script>
    </head>
    <body onload="showMenu();${param.showHide}('listToDeploy');${param.recherche}('rechercheVide')">
        <jsp:include page="header.jsp"></jsp:include>

        <div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>

            <div id="left">
                <h2>
                    <a href="SearchInterest.jsp?interet=current&amp;recherche=hide"
                       title="Recherche d'intérêts">Recherche d'int&eacute;r&ecirc;ts </a>
                </h2>
                <jsp:include page="date.jsp"></jsp:include>
            </div>

            <html:javascript formName="/searchInterest"/>

            <div id="tableauprincipal">

                <html:form action="/searchInterest.do">
                    <div>
                        <label for="searchText">Recherche : </label>
                        <html:text errorStyleClass="error" property="searchtext" styleId="searchText"/>
                        <html:submit>Rechercher</html:submit>
                        <div><html:errors property="searchtext" /></div>
                    </div>
                </html:form>
                <form id="RemoveInterest" method="post" action="RemoveInterest.do">
                    <table>
                        <tr>
                            <td>
                                <input type="hidden" name="redirection" value="SearchInterest.jsp"/>
                                <table id="listToDeploy">
                                    <tr class="champ">
                                        <th>Supprimer
                                            <input  id="allInterests"
                                                    type="checkbox"
                                                    name="allInterests"
                                                    title="Tout supprimer"
                                                    onclick="selectAll('allInterests','interestSelected');showHideButton('removeButton','interestSelected');" />
                                        </th>
                                        <th scope="row">Intitulé</th>
                                    </tr>
                                    <admin:interet var="interet" parametre="${requestScope.searchtext}">
                                        <tr>
                                            <td>
                                                <input  type="checkbox"
                                                        name="interestSelected"
                                                        value="${interet.id}"
                                                        title="Supprimer"
                                                        onclick="showHideButton('removeButton','interestSelected');" />
                                            </td>
                                            <td align="center"
                                                title="${interet.nomInteret}">
                                                ${svarInteret}
                                            </td>
                                        </tr>
                                    </admin:interet>
                                    <c:if test="${vide ne 'nonVide'}">
                                        <tr>
                                            <td id="rechercheVide">
                                      	   				Aucun résultat ne correspond à votre recherche !!
                                            </td>
                                        </tr>
                                    </c:if>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <div id="removeButton">
                        <input onclick="if (!confirm('Etes-vous sûr de vouloir supprimer?')) return false;"
                               type="submit"
                               value="Supprimer"
                               title="Supprimer" />
                    </div>
                </form>

            </div>
            <div id="side"></div>
        </div>

        <jsp:include page="footer.jsp"></jsp:include>

    </body>
</html:html>