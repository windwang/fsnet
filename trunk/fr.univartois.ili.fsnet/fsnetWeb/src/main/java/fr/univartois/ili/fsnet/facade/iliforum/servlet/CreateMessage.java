package fr.univartois.ili.fsnet.facade.iliforum.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

/**
 * Servlet implementation class CreateMessage
 */
public class CreateMessage extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String nom;
        Date date;
        EntiteSociale ent;
        String nomUTF8;
        Message message;

        nom = request.getParameter("contenuMessage");
        date = new Date();
        ent = (EntiteSociale) request.getSession().getAttribute("entite");
        nomUTF8 = new String(nom.getBytes("ISO-8859-1"), "UTF-8");
        message = new Message(nomUTF8, date, ent, (Topic) getServletContext().getAttribute("monTopic"));

        IliForumFacade.getInstance().addMessage(message);

        RequestDispatcher dispa;
        dispa = getServletContext().getRequestDispatcher("/message.jsp");
        dispa.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        this.doGet(request, response);
    }
}
