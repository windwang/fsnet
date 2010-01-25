package fr.univartois.ili.fsnet.form;



import java.text.ParseException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import org.apache.struts.validator.ValidatorForm;

import fr.univartois.ili.fsnet.actions.utils.DateUtils;



public class ProfileForm extends ValidatorForm {
	private static final long serialVersionUID = 8185684908229309637L;

	private String name;
	private String firstName;
	private String adress;
	private String dateOfBirth;
	private String sexe;
	private String pwd;
	private String confirmPwd;
	private String job;
	private String mail;
	private String phone;
	private Date parsedDateOfBirth;
	
	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = super.validate(arg0, arg1);
		if (errors.isEmpty() && ! pwd.equals(confirmPwd)) {
			errors.add("confirmPwd",new ActionMessage("error.updateProfile.confirmPwd.diff"));
		}
		try {
			java.util.logging.Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, dateOfBirth);
			parsedDateOfBirth  = DateUtils.format(dateOfBirth);
		} catch (ParseException e) {
			errors.add("dateOfBirth",new ActionMessage("error.updateProfile.date.invalid"));
		}
		return errors; 
	}
	
	public Date getParsedDateOfBirth() {
		return parsedDateOfBirth;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
