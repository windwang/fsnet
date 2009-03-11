package fr.univartois.ili.fsnet.TrayWithServlets.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InformationsSender
 */
public class InformationsSender extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InformationsSender() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Set<String> maSet = new HashSet<String>();
		String info1= "java site developpez";
		String info2= "nouvelle annoce de soiree";
		String info3="nouvel evenement de sortie";
		maSet.add(info1);
		maSet.add(info2);
		maSet.add(info3);
		
		RequestDispatcher dispatcher =getServletContext().getRequestDispatcher("/InformationsReception");
	    request.setAttribute("maSet", maSet);
	    dispatcher.forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
