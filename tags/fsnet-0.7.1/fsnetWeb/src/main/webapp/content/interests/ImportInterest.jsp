<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<c:if test="${not empty KEY_FACEBOOK}">
<h3>
    <bean:message key="interests.19"/>
</h3>
<div id="fb-root"></div>
	<script src="http://connect.facebook.net/fr_FR/all.js"></script>
	<script src="js/facebookInterest.js"></script>
	<script type="text/javascript">
		initFacebook(${KEY_FACEBOOK});
	</script>
<div class="box">
	<div id="social_networks">
	<div id="facebook_button_box">
		<fb:login-button show-faces="false" width="450" perms="user_interests"></fb:login-button>
	</div>
</div>
<div id="social_networks_profiles">
	<div id="facebook_profile" style="display:none;">
		<div id="facebook_profile_text">
			
		</div>
		<div id="facebook_interests">
		
		</div>
	</div>
	<div class="clear"></div>
</div>
</div>
</c:if>