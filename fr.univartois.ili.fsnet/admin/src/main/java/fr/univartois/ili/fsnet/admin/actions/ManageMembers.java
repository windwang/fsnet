package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ManageMembers extends MappingDispatchAction implements CrudAction {

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");
	private static final Logger logger = Logger.getAnonymousLogger();

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		  	DynaActionForm dynaForm = (DynaActionForm) form;						//NOSONAR
	        String name = (String) dynaForm.get("name");
	        String firstName = (String) dynaForm.get("firstName");
	        String mail = (String) dynaForm.get("mail");

	        logger.info("new socialentity: " + name+" "+firstName+" "+mail);
	        EntityManager em = factory.createEntityManager();
	     	      
	        SocialEntity socialEntity = new SocialEntity(name, firstName, mail);
	        em.getTransaction().begin();
	        em.persist(socialEntity);
	        em.getTransaction().commit();
	        em.close();

	        return mapping.findForward("success");
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String entitySelected = request.getParameter("entitySelected");

        logger.info("delete social entity: " + entitySelected);

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM SocialEntity socialentity WHERE socialentity.id = :entitySelected ")
        				.setParameter("entitySelected", Integer.parseInt(entitySelected)).executeUpdate();
        em.getTransaction().commit();
        em.close();
        return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<SocialEntity> query = null;
		List<SocialEntity> resultOthers = null;
		
		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form;			//NOSONAR
			String searchText = (String) dynaForm.get("searchText");
			
			query = em.createQuery(
					"SELECT es FROM SocialEntity es WHERE (es.name LIKE :searchText"
					+ " OR es.firstName LIKE :searchText OR es.email LIKE :searchText)", SocialEntity.class);
			query.setParameter("searchText", "%" + searchText + "%");

		}
		else {
			query = em.createQuery("SELECT es FROM SocialEntity es", SocialEntity.class);
		}
		resultOthers = query.getResultList();
		em.close();

		request.setAttribute("membersResult", resultOthers);
		return mapping.findForward("success");
	}
}
