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
            xhr = null;
        }
        return xhr;
    }

    function switchFavorite(){
        var div = document.getElementById("debug");
        div.innerHTML += "<br/>";
        div.innerHTML += "favorite = "+isFavorite+" <br/>";
        var xhr = getXhr();
        xhr.onreadystatechange = function() {
            div.innerHTML += "changing state = "+xhr.readyState;
            div.innerHTML += " status = "+xhr.status+" <br/>";
            if(xhr.readyState == 4 && xhr.status == 500) {
                div.innerHTML += "blem : "+xhr.responseText+" <br/>";
            }
            if(xhr.readyState == 4 && xhr.status == 200) {
                isFavorite = !isFavorite;
                div.innerHTML += "over : "+isFavorite+" <br/>";
                document.getElementById('favorite'+interactionId).src="images/"+(isFavorite ? "favorite.png" : "non-favorite.png");
            }
        }
        if(isFavorite){
            xhr.open("POST",'RemoveFavorite.do',true);
            xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
            xhr.send("interactionId="+interactionId);
            div.innerHTML += "called : RemoveFavorite.do<br/>";
        }else {
            xhr.open("POST",'AddFavorite.do',true);
            xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
            xhr.send("interactionId="+interactionId);
            div.innerHTML += "called : AddFavorite.do<br/>";
        }
    }

</script>

<img id="favorite${interactionId}" src="images/${isFavorite ? "favorite.png" : "non-favorite.png"}" alt="Favorite" onclick="switchFavorite();" onmouseover="this.style.cursor='pointer'"/>
<div id="debug">

</div>