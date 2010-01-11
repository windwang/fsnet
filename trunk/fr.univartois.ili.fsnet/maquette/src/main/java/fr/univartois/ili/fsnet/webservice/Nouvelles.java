package fr.univartois.ili.fsnet.webservice;

import java.util.Date;
import java.util.Iterator;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import fr.univartois.ili.fsnet.entities.*;

@WebService(name = "NouvellesInformations")
public class Nouvelles {
	private Iterator<Annonce> it;
	private EntityManagerFactory factory;
	private EntityManager em;
	private Query ql;
	private Query ql1;
	int j,i;
	Date dt;

	public Nouvelles() {
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		em = factory.createEntityManager();
		this.dt=new Date();


	}

	@WebMethod
	public @WebResult(name = "nbevenement")
	int getNumberOfNewEvents() {

		ql = em.createQuery("SELECT m FROM Manifestation m where m.dateInformation=?1 ");
		ql.setParameter(1, dt);
		j=ql.getResultList().size();
		

		return j;
	}
	@WebMethod
	public @WebResult(name = "nbevenement")
	int getNumberOfNewAnnonce() {

		ql1 = em.createQuery("SELECT m FROM Annonce m where m.dateInformation=?1 ");
		ql1.setParameter(1, dt);
		i=ql1.getResultList().size();
		

		return i;
	}

}
