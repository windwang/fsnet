<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>

<div id="logo">
    <h1>
        <a href="index.jsp?accueil=current" title="Accueil">
            FSNet<br />
        </a>
    </h1>
    <h2 class="slogan">Réseau social</h2>
    <h2 class="slogan">Administration</h2>
</div>

<div id="features">
    <ul id="feature_menu">
        <li>
            <a class="current" href="#">Actualité</a>
        </li>
    </ul>

    <div id="feature">
        <img src="images/feature_img.gif" alt="Featured" />
        <h3>Les derniers inscrits</h3>
        <ul>
            <admin:inscription var="inscription" etat="Inscrit">
                <li>
                    <a  href="#"
                        onclick="recupPage('DetailsMember.jsp','ent','${inscription.entite.id}','side');show('side');"
                        title="Cliquez pour afficher les détails de ${inscription.entite.nom} ${inscription.entite.prenom} ">
                        ${svarNom} ${svarPrenom}
                    </a>
                </li>
            </admin:inscription>
        </ul>
    </div>
</div>

<div class="clear"></div>
