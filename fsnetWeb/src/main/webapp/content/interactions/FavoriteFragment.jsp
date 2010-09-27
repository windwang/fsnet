<%-- 
    Document   : FavoriteFragment
    Created on : 11 févr. 2010, 15:24:57
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script type="text/javascript">

	function getElementsByClass(tag, class){
		var elements = document.getElementsByTagName(tag);
		var results = new Array();
		for(var i=0; i<elements.length; i++){
			if(elements[i].className == class){
				results[results.length] = elements[i];
			}
		}
		return results;
	}

    if(!isFavorite) var isFavorite = new Array();
    
    isFavorite[${interactionId}] = ${isFavorite};

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
        else xhr = null;
        return xhr;
    }

    function switchFavorite(id){
        var xhr = getXhr();
        xhr.onreadystatechange = function() {
            if(xhr.readyState == 4 && xhr.status == 200) {
                isFavorite[id] = !isFavorite[id];
                var favoriteImages = getElementsByClass('img', 'favorite'+id);
                for (var i = 0 ; i< favoriteImages.length ; i++) {
                	favoriteImages[i].src = "images/"+(isFavorite[id] ? "favorite.png" : "non-favorite.png");
                }
            }
        }
        if(isFavorite[id]){
            xhr.open("POST",'RemoveFavorite.do',true);
            xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
            xhr.send("interactionId="+id);
        }else {
            xhr.open("POST",'AddFavorite.do',true);
            xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
            xhr.send("interactionId="+id);
        }
    }

</script>

<img class="favorite${interactionId}"
     src="images/${isFavorite ? 'favorite.png' : 'non-favorite.png'}"
     alt="Favorite"
     onclick="switchFavorite(${interactionId});"
     onmouseover="this.style.cursor='pointer'"
     style="vertical-align: middle"/>