package fr.univartois.ili.fsnet.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostRemove;

/**
 * 
 * The class Hub.
 * 
 */
@Entity
public class Hub extends Interaction {

    private static final long serialVersionUID = 1L;
    /**
     * The list of topics of a hub.
     */
    @OneToMany(mappedBy = "hub", cascade = CascadeType.ALL)
    private List<Topic> topics;
    @ManyToOne
    private Community community;

    /**
     * Constructor of the class Hub.
     */
    public Hub() {
    }

    /**
     * Constructor of the class Hub.
     *
     * @param nomCommunaute
     * @param dateCreation
     * @param topics
     */
    public Hub(Community community, SocialEntity creator, String name) {
        super(creator, name);
        topics = new ArrayList<Topic>();
        this.community = community;
    }

    /**
     *
     * @return the list of topics of a hub.
     */
    public List<Topic> getTopics() {
        return topics;
    }

    /**
     * Gives a list of topics to a hub.
     *
     * @param topics
     */
    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    /**
     *
     * @return the community
     */
    public Community getCommunity() {
        return community;
    }

    /**
     *
     * @param community the hub community
     */
    public void setCommunity(Community community) {
        this.community = community;
    }
    
    @PostRemove
	public void onHubRemove() {
    	setCommunity(null);
    	topics.clear();
    }
}
