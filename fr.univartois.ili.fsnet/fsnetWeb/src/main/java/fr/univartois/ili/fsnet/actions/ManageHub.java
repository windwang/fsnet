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

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Hub;

/**
 *
 * @author Cerelia Besnainou et Audrey Ruellan
 */
public class ManageHub extends MappingDispatchAction implements CrudAction {
	
	private static EntityManagerFactory factory = Persistence
	.createEntityManagerFactory("fsnetjpa");
	
	private static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	DynaActionForm dynaForm = (DynaActionForm) form;
		String hubName = (String) dynaForm.get("hubName");
				
		logger.info("new hub: " + hubName);
		
		Hub hub= new Hub(hubName, new Date());
		EntiteSociale es = (EntiteSociale) request.getSession().getAttribute(Authenticate.AUTHENTICATED_USER);
		
		hub.setCreateur(es);
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(hub);
		em.getTransaction().commit();
		em.close();
		
		return mapping.findForward("success");
    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	String hubId = request.getParameter("hubId");
    	
//    	DynaActionForm dynaForm = (DynaActionForm) form;
//		String hubId = (String) dynaForm.get("hubId");
				
		logger.info("delete hub: " + hubId);
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
        em.createQuery("DELETE FROM Hub hub WHERE hub.id = :hubId ")
        		.setParameter("hubId", Integer.parseInt(hubId)).executeUpdate();
        em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	DynaActionForm dynaForm = (DynaActionForm) form;
    	String hubName;
    	if(form==null){
			hubName="";
		}
    	else{
    		hubName = (String) dynaForm.get("hubName");
    	}
		
				
		logger.info("search hub: " + hubName);
		
		EntityManager em = factory.createEntityManager();
		
		List<Hub> result =
        	em.createQuery("SELECT hub FROM Hub hub WHERE hub.nomCommunaute LIKE :hubName ")
        		.setParameter("hubName", "%"+hubName+"%")
        		.getResultList();
		request.setAttribute("hubResults", result);
    	
		return mapping.findForward("success");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
