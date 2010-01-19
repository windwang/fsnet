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
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.entities.Annonce;
import fr.univartois.ili.fsnet.entities.EntiteSociale;

/**
 * 
 * @author BENZAGHAR MEHDI
 */
public class ManageAnnounces extends MappingDispatchAction implements
		CrudAction {

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (processRoles(request, response, mapping)) {
			Annonce newAnnounce;
			EntiteSociale entiteSociale;
			HttpSession session;
			String title;
			String content;
			String stringExpiryDate;
			Date expiryDate;
			Date today;
			DateFormat simpleFormat;
			String DATABASE_NAME = "fsnet";
			EntityManagerFactory factory;
			EntityManager entityManager;
			ActionMessages errors = new ActionErrors();
			session = request.getSession();
			factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
			entityManager = factory.createEntityManager();
			entiteSociale = (EntiteSociale) session.getAttribute("entite");
			title = request.getParameter("titleAnnouncement");
			content = request.getParameter("contentAnnouncement");
			stringExpiryDate = request.getParameter("expiryDateAnnouncement");
			today = new Date(0);
			simpleFormat = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
			try {
				expiryDate = (Date) simpleFormat.parse(stringExpiryDate);
				//TODO
				if (0 < expiryDate.compareTo(today)) {
					newAnnounce = new Annonce(title, today, content,
							expiryDate, "Y", entiteSociale);
					entityManager.getTransaction().begin();
					entityManager.persist(newAnnounce);
					entityManager.getTransaction().commit();
				} else {
					errors.add("message", new ActionMessage(
							"errors.dateLessThanCurrentDate"));
					saveErrors(request, errors);
				}
			} catch (ParseException e) {
				servlet
						.log("class:ManageAnnounces methode:create exception whene formatying date ");
				e.printStackTrace();
			}
			// TODO
			return mapping.findForward("");
		}
		return mapping.findForward("");

	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
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

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @author mehdi
	 * @param request
	 * @return boolean true : if user is connected
	 */
	protected boolean isUserConnected(HttpServletRequest request) {
		EntiteSociale es = null;
		if (request != null) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				es = (EntiteSociale) session.getAttribute("es");
			}

			return es != null;
		}
		return false;
	}

	/**
	 * @author mehdi
	 * @param request
	 * @return boolean true : if this session is valid
	 * @throws ServletException
	 */

	protected boolean isSessionValid(HttpServletRequest request)
			throws ServletException {
		if (request != null) {
			return request.isRequestedSessionIdValid();
		}

		return false;
	}

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param mapping
	 * @return boolean true : if the session is valid and the user is connected
	 * @throws java.io.IOException
	 * @throws javax.servlet.ServletException
	 */
	protected boolean processRoles(HttpServletRequest request,
			HttpServletResponse response, ActionMapping mapping)
			throws java.io.IOException, javax.servlet.ServletException {

		ActionMessages messages = new ActionMessages();
		if (isSessionValid(request)) {
			if (isUserConnected(request)) {
				return true;
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.connection.required"));
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.session.expired"));
		}

		request.getRequestDispatcher("/index.jsp").forward(request, response);

		return false;
	}

}
