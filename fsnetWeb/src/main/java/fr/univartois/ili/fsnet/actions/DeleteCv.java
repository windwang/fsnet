package fr.univartois.ili.fsnet.actions;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.facade.CvFacade;


/**
 * @author Aich ayoub
 * 
 */

public class DeleteCv extends MappingDispatchAction {
	private EntityManager em = PersistenceProvider.createEntityManager();
	
	public ActionForward deleteCv(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		long id = Integer.parseInt(request.getParameter("idCv"));
		CvFacade cvFacade = new CvFacade(em);
		Curriculum curriculum = cvFacade.getCurriculum(id);
		em.getTransaction().begin();
		em.remove(curriculum);
		em.getTransaction().commit();
		
		return mapping.findForward("success");
		
	}
	

}
