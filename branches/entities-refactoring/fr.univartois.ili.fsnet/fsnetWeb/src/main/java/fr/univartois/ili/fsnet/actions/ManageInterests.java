/*
 *  Copyright (C) 2010 Matthieu Proucelle <matthieu.proucelle at gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.univartois.ili.fsnet.actions;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;
import org.eclipse.persistence.exceptions.DatabaseException;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Interest;

// TODO attention : rustine sur les requetes
// TODO attention : rustine sur les merge user de session
/**
 * Execute CRUD Actions (and more) for the entity interet
 * 
 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
 */
public class ManageInterests extends MappingDispatchAction implements
        CrudAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");
    private static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        DynaActionForm dynaForm = (DynaActionForm) form; //NOSONAR

        String interestName = (String) dynaForm.get("createdInterestName");
        Interest interest = new Interest(interestName);

        logger.info("new interest: " + interestName);

        try {
            em.getTransaction().begin();
            em.persist(interest);
            em.getTransaction().commit();
        } catch (RollbackException ex) {
            ActionErrors actionErrors = new ActionErrors();
            ActionMessage msg = new ActionMessage("interest.alreadyExists");
            actionErrors.add("createdInterestName", msg);
            saveErrors(request, actionErrors);
        }

        em.close();

        return mapping.findForward("success");
    }

    public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
        int interestId = Integer.valueOf((String) dynaForm.get("addedInterestId"));

        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

        logger.info("add interest: id=" + interestId + " for user: "
                + user.getName() + " " + user.getFirstName() + " " + user.getId());

        // TODO requete provisoire

        try {
            Interest interest = (Interest) em.createQuery(
                    "SELECT interest FROM Interest interest WHERE interest.id = :interestId").setParameter("interestId", interestId).getSingleResult();

            boolean dirtyIsOk = true;

            for (Interest interestTmp : user.getInterests()) {
                if (interestTmp.getId() == interestId) {
                    dirtyIsOk = false;
                }
            }

            if (dirtyIsOk) {
                em.getTransaction().begin();
                user.getInterests().add(interest);
                em.merge(user);
                em.getTransaction().commit();
            } else {
                logger.info("Add interest refused");
            }
        } catch (PersistenceException e) {
        }
        em.close();

        return mapping.findForward("success");
    }

    public ActionForward remove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();

        int interestId;

        if (request.getParameterMap().containsKey("removedInterestId")) {
            interestId = Integer.valueOf(request.getParameter("removedInterestId"));

        } else {
            DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
            interestId = Integer.valueOf((String) dynaForm.get("removedInterestId"));
        }

        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

        logger.info("remove interest: id=" + interestId + " for user: "
                + user.getName() + " " + user.getFirstName() + " " + user.getId());

        // TODO requete provisoire
        Interest interest = null;
        for (Interest interestTmp : user.getInterests()) {
            if (interestTmp.getId() == interestId) {
                interest = interestTmp;
                break;
            }
        }
        if (interest != null) {
            user.getInterests().remove(interest);
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } else {
            logger.info("remove interest refused");
        }

        em.close();

        return mapping.findForward("success");
    }

    // TODO modify
    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
        int interestId = Integer.valueOf((String) dynaForm.get("modifiedInterestId"));
        String interestName = (String) dynaForm.get("modifiedInterestName");

        logger.info("interest modification: " + interestName);

        try {
            em.getTransaction().begin();
            em.createQuery(
                    "UPDATE Interest interest SET interest.name = :interestName WHERE interest.id = :interestId").setParameter("interestName", interestName).setParameter("interestId", interestId).executeUpdate();
            em.getTransaction().commit();
        } catch (DatabaseException ex) {
            ActionErrors actionErrors = new ActionErrors();
            ActionMessage msg = new ActionMessage("interest.alreadyExists");
            actionErrors.add("modifiedInterestName", msg);
            saveErrors(request, actionErrors);
        }

        em.close();

        return mapping.findForward("success");
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();

        // TODO verify if user has the right to do delete

        int interestId = Integer.valueOf(request.getParameter("deletedInterestId"));

        logger.info("interest deleted: id=" + interestId);

        try {
            em.getTransaction().begin();
            em.createQuery(
                    "DELETE FROM Interest interest WHERE interest.id = :interestId").setParameter("interestId", interestId).executeUpdate();
            em.getTransaction().commit();

            SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
            em.refresh(user);
        } catch (RollbackException ex) {
            ActionErrors actionErrors = new ActionErrors();
            ActionMessage msg = new ActionMessage("interest.notExists");
            actionErrors.add("error.interest.delete", msg);
            saveErrors(request, actionErrors);
        }

        em.close();

        return mapping.findForward("success");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
        String interestName = "";

        if (dynaForm.get("searchInterestName") != null) {
            interestName = (String) dynaForm.get("searchInterestName");
        }

        logger.info("search interest: " + interestName);

        List<Interest> result = em.createQuery(
                "SELECT interest FROM Interest interest "
                + "WHERE interest.name LIKE :interestName ",
                Interest.class).setParameter("interestName",
                '%' + interestName + '%').getResultList();
        em.close();

        if (result.size() > 0) {
            request.setAttribute("interestResult", result);
        }

        return mapping.findForward("success");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

        logger.info("Displaying interests");

        // TODO requete provisoire

        List<Interest> listAllInterests = em.createQuery(
                "SELECT interest FROM Interet interest", Interest.class).getResultList();

        List<Interest> finalList = new ArrayList<Interest>();
        boolean dirtyIsOK;
        for (Interest interest : listAllInterests) {
            dirtyIsOK = true;
            for (Interest interestEntity : user.getInterests()) {
                if (interestEntity.getId() == interest.getId()) {
                    dirtyIsOK = false;
                }
            }
            if (dirtyIsOK) {
                finalList.add(interest);
            }
        }

        request.setAttribute("user", user);
        request.setAttribute("allInterests", listAllInterests);
        request.setAttribute("listInterests", finalList);

        em.close();
        return mapping.findForward("success");
    }
}
