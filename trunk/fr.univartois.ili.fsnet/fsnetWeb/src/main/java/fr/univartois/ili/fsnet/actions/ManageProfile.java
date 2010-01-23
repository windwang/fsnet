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
import fr.univartois.ili.fsnet.form.ProfileForm;

/**
 *
 * @author Geoffrey Boulay 
 */
public class ManageProfile extends MappingDispatchAction implements CrudAction {

    /**
     *  watched profile variable session name
     */
    public static final String WATCHED_PROFILE_VARIABLE = "watchedProfile";

    /**
     * format a name
     * exemple : entry : le BerrE return Le Berre
     * @param name string to format
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
                    buf.append((upperCase) ? (Character.toUpperCase(c)) : (Character.toLowerCase(c)));
                    upperCase = false;
            }
        }
        return buf.toString();
    }
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(Authenticate.AUTHENTICATED_USER);
        ProfileForm pform = (ProfileForm) form;
        user.setNom(formatName(pform.getName()));
        user.setPrenom(formatName(pform.getFirstName()));
        user.setAdresse(pform.getAdress());
        user.setDateNaissance(pform.getParsedDateOfBirth());
        user.setSexe(pform.getSexe());
        user.setMdp(pform.getPwd());
        user.setProfession(formatName(pform.getJob()));
        user.setEmail(pform.getMail());
        user.setNumTel(pform.getPhone());
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
        return mapping.findForward("success");
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        if (form == null) {
            request.setAttribute("currentUser", request.getSession().getAttribute(Authenticate.AUTHENTICATED_USER));
            return mapping.findForward("success");
        }
        try {
            String idS = ((DynaActionForm) form).getString("id");
            int id = Integer.parseInt(idS);
            EntiteSociale profile = em.find(EntiteSociale.class, id);
            request.setAttribute(WATCHED_PROFILE_VARIABLE, profile);
            em.close();
            return mapping.findForward("success");
        } catch (NumberFormatException e) {
            return mapping.findForward("fail");
        }
    }
}
