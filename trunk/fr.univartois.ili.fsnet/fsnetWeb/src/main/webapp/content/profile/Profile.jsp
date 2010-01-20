

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>


 	<h3>Update Profile</h3>
 	<html:form action="/UpdateProfile">
		<table id="UpdateProfile">
			<tr>
				<th>Name</th>
				<td>
					<html:text errorStyleClass="error" property="name" value="${user.nom}"/>
				</td>
			</tr>
			<tr>
				<th>Firstname</th>
				<td>
					<html:text errorStyleClass="error" property="firstName"  value="${user.prenom}"/>
				</td>
			</tr>
			<tr>
				<th>Adress</th>
				<td>
					<html:textarea errorStyleClass="error" property="adress" value="${user.adresse}"/>
				</td>
			</tr>
			<tr>
				<th>Welcome date</th>
				<td>					
					${user.dateEntree} 				
				</td>
			</tr>
			<tr>
				<th>Birth date (DD/MM/YYYY)</th>
				<td>
					<!-- TODO ajouter un calendrier avec ajax -->
					<html:text errorStyleClass="error" property="dateOfBirth" value="${user.dateNaissance}"/>
				</td>
			</tr>
			<tr>
				<th>Sexe</th>
				<td>
					<!-- TODO refaire avec les constante de entité social -->
					<html:select property="sexe">
						<html:option value="Man">Man</html:option>
						<html:option value="Woman">Woman</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<th>Password</th>
				<td><html:password property="pwd" value="${user.mdp}"/></td>
			</tr>
			<tr>
				<th>Confirm password</th>
				<td><html:password property="confirmPwd" value="${user.mdp}"/></td>
			</tr>
			<tr>
				<th>Job</th>
				<td>
					<html:text errorStyleClass="error" property="job" value="${user.profession}"/>
				</td>
			</tr>
			<tr>
				<th>E-mail</th>
				<td>
					<html:text errorStyleClass="error" property="mail" value="${user.email}"/>
				</td>
			</tr>	
			<tr>
				<th>Phone number</th>
				<td>
					<html:text errorStyleClass="error" property="phone" value="${user.numTel}"/>
				</td>
			</tr>	
			<tr> <td/> 
				<td><html:submit>Validate</html:submit></td>	
			</tr>	
		</table> 

	</html:form>      