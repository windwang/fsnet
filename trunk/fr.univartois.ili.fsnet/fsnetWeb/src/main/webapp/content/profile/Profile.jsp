

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>


 <div>
 	<html:form action="/UpdateProfile">
		<table>
			<tr>
				<th>Name</th>
				<td>
					<html:text errorStyleClass="error" property="name">
						<!-- TODO nom de l'utilisateur ${ XXX.nom } -->
					</html:text>
				</td>
			</tr>
			<tr>
				<th>Firstname</th>
				<td>
					<html:text errorStyleClass="error" property="firstname">
						<!-- TODO prenom de l'utilisateur ${ XXX.prenom } -->
					</html:text>
				</td>
			</tr>
			<tr>
				<th>Adress</th>
				<td>
					<html:textarea errorStyleClass="error" property="adress">
						<!-- TODO adresse de l'utilisateur ${ XXX.adresse } -->
					</html:textarea>
				</td>
			</tr>
			<tr>
				<th>Welcome date</th>
				<td>
					
						<!-- TODO date d'inscription de l'utilisateur ${ XXX.dateEntree } -->
					
				</td>
			</tr>
			<tr>
				<th>Birth date (DD/MM/YYYY)</th>
				<td>
					<!-- TODO ajouter un calendrier avec ajax -->
					<html:text errorStyleClass="error" property="dateOfBirth">
						<!-- TODO date de naissance de l'utilisateur ${ XXX.dateNaissance } -->
					</html:text>
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
				<td><html:password property="pwd">
					<!-- TODO date de naissance de l'utilisateur ${ XXX.mdp } -->
				</html:password></td>
			</tr>
			<tr>
				<th>Confirm password</th>
				<td><html:password property="confirmPwd">
					<!-- TODO date de naissance de l'utilisateur ${ XXX.mdp } -->
				</html:password></td>
			</tr>
			<tr>
				<th>Job</th>
				<td>
					<html:text errorStyleClass="error" property="job">
						<!-- TODO metier l'utilisateur ${ XXX.profession } -->
					</html:text>
				</td>
			</tr>
			<tr>
				<th>E-mail</th>
				<td>
					<html:text errorStyleClass="error" property="mail">
						<!-- TODO e-mail l'utilisateur ${ XXX.email } -->
					</html:text>
				</td>
			</tr>	
			<tr>
				<th>Phone number</th>
				<td>
					<html:text errorStyleClass="error" property="phone">
						<!-- TODO numero de telephone de l'utilisateur ${ XXX.numTel } -->
					</html:text>
				</td>
			</tr>			
		</table> 
		<html:submit>Validate</html:submit>
	</html:form>   
</div>   