<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
    <img id="close"
         src="icons/close.png"
         alt="Fermer la fenêtre"
         title="Fermer la fenêtre"
         onclick="hide('side');"/>
</div>

<div class="boxtop"></div>

<div class="box">
    <admin:inscription var="member" entite="${param.ent}">
        <h3>Détails</h3>

        <span class="item">
            <span class="sidedate">
                Email<br />
            </span>
            <strong>${member.entite.email}</strong>
            <br />

            <c:if test="${member.etat eq 'Inscrit'}">
                <span class="sidedate">Sexe<br /></span>
                <strong>${member.entite.sexe}</strong><br />
                <span class="sidedate">Date d'entrée<br /></span>
                <strong>${member.entite.dateEntree}</strong><br />
                <span class="sidedate">Date de naissance<br /></span>
                <strong>${member.entite.dateNaissance}</strong><br />
                <span class="sidedate">Adresse<br /></span>
                <strong>${member.entite.adresse}</strong><br />
                <span class="sidedate">Tel<br /></span>
                <strong>${member.entite.numTel}</strong><br />
                <span class="sidedate">Profession<br /></span>
                <strong>${member.entite.profession}</strong><br />
            </c:if>
        </span>
    </admin:inscription></div>

<div class="boxbottom"></div>