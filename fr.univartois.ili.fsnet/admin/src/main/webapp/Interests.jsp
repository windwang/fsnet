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
            	<div id="interestsList">
            		<fieldset>
            			<legend>Interetsts List</legend>
            				<c:set var="i" value="0"/>
							<admin:interet var="interet">
								<c:if test="${i % 7 == 0}">
									<!--<div class="clear"></div>-->
								</c:if>
                				<div class="interest">${interet.nomInteret}</div>
                				<c:set var="i" value="${i + 1}"/>             		
	            			</admin:interet>
            		</fieldset>
            	</div>
            	<fieldset>
            	                    	<legend>Search an interest</legend>
                <html:form action="/searchInterest.do">
                        <div><label for="searchText">Recherche : </label>
                        <html:text errorStyleClass="error" property="searchtext" styleId="searchText"/>
                        <html:submit>Rechercher</html:submit>
                        <div><html:errors property="searchtext" /></div>
                        </div>
                </html:form>
                    
                <html:form action="/RemoveInterest">
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
                                                    onclick="selectAll('allInterests','selectedInterests');showHideButton('removeButton','selectedInterests');" />
                                        </th>
                                        <th scope="row">Intitulé</th>
                                    </tr>
                                    <admin:interet var="interet" parametre="${requestScope.searchtext}">
                                        <tr>
                                            <td>
                                                <html:multibox
                                                        property="selectedInterests"
                                                        value="${interet.id}"
                                                        onclick="showHideButton('removeButton','selectedInterests');" />
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
				</html:form>
				</fieldset>
                  <div id="AddInterest">
                  				<html:errors name="interest.alreadyExists" />
                                <html:form action="/addinterest.do" method="post">
                                	<fieldset>
                                	<legend>Add an interest</legend>
                                    <table>
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
                                    </table>
                                    <div class="button">
                                        <html:submit title="Enregistrer" >Enregistrer</html:submit>
                                    </div>
                                    </fieldset>
                                </html:form>
                            </div>
                     <div>
</div>
            </div>
            <div id="side"></div>
        </div>

        <jsp:include page="footer.jsp"></jsp:include>

    </body>
</html:html>