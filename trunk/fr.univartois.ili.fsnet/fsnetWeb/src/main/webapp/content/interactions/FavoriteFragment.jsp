<%-- 
    Document   : FavoriteFragment
    Created on : 11 févr. 2010, 15:24:57
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script type="text/javascript">
    var isFavorite = ${isFavorite};
    var interactionId = ${interactionId};
    function getXhr() {
        var xhr;
        if(window.XMLHttpRequest) xhr = new XMLHttpRequest();
        else if(window.ActiveXObject) {
            try {
                xhr = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
        }
        else {
            alert("Votre navigateur ne supporte pas les objets XMLHTTPRequest, veuillez le mettre à jour");
            xhr = null;
        }
        return xhr;
    }

    function switchFavorite(){

        var xhr = getXhr();

        xhr.onreadystatechange = function() {
            if(xhr.readyState == 4 && xhr.status == 200) {
                isFavorite = !isFavorite;
            }
        }
        if(isFavorite){
            xhr.open("POST",'RemoveFavorite.do',true);
            xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
            //cdcl = document.getElementById('cdcl').value;
            xhr.send("interactionId="+interactionId);
        }else {
            xhr.open("POST",'AddFavorite.do',true);
            xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
            //cdcl = document.getElementById('cdcl').value;
            xhr.send("interactionId="+interactionId);
        }
       
    }

    

</script>

<img id="favorite" src="images/${isFavorite ? "favorite.png" : "non-favorite.png"}" alt="Favorite" onclick="switchFavorite()" onmouseover="this.style.cursor='pointer'"/>
