package fr.univartois.ili.fsnet.admin.servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.admin.EnvoieMail;


/**
 * Servlet implementation class SendMessage
 */
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		EnvoieMail sendmessage = new EnvoieMail();
		String dest=req.getParameter("to");
		String obj=req.getParameter("subject");
		String conte=req.getParameter("contenu");
		String file=req.getParameter("fichier");
		String list[]=dest.split(",");
		String smtp;
		String user;
		String mots;
		String [] contenus=null;
		
		try{
			if(file.equals("")){
				sendmessage.envoyerMessage(list, obj, conte);
		System.out.println("Sucessfully Sent mail to All Users");
		PrintWriter out=res.getWriter();
		out.write("<b>message bien envoyer</b>");
			}
			else{
				sendmessage.postMail(list, obj, conte,file);
				System.out.println("Sucessfully Sent mail to All Users");
				PrintWriter out=res.getWriter();
				out.write("<b>message bien envoyer</b>");
							
			}
			String jsp = "BureauAdmin.html";
			RequestDispatcher dispatch = req.getRequestDispatcher(jsp);
			dispatch.forward(req,res); 

		}catch(Exception e){
			System.out.println("erreur d'envoie");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req,resp);
	}

}
