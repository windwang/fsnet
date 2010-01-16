<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%> 
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html:html xhtml="true">
    <head>
        <title>FSNET - Rapport d'activité</title>
        <link rel="icon" type="image/png" href="images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="refresh" content="300;RecupInfo"/>
        <meta name="author" content="Luka Cvrk - www.solucija.com" />
        <meta name="robots" content="index, follow" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <script type="text/javascript" src="admin.js"></script>
    </head>
    <body onload="showMenu();">
        <jsp:include page="header.jsp"></jsp:include>
        <div class="wrap background">
            <jsp:include page="subHeader.jsp"></jsp:include>

            <div id="left">
                <h2><a href="RecupInfo" title="Rapport d'activités">Rapport d'activités</a>
                </h2>
                <jsp:include page="date.jsp"></jsp:include>
            </div>
			<admin:activityReport>
            	<div id="tableauprincipal">
	                <table>
    	                <tr>
        	                <td>Nombre de personnes inscrites</td>
            	            <td>${nbInscrit}</td>
                	    </tr>
		                <tr>
        	                <td>Nombre total d'annonces</td>
            	            <td>${nbAnnoncesTot}</td>
                	    </tr>
	                    <tr>
    	                    <td>Nombre d'annonces en ligne</td>
        	                <td>${nbAnnoncesPub}</td>
            	        </tr>
                	    <tr>
                    	    <td>Nombre total de manifestations</td>
                        	<td>${nbEvenementTot}</td>
	                    </tr>
    	                <tr>
        		                <td>Nombre de manifestations en ligne</td>
                	        <td>${nbEvenementPub}</td>
                    	</tr>
                    	<tr>
	                        <td>Nombre total de hubs/forums</td>
    	                    <td>${nbHubTot}</td>
        	            </tr>
            	        <tr>
                	    	<td><strong>Liste des hubs</strong></td>
                    	</tr>
	                    <tr>
    	                    <td>
        	                	<admin:hub var="hub">
 									Pour le hub " ${hub.nomCommunaute } ",  il y a 
 										<admin:collectionSize collection="${hub.lesTopics}"/> 
 									topic(s) :<br/>
 									<ul>
                	                <admin:topic var="topic" hub="${hub}">
	 									<li>
	 										Topic " ${topic.sujet } " contenant 
	 										<admin:collectionSize collection="${topic.lesMessages}"/>  
	 										message(s)
	 									</li>
                        	        </admin:topic>
                        	        </ul>
                            	</admin:hub>
	                        </td>
    	                </tr>
        	        </table>
            	</div>
            </admin:activityReport>
            <div id="side"></div>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html:html>