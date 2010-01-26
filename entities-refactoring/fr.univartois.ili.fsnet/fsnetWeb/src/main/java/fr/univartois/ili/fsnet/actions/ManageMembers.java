package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.List;

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

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ManageMembers extends MappingDispatchAction {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<SocialEntity> query = null;
		TypedQuery<SocialEntity> queryContacts = null;
		TypedQuery<SocialEntity> queryRequested = null;
		TypedQuery<SocialEntity> queryAsked = null;
		List<SocialEntity> resultOthers = null;
		List<SocialEntity> resultContacts = null;
		List<SocialEntity> resultRequested = null;
		List<SocialEntity> resultAsked = null;
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);

		member = em.find(SocialEntity.class, member.getId());

		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
			String searchText = (String) dynaForm.get("searchText");

			query = em.createQuery(
					"SELECT es FROM SocialEntity es WHERE (es.name LIKE :searchText"
					+ " OR es.firstName LIKE :searchText OR es.email LIKE :searchText) AND es.id <> :id", SocialEntity.class);
			query.setParameter("searchText", "%" + searchText + "%");
			query.setParameter("id", member.getId());

			queryContacts = em.createQuery(
					"SELECT e FROM SocialEntity e JOIN e.contacts c WHERE c.id = :id AND (e.name LIKE :searchText"
					+ " OR e.firstName LIKE :searchText OR e.email LIKE :searchText)", SocialEntity.class);
			queryContacts.setParameter("searchText", "%" + searchText + "%");
			queryContacts.setParameter("id", member.getId());

			queryRequested = em.createQuery(
					"SELECT e FROM SocialEntity e JOIN e.asked r WHERE r.id = :id AND (e.name LIKE :searchText"
					+ " OR e.firstName LIKE :searchText OR e.email LIKE :searchText)", SocialEntity.class);
			queryRequested.setParameter("searchText", "%" + searchText + "%");
			queryRequested.setParameter("id", member.getId());

			queryAsked = em.createQuery(
					"SELECT e FROM SocialEntity e JOIN e.requested r WHERE r.id = :id AND (e.name LIKE :searchText"
					+ " OR e.firstName LIKE :searchText OR e.email LIKE :searchText)", SocialEntity.class);
			queryAsked.setParameter("searchText", "%" + searchText + "%");
			queryAsked.setParameter("id", member.getId());

			resultContacts = queryContacts.getResultList();
			resultRequested = queryRequested.getResultList();
			resultAsked = queryAsked.getResultList();

		} else {
			query = em.createQuery("SELECT es FROM SocialEntity es WHERE es.id <> :id", SocialEntity.class);
			query.setParameter("id", member.getId());

			resultContacts = member.getContacts();
			resultRequested = member.getRequested();
			resultAsked = member.getAsked();
		}

		resultOthers = query.getResultList();

		resultOthers.removeAll(resultContacts);
		resultOthers.removeAll(resultAsked);
		resultOthers.removeAll(resultRequested);

		em.close();

		request.setAttribute("membersResult", resultOthers);
		request.setAttribute("membersContactsResult", resultContacts);
		request.setAttribute("membersRequestedResult", resultRequested);
		request.setAttribute("membersAskedResult", resultAsked);


		return mapping.findForward("success");
	}
}
