package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.ProfileFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;

/**
 * 
 * @author Geoffrey Boulay
 */
public class ManageProfile extends MappingDispatchAction implements CrudAction {

	/**
	 * watched profile variable session name
	 */
	public static final String WATCHED_PROFILE_VARIABLE = "watchedProfile";
	public static final String EDITABLE_PROFILE_VARIABLE = "edit";

	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");
	
	private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * format a name exemple : entry : le BerrE return Le Berre
	 * 
	 * @param name
	 *            string to format
	 * @return format string
	 */
	public static final String formatName(String name) {
		StringBuffer buf = new StringBuffer();
		boolean upperCase = true;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			switch (c) {
			case '\'':
				;
			case ' ':
				;
			case '-':
				buf.append(c);
				upperCase = true;
				break;
			default:
				buf.append((upperCase) ? (Character.toUpperCase(c))
						: (Character.toLowerCase(c)));
				upperCase = false;
			}
		}
		return buf.toString();
	}

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;		//NOSONAR
		Date birthday = null;
		try {
			birthday = DateUtils.format(dynaForm.getString("dateOfBirth"));
		} catch (ParseException e1) {
			// DO NOTHING
		}
		ProfileFacade pf = new ProfileFacade(em);
		em.getTransaction().begin();
		pf.editProfile(UserUtils.getAuthenticatedUser(request, em),
				formatName(dynaForm.getString("name")), formatName(dynaForm
						.getString("firstName")), new Address(dynaForm
						.getString("adress"), formatName(dynaForm
						.getString("city"))), birthday, dynaForm
						.getString("sexe"), formatName(dynaForm
						.getString("job")), dynaForm.getString("mail"),
				dynaForm.getString("phone"));
		em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public ActionForward displayToModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dina = (DynaActionForm) form;			//NOSONAR
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		request.setAttribute("currentUser", user);
		dina.set("name", user.getName());
		dina.set("firstName", user.getFirstName());
		if (user.getAddress() != null) {
			dina.set("adress", user.getAddress().getAddress());
			dina.set("city", user.getAddress().getCity());
		}
		if (user.getBirthDate() != null)
			dina.set("dateOfBirth", formatter.format(user.getBirthDate()));
		dina.set("sexe", user.getSex());
		dina.set("job", user.getProfession());
		dina.set("mail", user.getEmail());
		dina.set("phone", user.getPhone());
		em.close();
		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		DynaActionForm dyna = (DynaActionForm) form;		// NOSONAR 
	    int id = -1;
		try {
			String idS = dyna.getString("id");
			id = Integer.parseInt(idS);			
		} catch (NumberFormatException e) {
			id = user.getId();
		} 
		SocialEntity profile = sef.getSocialEntity(id);
		request.setAttribute(WATCHED_PROFILE_VARIABLE, profile);
		request.setAttribute(EDITABLE_PROFILE_VARIABLE, profile.equals(user));
		if(profile.getBirthDate()!=null)
			request.setAttribute("birthDay", formatter.format(profile.getBirthDate()));
		em.close();
		return mapping.findForward("success");		
	}
}
