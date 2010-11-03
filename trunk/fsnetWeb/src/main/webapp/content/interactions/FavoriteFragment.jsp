<%-- 
    Document   : FavoriteFragment
    Created on : 11 févr. 2010, 15:24:57
    Author     : Matthieu Proucelle <matthieu.proucelle at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script type="text/javascript">

    if (! isFavorite) { 
    	var isFavorite = new Array();
    } 
    
    isFavorite['${InteractionId}'] = ${isFavorite};
    
</script>

<img class="favorite${interactionId}"
     src="images/${isFavorite ? 'favorite.png' : 'non-favorite.png'}"
     alt="Favorite"
     onclick="switchFavorite('${interactionId}');"
     onmouseover="this.style.cursor='pointer'"
     style="vertical-align: middle"/>
     
     