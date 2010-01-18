package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.Manifestation;

/**
 * Servlet implementation class SuppEven
 */
public class SuppEven extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        int idEven;
        idEven = Integer.valueOf(request.getParameter("idEven"));
        int identite;
        identite = Integer.valueOf(request.getParameter("idEntite"));

        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("fsnetjpa");
        EntityManager entM;
        entM = factory.createEntityManager();
        Manifestation manif;
        manif = entM.getReference(Manifestation.class, Integer.valueOf(idEven));

        if (manif.getCreateur().getId() == identite) {
            manif.setVisible("N");
            entM.getTransaction().begin();
            entM.merge(manif);
            entM.getTransaction().commit();
            // IliForumFacade.getInstance().removeHub( manif);
        }

        request.setAttribute("info",
                "<p id=\"info\">La suppression d'un événement est effectuée.</p>");

        RequestDispatcher dispa;
        dispa = getServletContext().getRequestDispatcher("/toutEvenement.jsp");
        dispa.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        super.doPost(request, response);
    }
}
