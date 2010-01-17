package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;

/**
 * 
 * The class Communaute.
 * 
 */
@Entity
public class Communaute extends Interaction {

    /**
     * The community name.
     */
    private String nomCommunaute;

    /**
     * Constructor of the class Communaute.
     */
    public Communaute() {
    }

    /**
     * Constructor of the class Communaute.
     *
     * @param nomCommunaute
     *            .
     */
    public Communaute(String nomCommunaute) {
        super();
        this.nomCommunaute = nomCommunaute;
    }

    /**
     *
     * @return the community name.
     */
    public String getNomCommunaute() {
        return nomCommunaute;
    }

    /**
     * Gives a name to the community.
     *
     * @param nomCommunaute
     *            .
     */
    public void setNomCommunaute(String nomCommunaute) {
        this.nomCommunaute = nomCommunaute;
    }
}
