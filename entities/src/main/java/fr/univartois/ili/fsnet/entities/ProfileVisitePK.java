package fr.univartois.ili.fsnet.entities;

public class ProfileVisitePK {

	private int visitor;
	private int visited;
	
	/**
	 * Default constructor
	 */
	public ProfileVisitePK() {
	}
	
	
	/**
	 * create a rofileVisitePK this value
	 * @param visitor visitor Idenfifiant
	 * @param visited  visited Identifiant
	 */
	
	public ProfileVisitePK(int visitor, int visited) {
		super();
		this.visitor = visitor;
		this.visited = visited;
	}




	/**
	 * 
	 * @return get the profile visiter identifiant 
	 */
	public int getVisitor() {
		return visitor;
	}
	
	/**
	 * set the visiter identidiant
	 * @param visiterId the new identifiant
	 */
	public void setVisitor(int visitorId) {
		this.visitor = visitorId;
	}
	
	/**
	 * 
	 * @return the identidiant of the social entity who's watch the profile
	 */
	public int getVisited() {
		return visited;
	}
	
	/**
	 * set the identidiant of the social entity who's watch the profile
	 * 
	 * @param visitor the new identifiant
	 */
	public void setVisited(int visitedId) {
		this.visited = visitedId;
	}
	
	
	
}
